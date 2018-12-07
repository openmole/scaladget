package scaladget.acediff

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import org.querki.jsext._
import scaladget.ace.Editor

import scala.scalajs.js
import js.annotation._
import js.|

object AceDiff {
  def apply() = AceDiffConstructorBuilder
}

@js.native
@JSGlobal
class AceDiff protected() extends js.Object {
  def this(opts: AceDiffConstructor) = this()

  def getEditors(): Editors = js.native

  def getNumDiffs(): Double = js.native

  def diff(): Unit = js.native

  def destroy(): Unit = js.native
}

@js.native
trait AceDiffConstructor extends js.Object {
  var element: js.UndefOr[String | HTMLElement] = js.native

  var left: js.UndefOr[LR] = js.native

  var right: js.UndefOr[LR] = js.native

  var mode: String = js.native

  var theme: String = js.native

  var diffGranularity: String = js.native

  var showDiffs: Boolean = js.native

  var showConnectors: Boolean = js.native

  var maxDiffs: Double = js.native
}

object AceDiffConstructorBuilder extends AceDiffConstructorBuilder(noOpts)

class AceDiffConstructorBuilder(val dict: OptMap) extends JSOptionBuilder[AceDiffConstructor, AceDiffConstructorBuilder](new AceDiffConstructorBuilder(_)) {

  def element(v: String | HTMLElement) = jsOpt("element", v)

  def left(v: LR) = jsOpt("left", v)

  def right(v: LR) = jsOpt("right", v)

  def showDiffs(v: Boolean) = jsOpt("showDiffs", v)

  def mode(v: String) = jsOpt("mode", v)

  def theme(v: String) = jsOpt("theme", v)

  def diffGranularity(v: String) = jsOpt("diffGranularity", v)

  def showConnectors(v: Boolean) = jsOpt("showConnectors", v)

  def maxDiffs(v: Double) = jsOpt("maxDiffs", v)

  def build = new AceDiff(this)
}

@js.native
trait LR extends js.Object {
  def content: String = js.native

  var mode: js.UndefOr[String] = js.native

  var theme: js.UndefOr[String] = js.native

  var editable: js.UndefOr[Boolean] = js.native

  var copyLinkEnabled: js.UndefOr[Boolean] = js.native

}

object LR extends LRBuilder(noOpts)

class LRBuilder(val dict: OptMap) extends JSOptionBuilder[LR, LRBuilder](new LRBuilder(_)) {

  def content(v: String) = jsOpt("content", v)

  def mode(v: String) = jsOpt("mode", v)

  def theme(v: String) = jsOpt("theme", v)

  def editable(v: Boolean) = jsOpt("editable", v)

  def copyLinkEnabled(v: Boolean) = jsOpt("copyLinkEnabled", v)
}

@js.native
trait Editors extends js.Object {
  var right: Editor = js.native

  var left: Editor = js.native
}