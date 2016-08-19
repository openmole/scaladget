import java.io.File

import sbt._
import Keys._
import org.scalajs.sbtplugin.ScalaJSPlugin
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

object ScaladgetBuild extends Build {
  val Organization = "fr.iscpif"
  val Name = "Scaladget"
  val Version = "0.9.0-SNAPSHOT"
  val ScalaVersion = "2.11.8"
  val Resolvers = Seq(Resolver.sonatypeRepo("snapshots"),
    DefaultMavenRepository
  )


  lazy val scaladget = Project(
    "scaladget",
    file("scaladget"),
    settings = Seq(
      version := Version,
      organization := Organization,
      scalaVersion := ScalaVersion,
      resolvers in ThisBuild ++= Resolvers,
      libraryDependencies ++= Seq(
        "org.scala-js" %%% "scalajs-dom" % "0.9.1",
        "com.lihaoyi" %%% "scalatags" % "0.6.0",
        "com.lihaoyi" %%% "scalarx" % "0.3.1",
        "org.querki" %%% "querki-jsext" % "0.6",
        "org.querki" %%% "jquery-facade" % "1.0-RC3"
      ),
      publishTo := {
        val nexus = "https://oss.sonatype.org/"
        if (version.value.trim.endsWith("SNAPSHOT"))
          Some("snapshots" at nexus + "content/repositories/snapshots")
        else
          Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      pomIncludeRepository := { _ => false },
      licenses := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/")),
      homepage := Some(url("https://github.com/mathieuleclaire/scaladget")),
      scmInfo := Some(ScmInfo(url("https://github.com/mathieuleclaire/scaladget.git"), "scm:git:git@github.com:mathieuleclaire/scaladget.git")),
      pomExtra := {
        <developers>
          <developer>
            <id>mathieuleclaire</id>
            <name>Mathieu Leclaire</name>
            <url>https://github.com/mathieuleclaire/</url>
          </developer>
        </developers>
      }
    )
  ) enablePlugins (ScalaJSPlugin)


  lazy val demo = Project(
    "demo",
    file("demo"),
    settings = Seq(
      version := Version,
      scalaVersion := ScalaVersion,
      libraryDependencies ++= Seq(
        "com.lihaoyi" %%% "scalarx" % "0.3.1"
      )
    )
  ) dependsOn (scaladget) enablePlugins (ScalaJSPlugin)

  lazy val runDemo = taskKey[Unit]("runDemo")

  lazy val bootstrap = Project(
    "bootstrap",
    file("target/bootstrap"),
    settings = Seq(
      version := Version,
      scalaVersion := ScalaVersion,
      (runDemo <<= (fullOptJS in demo in Compile, resourceDirectory in demo in Compile, target in demo) map { (ct, r, t) =>
        ct.map { f => IO.copyFile(f, new File(t, s"js/${f.getName}"), preserveLastModified = true) }
        IO.copyFile(new File(r, "index.html"), new File(t, "index.html"))
        recursiveCopy(new File(r, "js"), new File(t, "js"))
        recursiveCopy(new File(r, "css"), new File(t, "css"))
        recursiveCopy(new File(r, "fonts"), new File(t, "fonts"))
      }
        )
    )
  ) dependsOn (demo)

  private def recursiveCopy(from: File, to: File): Unit = {
    if (from.isDirectory) {
      to.mkdirs()
      for {
        f ← from.listFiles()
      } recursiveCopy(f, new File(to, f.getName))
    }
    else if (!to.exists() || from.lastModified() > to.lastModified) {
      println(s"Copy file $from to $to ")
      from.getParentFile.mkdirs
      IO.copyFile(from, to, preserveLastModified = true)
    }
  }
}