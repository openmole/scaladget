import sbt._
import Keys._
import org.scalajs.core.tools.linker.backend.ModuleKind.CommonJSModule

import ScalaJSBundlerPlugin.autoImport._
import org.scalajs.sbtplugin.ScalaJSPlugin.autoImport._

import scalajsbundler.BundlingMode.LibraryOnly

val aceVersion = "1.3.0"
val bootstrapNativeVersion = "2.0.19"
val bootstrapSliderVersion = "10.0.0"
val d3Version = "4.12.0"
val highlightVersion = "9.10.0"
val querkiVersion = "0.8"
val lunrVersion = "2.1.4"
val rxVersion = "0.3.2"
val scalatagsVersion = "0.6.5"
val scalaJSdomVersion = "0.9.2"
val sortableFacadeVersion = "0.2.1"
val sortableVersion = "2.0.0"
val sourceCodeVersion = "0.1.2"

//crossScalaVersions := Seq("2.11.8", "2.12"),
scalaVersion in ThisBuild := "2.12.4"
organization in ThisBuild := "scaladget"
version in ThisBuild := "0.9.6-SNAPSHOT"
//resolvers in ThisBuild := Seq(Resolver.sonatypeRepo("snapshots")(
//  DefaultMavenRepository
//)
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

useYarn in ThisBuild := true

lazy val scalatags = libraryDependencies += "com.lihaoyi" %%% "scalatags" % scalatagsVersion
lazy val scalaJsDom = libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion
lazy val rx = libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion
lazy val querki = libraryDependencies += "org.querki" %%% "querki-jsext" % querkiVersion

lazy val ace = project.in(file("ace")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  npmDependencies in Compile += "ace" -> aceVersion
)

lazy val bootstrapslider = project.in(file("bootstrapslider")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  querki,
  npmDependencies in Compile += "bootstrap-slider" -> bootstrapSliderVersion,
  jsDependencies += ProvidedJS / "jquery-stub.js",
  webpackConfigFile := Some(baseDirectory.value / "jqueryConfig.config.js")
)

lazy val bootstrapnative = project.in(file("bootstrapnative")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  scalatags,
  querki,
  libraryDependencies += "net.scalapro" %%% "sortable-js-facade" % sortableFacadeVersion,
  npmDependencies in Compile += "bootstrap.native" -> bootstrapNativeVersion,
  npmDependencies in Compile += "sortable" -> sortableVersion,
) dependsOn (tools)

lazy val d3 = project.in(file("d3")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  npmDependencies in Compile += "d3" -> d3Version
)

lazy val lunr = project.in(file("lunr")) enablePlugins (ScalaJSBundlerPlugin) settings (
  npmDependencies in Compile += "lunr" -> lunrVersion
  )

lazy val svg = project.in(file("svg")) enablePlugins (ScalaJSPlugin) settings(
  scalatags,
  scalaJsDom
) dependsOn (tools)


lazy val tools = project.in(file("tools")) enablePlugins (ScalaJSPlugin) settings(
  scalatags,
  scalaJsDom,
  rx
)


lazy val runDemo = taskKey[Unit]("runDemo")
lazy val bootstrapJS = taskKey[TaskKey[Attributed[sbt.File]]]("bootstrapJS")

lazy val demo = project.in(file("demo")) enablePlugins (ScalaJSBundlerPlugin) settings(
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion,
  webpackBundlingMode := BundlingMode.LibraryAndApplication(),
  runDemo := {
    val demoTarget = target.value
    val demoResource = (resourceDirectory in Compile).value

    val demoJS = (fastOptJS in webpack in Compile).value

    IO.copyFile(demoJS.data, demoTarget / "js/demo.js")


    IO.copyFile(demoResource / "bootstrap-native.html", demoTarget / "bootstrap-native.html")
    IO.copyFile(demoResource / "flowchart.html", demoTarget / "flowchart.html")

    IO.copyDirectory(demoResource / "css", demoTarget / "css")
    IO.copyDirectory(demoResource / "fonts", demoTarget / "fonts")
    IO.copyDirectory(demoResource / "img", demoTarget / "img")
  }
) dependsOn(bootstrapnative, lunr, tools, svg)
