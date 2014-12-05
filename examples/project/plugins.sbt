resolvers ++= Seq(Resolver.sonatypeRepo("snapshots"),
Resolver.sonatypeRepo("releases"),
  Resolver.url("scala-js-releases",
    url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
      Resolver.ivyStylePatterns))

resolvers += "Typesafe repository" at
  "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("fr.iscpif" %% "jsmanager" % "0.7.0-SNAPSHOT")
