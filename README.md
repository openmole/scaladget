scaladget
=========

Scaladget is a library for generating html pages based on famous js libraries such as:
* D3.js
* Bootstrap.js
* jQuery

Scaladget provides high level functions to generates easily ready to use html pages. 
It takes advantages of the [(scala-js library)][1] and transform all the scala code into javascript.
The output is store into 2 directories *css* and *js* and optionaly a html page running your script.

Scaladget is composed of:
- a Scaladget library containing the API,
- a jsManager plugin dealing with the code generation and file copies.

Usage
------

Depends on the scaladget library
```scala
resolvers += Resolver.sonatypeRepo("snapshots")
libraryDependencies += "fr.iscpif" %% "scaladget" % "0.1.0-SNAPSHOT"
```

Adds the jsManager plugin (in plugins.sbt)

```scala
resolvers += Resolver.sonatypeRepo("snapshots")

addSbtPlugin("fr.iscpif" %% "jsmanager" % "0.1.0-SNAPSHOT")
```

Add the *jsManagerSettings* to your project settings contained in the JSManagerPlugin
```scala
import fr.iscpif.jsmanager.JSManagerPlugin._
```

Several additonal settings can be added:
 **outputPath**: to specify a particular output path (instead of the default target one). *Ex: "/tmp/*
 **jsCall**: the string calling your javascript function: *Ex: "Form().run();"*






[1]: http://www.scala-js.org/