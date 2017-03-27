scaladget
=========

Scaladget provides a scala facades of some famous javascript libraries. It relies on the [scala-js](http://www.scala-js.org/) project. Among them:
* [Bootstrap-native.js](https://thednp.github.io/bootstrap.native/)
* [D3.js](d3js.org)
* [Ace Editor](http://ace.c9.io)


## Usage ##
Just add this to your dependencies:
```sh
  libraryDependencies += "fr.iscpif" %%% "scaladget" % "0.9.2-SNAPSHOT"
```

All the facades are intensively used in the [OpenMOLE project](https://github.com/openmole/openmole).

## Bootstrap-native library ##
[Demo](http://zebulon.iscpif.fr/~leclaire/scaladget/)

The boostrap-native facade (based on [https://thednp.github.io/bootstrap.native/](http://zebulon.iscpif.fr/~leclaire/scaladget/)) renders transparent the use of buttons, forms, modals, collapsers, selectors etc...
The bootstrap-native.min.js ([bootstrap-native 2.0.+ or more](https://www.jsdelivr.com/projects/bootstrap.native)) has to be set in a js folder. Then the whole html element (like div or span) as to be placed in :
```scala
withBootstrapNative{
 // your code here
 }
```
The reason is that the bootstrap-native.min.js file has to be loaded lazyly after the dom is loaded. Set you code in the ```withBootstrapNative``` guarantees the bootstrap-native script is loaded last.


Here is an example of bootstrap modal dialog creation in full scala:
```scala
  // Create the Modal dialog
    val modalDialog: ModalDialog =
      bs.ModalDialog(
        onopen = () => println("OPEN"),
        onclose = () => println("CLOSE")
      )

    // Append header, body, footer elements
    modalDialog header div("Header")
    modalDialog footer bs.buttonGroup()(
      ModalDialog.closeButton(modalDialog, btn_info, "OK"),
      ModalDialog.closeButton(modalDialog, btn_default, "Cancel")
    )

    // Build the modal dialog and the trigger button
    tags.span(
      modalDialog.dialog,
      bs.button("Modal !", btn_primary +++ sheet.marginLeft(5), () => modalDialog.show)
    ).render
```

Find more examples on the: [API Demo](http://zebulon.iscpif.fr/~leclaire/scaladget/)

Here is an example of scaladget intensive use in the [OpenMOLE project](https://github.com/openmole/openmole/blob/master/openmole/gui/client/org.openmole.gui.client.core/src/main/scala/org/openmole/gui/client/core/ScriptClient.scala)

## D3 wrapper ##
An example using the scaladget D3 wrapper can be found in the [ScalaWUI](https://github.com/mathieuleclaire/scalaWUI) project: [FlowChart](https://github.com/mathieuleclaire/scalaWUI/blob/master/client/src/main/scala/fr/iscpif/client/FlowChart.scala). It reproduces this [D3 flowchart](http://bl.ocks.org/cjrd/6863459).

