import sbt._
import Keys._
import execnpm.ExecNpmPlugin.autoImport._
import execnpm.NpmDeps._

val aceVersion = "1.4.3"
val aceDiffVersion = "2.3.0"
val bootstrapNativeVersion = "2.0.26"
val bootstrapSwitchVersion = "3.3.4"
val bootstrapVersion = "3.4.1"
val bootstrapSliderVersion = "10.4.0"
val highlightVersion = "9.12.0"
val lunrVersion = "2.1.6"

//2.13
val jsextVersion = "0.10"
val rxVersion = "0.4.2"
val scalatagsVersion = "0.9.1"
val scalaJSdomVersion = "1.0.0"
val sortableVersion = "1.10.2"
val sourceCodeVersion = "0.2.1"
val scalaJsMarkedVersion = "1.0.2"
val scalaJSortableVersion = "0.6"


organization in ThisBuild := "org.openmole.scaladget"
name := "scaladget"


import sbtrelease.ReleasePlugin.autoImport.ReleaseTransformations._

scalaVersion in ThisBuild := "2.13.2"

crossScalaVersions in ThisBuild := Seq("2.12.11", "2.13.2")

pomIncludeRepository in ThisBuild := { _ => false }

resolvers += Resolver.sonatypeRepo("releases")

licenses in ThisBuild := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/"))

homepage in ThisBuild := Some(url("https://github.com/openmole/scala-js-plotlyjs"))

scmInfo in ThisBuild := Some(ScmInfo(url("https://github.com/openmole/scaladget.git"), "git@github.com:openmole/scaladget.git"))

pomExtra in ThisBuild := (
  <developers>
    <developer>
      <id>mathieu.leclaire</id>
      <name>Mathieu Leclaire</name>
    </developer>
  </developers>
  )

releasePublishArtifactsAction in ThisBuild := PgpKeys.publishSigned.value

releaseVersionBump := sbtrelease.Version.Bump.Minor

releaseTagComment := s"Releasing ${(version in ThisBuild).value}"

releaseCommitMessage := s"Bump version to ${(version in ThisBuild).value}"

sonatypeProfileName := "org.openmole"


publishConfiguration in ThisBuild := publishConfiguration.value.withOverwrite(true)

publishTo in ThisBuild := sonatypePublishToBundle.value

publishMavenStyle in ThisBuild := true

releaseCrossBuild in ThisBuild := true


releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  setReleaseVersion,
  tagRelease,
  releaseStepCommand("publishSigned"),
  releaseStepCommand("sonatypeBundleRelease"),
  setNextVersion,
  commitNextVersion,
  pushChanges
)

lazy val scalatags = libraryDependencies += "com.lihaoyi" %%% "scalatags" % scalatagsVersion
lazy val scalaJsDom = libraryDependencies += "org.scala-js" %%% "scalajs-dom" % scalaJSdomVersion
lazy val rx = libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion
lazy val jsext = libraryDependencies += "org.querki" %%% "querki-jsext" % jsextVersion

lazy val ace = project.in(file("ace")) enablePlugins (ExecNpmPlugin) settings(
  scalaJsDom,
  jsext,
  npmDeps in Compile += Dep("ace-builds", aceVersion, List("ace.js"))
)

lazy val aceDiff = project.in(file("acediff")) enablePlugins (ExecNpmPlugin) dependsOn (ace) settings(
  scalaJsDom,
  jsext,
  npmDeps in Compile += Dep("ace-diff", aceDiffVersion, List("ace-diff.min.js", "ace-diff.min.css"), true)
)

lazy val bootstrapslider = project.in(file("bootstrapslider")) enablePlugins (ExecNpmPlugin) settings(
  scalaJsDom,
  scalatags,
  jsext,
  npmDeps in Compile += Dep("bootstrap-slider", bootstrapSliderVersion, List("bootstrap-slider.min.js", "bootstrap-slider.min.css"))
)

lazy val bootstrapnative = project.in(file("bootstrapnative")) enablePlugins (ExecNpmPlugin) settings(
  scalaJsDom,
  scalatags,
  jsext,
  libraryDependencies += "org.openmole" %%% "sortable-js-facade" % scalaJSortableVersion,
  npmDeps in Compile += Dep("bootstrap.native", bootstrapNativeVersion, List("bootstrap-native.min.js")),
  npmDeps in Compile += Dep("bootstrap-switch", bootstrapSwitchVersion, List("bootstrap-switch.min.css")),
  npmDeps in Compile += Dep("bootstrap", bootstrapVersion, List("bootstrap.min.css")),
  npmDeps in Compile += Dep("sortablejs", sortableVersion, List("Sortable.min.js")),
) dependsOn (tools)

lazy val lunr = project.in(file("lunr")) enablePlugins (ExecNpmPlugin) settings (
  npmDeps in Compile += Dep("lunr", "2.1.5", List("lunr.js"))
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

lazy val demo = project.in(file("demo")) enablePlugins (ExecNpmPlugin) settings(
  publishArtifact := false,
  publish := {},
  publishLocal := {},
  test := println("Tests disabled"),
  libraryDependencies += "com.lihaoyi" %%% "scalarx" % rxVersion,
  libraryDependencies += "com.lihaoyi" %%% "sourcecode" % sourceCodeVersion,
  //libraryDependencies ++= Seq("com.dbrsn.scalajs.react.components" %%% "react-syntax-highlighter" % "0.3.1"),
  //libraryDependencies += "com.github.karasiq" %%% "scalajs-marked" % scalaJsMarkedVersion,
  npmDeps in Compile += Dep("ace-builds", aceVersion, List("mode-scala.js", "theme-github.js", "ext-language_tools.js"), true),
  runDemo := {

    val demoTarget = target.value
    val demoResource = (resourceDirectory in Compile).value

    IO.copyFile((fullOptJS in Compile).value.data, demoTarget / "js/demo.js")
    IO.copyFile(dependencyFile.value, demoTarget / "js/deps.js")
    IO.copyDirectory(cssFile.value, demoTarget / "css")
    IO.copyDirectory(demoResource, demoTarget)
  }
) dependsOn(bootstrapnative, bootstrapslider, lunr, tools, svg, ace, aceDiff)
