resolvers += Resolver.sonatypeRepo("public")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "2.0.1")

addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.5.0")

//addSbtPlugin("ch.epfl.scala" % "sbt-scalajs-bundler" % "0.20.0")
addSbtPlugin("ch.epfl.scala" % "sbt-web-scalajs-bundler" % "0.20.0")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % "1.4.0")

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.7")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "3.9.2")

addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "1.0.0")
