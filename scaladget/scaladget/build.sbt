import scala.scalajs.sbtplugin.ScalaJSPlugin._

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
