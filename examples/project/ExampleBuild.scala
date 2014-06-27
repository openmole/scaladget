import sbt._
import Keys._
import fr.iscpif.jsmanager.JSManagerPlugin._

object ExampleBuild extends Build {

  lazy val scaladgetExample = Project("examples",
    file("examples"),
    settings = Defaults.defaultSettings ++ jsManagerSettings ++ Seq(
      resolvers += Resolver.sonatypeRepo("snapshots"),
      libraryDependencies += "fr.iscpif" %%% "scaladget" % "0.1.0-SNAPSHOT",
      jsCall := "Form().run();"
      //outputPath := "/tmp"
    )
  )
}