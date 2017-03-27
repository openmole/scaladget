import java.io.PrintWriter

import sbt._
import Keys._

import ScalaJSBundlerPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._


//scalaOrganization in ThisBuild := "org.typelevel"

val ScalaVersion = "2.11.8"
val bootstrapNativeVersion = "1.1.0"
val scalatagsVersion = "0.6.2"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.1"
val sourceCodeVersion = "0.1.2"

lazy val defaultSettings = Seq(
  organization := "fr.iscpif",
  version := "0.9.3-SNAPSHOT",
  scalaVersion := ScalaVersion,
  resolvers := Seq(Resolver.sonatypeRepo("snapshots"),
    DefaultMavenRepository
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

lazy val scaladget = project.in(file("scaladget")) settings(
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion/*,
  npmDependencies in Compile += "bootstrap.native" -> bootstrapNativeVersion*/
) enablePlugins (ScalaJSPlugin) settings (defaultSettings: _*)


lazy val runDemo = taskKey[Unit]("runDemo")
lazy val bootstrapJS = taskKey[TaskKey[Attributed[sbt.File]]]("bootstrapJS")

//lazy val demo = project.in(file("demo")) dependsOn (scaladget) enablePlugins (ScalaJSBundlerPlugin) settings (defaultSettings: _*) settings(
lazy val demo = project.in(file("demo")) dependsOn (scaladget) enablePlugins (ScalaJSPlugin) settings (defaultSettings: _*) settings(
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion,
  libraryDependencies += "org.scala-lang" % "scala-reflect" % ScalaVersion % "provided",
  //  enableReloadWorkflow := false,
  runDemo := {
    val demoTarget = target.value
    val demoResource = (resourceDirectory in Compile).value
  //  val bootstrapJS = (npmUpdate in Compile).value / "node_modules" / "bootstrap.native" / "dist" / "bootstrap-native.min.js"
    val demoJS = (fullOptJS in Compile).value

    IO.copyFile(demoJS.data, demoTarget / "js/demo.js")
  //  IO.copyFile(bootstrapJS, demoTarget / "js/bootstrap.-native.min.js")


    IO.copyFile(demoResource / "index.html", demoTarget / "index.html")
    IO.copyDirectory(demoResource / "js", demoTarget / "js")
    IO.copyDirectory(demoResource / "css", demoTarget / "css")
    IO.copyDirectory(demoResource / "fonts", demoTarget / "fonts")
  }
)


/* (fastOptJS in Compile).map { fjs =>
      println("XXXXX")
      val file = fjs.data.getAbsoluteFile
      val lines = io.Source.fromFile(file).getLines.mkString

      println("file " + file)
      val pw = new PrintWriter(file)
      try pw.write(lines.replace("$g.require", "require")) finally pw.close()
    }*/

//  println("Update: " + bootstrapJS.getAbsolutePath)
// (webpack in(Compile, fastOptJS in Compile)).value.foreach { f =>
