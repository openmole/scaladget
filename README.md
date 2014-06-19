scaladget
=========

Scaladget is a library for generating html pages based on famous js libraries such as:
* D3.js
* Bootstrap.js
* jQuery

Scaladget provides high level functions to generates easily ready to use html pages. 
It takes advantages of the [scala-js library][1] and transform all the scala code into javascript.
The output is store into 2 directories *css* and *js* and optionaly a html page running your script.

Scaladget is composed of:
- a *scaladget* library containing the API,
- a *jsManager* plugin dealing with the code generation and file copies.

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

 
 Here is a typical Build project:
 
 ```scala
import sbt._
import Keys._
import fr.iscpif.jsmanager.JSManagerPlugin._

object ExampleBuild extends Build {

  lazy val scaladgetExample= Project("examples",
    file("examples"),
    settings = Defaults.defaultSettings ++ jsManagerSettings ++ Seq(
      resolvers += Resolver.sonatypeRepo("snapshots"),
      libraryDependencies += "fr.iscpif" %% "scaladget" % "0.1.0-SNAPSHOT",
      jsCall := "Form().run();",
      outputPath := "/tmp"
      )
  )
}
```

The Library
------

The functions you want to exhibit in the generated javascript have to be preceeded by the scala-js annotation
 ```scala
@JSExport
```

The following imports are then required:
```scala
import scala.scalajs.js._
import annotation.JSExport
 ```

Examples
------

Examples can be run in the examples directory by running on of the two tasks:
* toJS: generates the *js* and *css* files only
* toHtml: generates *js*, *css* and a ready to use *html* file

To switch from one example to another, just change the invoked script function in the html directly or throuh the *jsCall* settings in the Build. 
For instance, the content of the Form.scala example:

 ```scala
 @JSExport
object Form {

  @JSExport
  def run() {
    title("My first html page with Scala !")

    body
      .backgroundColor("#d4d4d4")
      .color("#000")
      .h1.html("A Scaladget form example")

    form(body)
      .line.input("name", "Name", "", 6).input("email", "Email", "", 6)
      .line.input("firstname", "First Name", "")
      .group.button("save", "Save", State.PRIMARY).button("cancel", "Cancel")

  }
}
```

generates:
[[files|formExample.png]]


[1]: http://www.scala-js.org/