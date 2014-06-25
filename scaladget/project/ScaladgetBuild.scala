import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

object ScaladgetBuild extends Build {
  val Organization = "fr.iscpif"
  val Name = "Scaladget"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.4"

  lazy val jsManager = Project(
    "jsManager",
    file("./jsManager"),
    settings = Defaults.defaultSettings ++ scalaJSSettings ++ Seq(
      organization := "fr.iscpif",
      version := Version,
      sbtPlugin := true,
      publishTo <<= isSnapshot { snapshot =>
        val nexus = "https://oss.sonatype.org/"
        if (snapshot) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      libraryDependencies ++= Seq("com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2"),
      addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.0")
    )
  )

  lazy val scaladget = Project(
    "scaladget",
    file("./scaladget"),
    settings = Defaults.defaultSettings ++ scalaJSSettings ++ Seq(
      version := Version,
      organization := "fr.iscpif",
      scalaVersion := ScalaVersion,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
        "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6"
      ),
      publishTo <<= isSnapshot { snapshot =>
        val nexus = "https://oss.sonatype.org/"
        if (snapshot) Some("snapshots" at nexus + "content/repositories/snapshots")
        else Some("releases" at nexus + "service/local/staging/deploy/maven2")
      },
      credentials += Credentials(Path.userHome / ".sbt" / "iscpif.credentials"),
      pomIncludeRepository := { _ => false},
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
  )
}