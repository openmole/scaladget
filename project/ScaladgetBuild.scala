import sbt._
import Keys._
import scala.scalajs.sbtplugin.ScalaJSPlugin._
import ScalaJSKeys._

object ScaladgetBuild extends Build {
  val Organization = "fr.iscpif"
  val Name = "Scaladget"
  val Version = "0.1.0-SNAPSHOT"
  val ScalaVersion = "2.10.4"


  val js = taskKey[Unit]("Generates and copy js files")
  val runExample = taskKey[Unit]("Run examples")

  lazy val examples = Project("examples",
    file("./examples"),
    settings = Defaults.defaultSettings ++ scaladget.settings ++ Seq(
      js := {
        val optfile = (optimizeJS in Compile).toTask.value
        val optFilename = optfile.getName
        val preoptFileName = optFilename.replace("opt", "preopt")
        val jsDir = new java.io.File(sourceDirectory.toTask.value, "main/resources/js")
        IO.copyFile(optfile, new java.io.File(jsDir,optFilename))
        IO.copyFile(new java.io.File(optfile.getParentFile, preoptFileName), new java.io.File(jsDir,preoptFileName) )
      },
      runExample <<= (js in Compile, copyResources in Compile, compile in Compile) map {
        (j, c, p) =>
      }
    )
  ) dependsOn (scaladget)

  lazy val scaladget = Project(
    "scaladget",
    file("./scaladget"),
    settings = Defaults.defaultSettings ++ scalaJSSettings ++ Seq(
      version := Version,
      scalaVersion := ScalaVersion,
      resolvers += Classpaths.typesafeReleases,
      libraryDependencies ++= Seq(
        "org.scala-lang.modules.scalajs" %% "scalajs-dom" % "0.4",
        "org.scala-lang.modules.scalajs" %% "scalajs-jquery" % "0.4"
      )
    )
  )

}