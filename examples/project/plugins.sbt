resolvers ++= Seq(Resolver.sonatypeRepo("snapshots")/*,
  Resolver.url("scala-js-releases",
    url("http://dl.bintray.com/content/scala-js/scala-js-releases"))(
      Resolver.ivyStylePatterns)*/)

addSbtPlugin("fr.iscpif" %% "jsmanager" % "0.1.0-SNAPSHOT")
