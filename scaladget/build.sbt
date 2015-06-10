import org.scalajs.sbtplugin.ScalaJSPlugin
import com.typesafe.sbt.pgp.PgpKeys._

//scalaJSSettings
enablePlugins(ScalaJSPlugin)

organization := "fr.iscpif"

name := "scaladget"

version := "0.5.0"

scalaVersion := "2.11.6"

libraryDependencies ++= Seq(
  "org.scala-js" %%% "scalajs-dom" % "0.8.0",
  "be.doeraene" %%% "scalajs-jquery" % "0.8.0",
  "com.lihaoyi" %%% "scalatags" % "0.5.2",
  "com.lihaoyi" %%% "scalarx" % "0.2.8")

publishTo := {
  val nexus = "https://oss.sonatype.org/"
  if (version.value.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases" at nexus + "service/local/staging/deploy/maven2")
}

pomIncludeRepository := { _ => false}

licenses := Seq("Affero GPLv3" -> url("http://www.gnu.org/licenses/"))

homepage := Some(url("https://github.com/mathieuleclaire/scaladget"))

scmInfo := Some(ScmInfo(url("https://github.com/mathieuleclaire/scaladget.git"), "scm:git:git@github.com:mathieuleclaire/scaladget.git"))

pomExtra := {
  <developers>
    <developer>
      <id>mathieuleclaire</id>
      <name>Mathieu Leclaire</name>
      <url>https://github.com/mathieuleclaire/</url>
    </developer>
  </developers>
}
