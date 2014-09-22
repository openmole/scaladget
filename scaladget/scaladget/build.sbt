import scala.scalajs.sbtplugin.ScalaJSPlugin._
import com.typesafe.sbt.pgp.PgpKeys._

scalaJSSettings

organization := "fr.iscpif"

name := "scaladget"

version := "0.1.0"

scalaVersion := "2.11.2"

libraryDependencies ++= Seq("com.github.scala-incubator.io" %% "scala-io-core" % "0.4.3",
        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
        "com.scalatags" %%% "scalatags" % "0.4.0",
        "com.scalarx" %%% "scalarx" % "0.2.6")

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