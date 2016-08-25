scaladget
=========

Scaladget provides a scala facades of some famous javascript libraries. It relies on the [scala-js](http://www.scala-js.org/) project. Among them:
* [D3.js](d3js.org)
* [Bootstrap.js](http://getbootstrap.com/)
* [Ace Editor](http://ace.c9.io)
* [Tooltipster](http://iamceege.github.io/tooltipster/)


##Usage##
Just add this to your dependencies:
```sh
  libraryDependencies += "fr.iscpif" %%% "scaladget" % "0.8.1"
```

All the facades are intensively used in the [OpenMOLE project](https://github.com/openmole/openmole).

##D3 wrapper##
An example using the scaladget D3 wrapper can be found in the [ScalaWUI](https://github.com/mathieuleclaire/scalaWUI) project: [FlowChart](https://github.com/mathieuleclaire/scalaWUI/blob/master/client/src/main/scala/fr/iscpif/client/FlowChart.scala). It reproduces this [D3 flowchart](http://bl.ocks.org/cjrd/6863459).


##Bootstrap library##
The boostrap facade can be used directly or by means of a higher level library, which render transparent the use of simple elements such div, span, button, etc...

Here are 3 examples:
```scala
import fr.iscpif.scaladget.api.{ BootstrapTags ⇒ bs}
import bs._

// Build a 12 columm a Boostrap div composed of 3 column divs
bs.div("myClass")(
  bs.div(col_md_8)("Something long to write on 8 colomuns"),
  bs.div(col_md_2)("Something smaller on 2 colomuns"),
  bs.div(col_md_2)("Something smaller on 2 colomuns")
)

// A button with the Primary style, doing the action todo on click.
bs.button(
  "Save", 
  btn_primary + key("otherClass"), 
  () ⇒ {todo}
  )

// Build the famous Bootstrap dialog
modalDialog(
  "DialogID",
  headerDialog("My nice header"),
  bodyDialog("My body content"),
  footerDialog("My footer content")
)
```

Scaladget also provides a higher level API for the Bootstrap use: [BootstrapTags](https://github.com/mathieuleclaire/scaladget/blob/master/scaladget/src/main/scala/fr/iscpif/scaladget/api/BootstrapTags.scala)


##Tooltipster##
Here is an example for adding a tooltip on a div dynamically, using [scalatags](https://github.com/lihaoyi/scalatags).
```scala

import org.scalajs.jquery
import scalatags.JsDom._
import fr.iscpif.scaladget.mapping.tooltipster._
import fr.iscpif.scaladget.tooltipster._

 val ttdiv = div(
      title := "My message"
    ).render

    ttdiv.appendChild(h)
    val options = TooltipsterOptions.
      position("left)".
      delay(400)

    jquery.jQuery(ttdiv).tooltipster(options)
    ttdiv
```
