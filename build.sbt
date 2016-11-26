import sbt._
import Keys._
import org.scalajs.core.tools.linker.backend.ModuleKind.{CommonJSModule, NoModule}

import ScalaJSBundlerPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._
import webscalajs.WebScalaJS.autoImport._


val ScalaVersion = "2.11.8"
val bootstrapNativeVersion = "1.1.0"
val scalatagsVersion = "0.6.2"
val scalaJSdomVersion = "0.9.1"
val rxVersion = "0.3.1"
val sourceCodeVersion = "0.1.2"

lazy val defaultSettings = Seq(
  organization := "fr.iscpif",
  version := "0.9.1-SNAPSHOT",
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
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  npmDependencies in Compile += "bootstrap.native" -> bootstrapNativeVersion
  ) enablePlugins(ScalaJSBundlerPlugin, ScalaJSWeb) settings (defaultSettings: _*)


lazy val runDemo = taskKey[Unit]("runDemo")

lazy val demo = project.in(file("demo")) dependsOn (scaladget) enablePlugins(ScalaJSBundlerPlugin, WebScalaJSBundlerPlugin) settings (defaultSettings: _*) settings(
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion,
  libraryDependencies += "org.scala-lang" % "scala-reflect" % ScalaVersion % "provided",
  scalaJSModuleKind := CommonJSModule,
  runDemo := {
    val demoTarget = target.value
    val demoResource = (resourceDirectory in Compile).value
    IO.copyFile((fullOptJS in Compile).value.data, demoTarget / "js/demo.js")
    (webpack in (Compile, fullOptJS)).value.foreach { f =>
      IO.copyFile(f, demoTarget / "js/scaladget.js")
    }
    IO.copyFile(demoResource / "index.html", demoTarget / "index.html")
    IO.copyDirectory(demoResource / "js", demoTarget / "js")
    IO.copyDirectory(demoResource / "css", demoTarget / "css")
    IO.copyDirectory(demoResource / "fonts", demoTarget / "fonts")
  }
  )