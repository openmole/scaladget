import sbt._
import Keys._
import fr.iscpif.jsmanager.JSManagerPlugin._

object ExampleBuild extends Build {


  lazy val scaladgetExample = Project("examples",
    file("examples"),
    settings = Defaults.defaultSettings ++ jsManagerSettings ++ Seq(
    scalaVersion := "2.11.5",
    resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"),
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"),
      libraryDependencies ++= Seq("fr.iscpif" %%% "scaladget" % "0.3.0-SNAPSHOT",
        "org.scala-lang.modules.scalajs" %%% "scalajs-dom" % "0.6",
        "org.scala-lang.modules.scalajs" %%% "scalajs-jquery" % "0.6",
        "com.scalatags" %%% "scalatags" % "0.4.2",
        "com.scalarx" %%% "scalarx" % "0.2.6"),
     // jsCall := "TestJSPlumb().run();",
     // outputPath := "/home/mathieu/work/labo/testjsPlumb"
      jsCall := "TestScalaTags().run();",
      outputPath := "/tmp/out"
    )
  )
}
