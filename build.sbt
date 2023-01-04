import sbt._
import Keys._
//import execnpm.ExecNpmPlugin.autoImport._
//import execnpm.NpmDeps._


val aceVersion = "1.4.3"
val aceDiffVersion = "2.3.0"
val bootstrapNativeVersion = "3.0.14-f"
val bootstrapIcons = "1.4.0"
val bootstrapSliderVersion = "10.4.0"
val nouiSliderVersion = "15.5.0"
val highlightVersion = "10.4.1"
val lunrVersion = "2.1.5"

//2.13
val jsextVersion = "0.10"
val laminarVersion = "0.14.2"
val scalaJSdomVersion = "2.0.0"
//val sortableVersion = "1.13.0"
val sourceCodeVersion = "0.2.7"
val scalaJsMarkedVersion = "1.0.2"
val scalaJSortableVersion = "0.8"


ThisBuild / organization := "org.openmole.scaladget"
name := "scaladget"


import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

ThisBuild / scalaVersion := "3.1.2"

ThisBuild / crossScalaVersions := Seq("2.13.8", "3.0.0")

ThisBuild / pomIncludeRepository := { _ => false }

resolvers += Resolver.sonatypeRepo("releases")

ThisBuild / licenses := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/"))

ThisBuild / homepage := Some(url("https://github.com/openmole/scala-js-plotlyjs"))

ThisBuild / scmInfo := Some(ScmInfo(url("https://github.com/openmole/scaladget.git"), "git@github.com:openmole/scaladget.git"))

ThisBuild / pomExtra := (
  <developers>
    <developer>
      <id>mathieu.leclaire</id>
      <name>Mathieu Leclaire</name>
    </developer>
  </developers>
  )

ThisBuild / releasePublishArtifactsAction := PgpKeys.publishSigned.value

releaseVersionBump := sbtrelease.Version.Bump.Minor

releaseTagComment := s"Releasing ${(ThisBuild / version).value}"

releaseCommitMessage := s"Bump version to ${(ThisBuild / version).value}"

sonatypeProfileName := "org.openmole"


ThisBuild / publishConfiguration := publishConfiguration.value.withOverwrite(true)

ThisBuild / publishTo := sonatypePublishToBundle.value

ThisBuild / publishMavenStyle := true

ThisBuild / releaseCrossBuild := true


releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  tagRelease,
  releaseStepCommandAndRemaining("+publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

lazy val scalaJsDom = libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion
lazy val laminar = libraryDependencies += "com.raquo" %%% "laminar" % laminarVersion
lazy val sourceCode = libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion
lazy val jsext = libraryDependencies += "org.querki" %%% "querki-jsext" % jsextVersion cross (CrossVersion.for3Use2_13)

lazy val ace = project.in(file("ace")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  jsext,
  Compile / npmDependencies += "ace-builds" -> aceVersion
) dependsOn (bootstrapnative)
//
//lazy val aceDiff = project.in(file("acediff")) enablePlugins (ScalaJSBundlerPlugin) dependsOn (ace) settings(
//  scalaJsDom,
//  jsext,
//  npmDeps in Compile += Dep("ace-diff", aceDiffVersion, List("ace-diff.min.js", "ace-diff.min.css"), true)
//)
//
lazy val nouislider = project.in(file("nouislider")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  jsext,
  laminar,
  Compile / npmDependencies += "nouislider"-> nouiSliderVersion
)

//
lazy val bootstrapnative = project.in(file("bootstrapnative")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  // libraryDependencies += "org.openmole" %%% "sortable-js-facade" % scalaJSortableVersion,
  laminar,
  Compile / npmDependencies += "bootstrap.native" -> bootstrapNativeVersion,
  //npmDependencies in Compile += "sortablejs" -> sortableVersion
) dependsOn (tools)
//
//
lazy val highlightjs = project.in(file("highlightjs")) enablePlugins (ScalaJSBundlerPlugin) settings(
  jsext,
  scalaJsDom,
  Compile / npmDependencies += "highlight.js" -> highlightVersion
)

lazy val lunr = project.in(file("lunr")) enablePlugins (ScalaJSBundlerPlugin) settings (
  Compile / npmDependencies += "lunr" -> lunrVersion
  )

lazy val svg = project.in(file("svg")) enablePlugins (ScalaJSPlugin) settings(
  laminar,
  scalaJsDom
) dependsOn (tools)


lazy val tools = project.in(file("tools")) enablePlugins (ScalaJSBundlerPlugin) settings(
  scalaJsDom,
  laminar
)

lazy val runBootstrapDemo = taskKey[Unit]("runBootsrapDemo")

lazy val bootstrapDemo = project.in(file("bootstrapDemo")) enablePlugins (ScalaJSBundlerPlugin) settings(
  publishArtifact := false,
  publish := {},
  publishLocal := {},
  test := println("Tests disabled"),
  laminar,
  sourceCode,
  scalaJSLinkerConfig := scalaJSLinkerConfig.value.withSourceMap(false),
  scalaJSUseMainModuleInitializer := true,
  Test / requireJsDomEnv := true,
  webpackNodeArgs := Seq("--openssl-legacy-provider"),
  runBootstrapDemo := {
    val demoResource = (Compile / resourceDirectory).value
    val jsBuild = (Compile / fastOptJS / webpack).value.head.data

    IO.copyFile(jsBuild, target.value / "js/demobootstrapnative.js")
    IO.copyDirectory(demoResource, target.value)

  }
) dependsOn(ace, bootstrapnative, tools, highlightjs, nouislider, lunr)


lazy val runSVGDemo = taskKey[Unit]("runSVGDemo")

lazy val svgDemo = project.in(file("svgDemo")) enablePlugins (ScalaJSBundlerPlugin) settings(
  publishArtifact := false,
  publish := {},
  publishLocal := {},
  test := println("Tests disabled"),
  laminar,
  sourceCode,
  scalaJSUseMainModuleInitializer := true,
  Test / requireJsDomEnv := true,
  runSVGDemo := {
    val demoResource = (Compile / resourceDirectory).value
    val jsBuild = (Compile / fastOptJS / webpack).value.head.data

    IO.copyFile(jsBuild, target.value / "js/demosvg.js")
    IO.copyDirectory(demoResource, target.value)
  }
) dependsOn(svg, tools, bootstrapnative, highlightjs)


lazy val runFlowchartDemo = taskKey[Unit]("runFlowchartDemo")

lazy val flowchartDemo = project.in(file("flowchartDemo")) enablePlugins (ScalaJSBundlerPlugin) settings(
  publishArtifact := false,
  publish := {},
  publishLocal := {},
  test := println("Tests disabled"),
  laminar,
  sourceCode,
  scalaJSUseMainModuleInitializer := true,
  Test / requireJsDomEnv := true,
  runFlowchartDemo := {
    val demoResource = (Compile / resourceDirectory).value
    val jsBuild = (Compile / fastOptJS / webpack).value.head.data

    IO.copyFile(jsBuild, target.value / "js/flowchart.js")
    IO.copyDirectory(demoResource, target.value)
  }
) dependsOn(svg, tools, bootstrapnative)