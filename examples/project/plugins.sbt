resolvers ++= Seq("Openmole Nexus" at "http://maven.openmole.org/snapshots",
  "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea" % "1.6.0")

addSbtPlugin("fr.iscpif" %% "jsmanager" % "0.1.0-SNAPSHOT")