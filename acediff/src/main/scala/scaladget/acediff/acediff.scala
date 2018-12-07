package scaladget.acediff

import scala.scalajs.js
import org.scalajs.dom.raw.HTMLElement
import org.querki.jsext._

import scala.scalajs.js
import js.annotation._
import js.|


@js.native
@JSGlobal
class AceDiff protected () extends js.Object {
  def this(opts: AceDiffConstructor) = this()
  def getEditors(): js.Any = js.native
  def setOptions(options: AceDiffOpts): Unit = js.native
  def getNumDiffs(): Double = js.native
  def diff(): Unit = js.native
  def destroy(): Unit = js.native
}

@js.native
trait AceDiffConstructor extends js.Object {
  var element: js.UndefOr[String | HTMLElement] = js.native

  var left: js.UndefOr[LR] = js.native

  var right: js.UndefOr[LR] = js.native
}

object  AceDiffConstructorBuilder extends AceDiffConstructorBuilder(noOpts)

class AceDiffConstructorBuilder(val dict: OptMap) extends JSOptionBuilder[AceDiffConstructor, AceDiffConstructorBuilder](new AceDiffConstructorBuilder(_)) {

  def element(v: String | HTMLElement) = jsOpt("element", v)

  def left(v: LR) = jsOpt("left", v)

  def right(v: LR) = jsOpt("right", v)
}

@js.native
@JSGlobal
class LR extends js.Object

object LR extends LRBuilder(noOpts) {
  implicit def LRBuilderToLR(LRBuilder: LRBuilder): LR = LRBuilder._result
}

class LRBuilder(val dict: OptMap) extends JSOptionBuilder[LR, LRBuilder](new LRBuilder(_)) {

  def content(v: String | HTMLElement) = jsOpt("content", v)

  def mode(v: String) = jsOpt("mode", v)

  def theme(v: String) = jsOpt("theme", v)

  def editable(v: Boolean) = jsOpt("editable", v)

  def copyLinkEnabled(v: Boolean) = jsOpt("copyLinkEnabled", v)
}

@js.native
trait AceDiffOpts extends js.Object {
  var mode: String = js.native
  var theme: String = js.native
  var diffGranularity: String = js.native
  var showDiffs: Boolean = js.native
  var showConnectors: Boolean = js.native
  var maxDiffs: Double = js.native
  var left: LR = js.native
  var right: LR = js.native
}