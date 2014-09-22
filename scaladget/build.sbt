import com.typesafe.sbt.pgp.PgpKeys._

organization := "fr.iscpif"

name := "scaladget"

scalaVersion := "2.11.2"

packagedArtifacts in file(".") := Map.empty

publish in file(".") := {}

publishSigned := {}
