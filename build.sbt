import java.io.PrintWriter

import sbt._
import Keys._

import ScalaJSBundlerPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._


//scalaOrganization in ThisBuild := "org.typelevel"

val bootstrapNativeVersion = "1.1.0"
val scalatagsVersion = "0.6.5"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.2"
val sourceCodeVersion = "0.1.2"

crossScalaVersions in ThisBuild := Seq("2.11.8", "2.12.1")
organization in ThisBuild := "fr.iscpif"
version in ThisBuild := "0.9.5-SNAPSHOT"
resolvers in ThisBuild := Seq(Resolver.sonatypeRepo("snapshots"),
  DefaultMavenRepository
)
publishTo in ThisBuild := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}
pomIncludeRepository in ThisBuild := { _ => false }
licenses in ThisBuild := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/"))
homepage in ThisBuild := Some(url("https://github.com/mathieuleclaire/scaladget"))
scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/mathieuleclaire/scaladget.git"), "scm:git:git@github.com:mathieuleclaire/scaladget.git"))
pomExtra in ThisBuild := {
  <developers>
    <developer>
      <id>mathieuleclaire</id>
      <name>Mathieu Leclaire</name>
      <url>https://github.com/mathieuleclaire/</url>
    </developer>
  </developers>
}

lazy val scaladget = project.in(file("scaladget")) settings(
  libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalatags" % scalatagsVersion,
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion /*,
  npmDependencies in Compile += "bootstrap.native" -> bootstrapNativeVersion*/
) enablePlugins (ScalaJSPlugin)


lazy val runDemo = taskKey[Unit]("runDemo")
lazy val bootstrapJS = taskKey[TaskKey[Attributed[sbt.File]]]("bootstrapJS")

//lazy val demo = project.in(file("demo")) dependsOn (scaladget) enablePlugins (ScalaJSBundlerPlugin) settings (defaultSettings: _*) settings(
lazy val demo = project.in(file("demo")) dependsOn (scaladget) enablePlugins (ScalaJSPlugin) settings(
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion/*,
  libraryDependencies += "org.scala-lang" %% "scala-reflect" % "provided"*/,
  //  enableReloadWorkflow := false,
  runDemo := {
    val demoTarget = target.value
    val demoResource = (resourceDirectory in Compile).value
    //  val bootstrapJS = (npmUpdate in Compile).value / "node_modules" / "bootstrap.native" / "dist" / "bootstrap-native.min.js"
    val demoJS = (fullOptJS in Compile).value

    IO.copyFile(demoJS.data, demoTarget / "js/demo.js")
    //  IO.copyFile(bootstrapJS, demoTarget / "js/bootstrap.-native.min.js")


    IO.copyFile(demoResource / "bootstrap-native.html", demoTarget / "bootstrap-native.html")
    IO.copyFile(demoResource / "flowchart.html", demoTarget / "flowchart.html")
    IO.copyDirectory(demoResource / "js", demoTarget / "js")
    IO.copyDirectory(demoResource / "css", demoTarget / "css")
    IO.copyDirectory(demoResource / "fonts", demoTarget / "fonts")
    IO.copyDirectory(demoResource / "img", demoTarget / "img")
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
