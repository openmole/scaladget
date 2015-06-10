scaladget
=========

Scaladget provides a scala mapping of famous javascript libraries. It relies on the [scala-js](http://www.scala-js.org/) project. Among them:
* [D3.js](d3js.org)
* [Bootstrap.js](http://getbootstrap.com/)
* [Ace Editor](http://ace.c9.io)

##Usage##
Just add this to your dependencies:
```sh
  libraryDependencies += "fr.iscpif" %%% "scaladget" % "0.5.0"
```

##Bootstrap library##
Scaladget also provides a higher level API for the Bootstrap use: [BootstrapTags](https://github.com/mathieuleclaire/scaladget/blob/master/scaladget/src/main/scala/fr/iscpif/scaladget/api/BootstrapTags.scala)
