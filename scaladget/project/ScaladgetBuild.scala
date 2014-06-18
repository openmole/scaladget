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
    settings = Defaults.defaultSettings ++ Seq(
      organization := "fr.iscpif",
      version := Version,
      sbtPlugin := true,
      publishTo <<= isSnapshot(if (_) Some("ISC-PIF Snapshot" at "http://maven.iscpif.fr/snapshots")
      else Some("Openmole Releas" at "http://maven.iscpif.fr/releases")),
      credentials += Credentials(Path.userHome / ".sbt" / "iscpif.credentials"),
      libraryDependencies ++= Seq("com.github.scala-incubator.io" %% "scala-io-file" % "0.4.2",
        "org.scala-lang.modules.scalajs" %% "scalajs-jquery" % "0.4"),
      addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.4.0")
    )
  )

  lazy val scaladget = Project(
    "scaladget",
    file("./scaladget"),
    settings = Defaults.defaultSettings ++ scalaJSSettings ++ Seq(
      version := Version,
      organization := "fr.iscpif",
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.4",
        "org.scala-lang.modules.scalajs" %% "scalajs-jquery" % "0.4"
      ),
      addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.4.0"),
      publishTo <<= isSnapshot(if (_) Some("ISC-PIF Snapshot" at "http://maven.iscpif.fr/snapshots")
      else Some("Openmole Releas" at "http://maven.iscpif.fr/releases")),
      credentials += Credentials(Path.userHome / ".sbt" / "iscpif.credentials")
    )
  )
}