import com.typesafe.sbt.pgp.PgpKeys._

organization := "fr.iscpif"

name := "scaladget"

//scalaVersion := "2.11.1"

packagedArtifacts in file(".") := Map.empty

publish in file(".") := {}

publishSigned := {}
