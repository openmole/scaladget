resolvers += "Sonatype snapshots" at "http://oss.sonatype.org/content/repositories/snapshots/"

addSbtPlugin("org.scala-lang.modules.scalajs" % "scalajs-sbt-plugin" % "0.5.0")

addSbtPlugin("com.typesafe.sbt" % "sbt-scalariform" % "1.3.0")

//addSbtPlugin("com.typesafe.sbt" % "sbt-pgp" % "0.8.3")

scalariformSettings