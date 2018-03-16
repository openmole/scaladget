package scaladget.bootstrapslider

import org.querki.jsext._
import org.scalajs.dom.raw.Element

import scala.scalajs.js
import js.|
import scala.scalajs.js.annotation._
import scaladget.bootstrapslider.Slider.SliderValue
import scaladget.bootstrapslider.SliderOptions.SliderTooltip

@js.native
trait SliderOptions extends js.Object {
  val id: js.UndefOr[String] = js.native
  val min: js.UndefOr[Double] = js.native
  val max: js.UndefOr[Double] = js.native
  val step: js.UndefOr[Double] = js.native
  val precision: js.UndefOr[Double] = js.native
  val orientation: js.UndefOr[String] = js.native
  val value: js.UndefOr[scala.Double | js.Array[scala.Double]] = js.native
  val range: js.UndefOr[Boolean] = js.native
  val selection: js.UndefOr[String] = js.native
  val tooltip: js.UndefOr[String] = js.native
  val tooltip_split: js.UndefOr[Boolean] = js.native
  val handle: js.UndefOr[String] = js.native
  val reversed: js.UndefOr[Boolean] = js.native
  val enabled: js.UndefOr[Boolean] = js.native

  def formatter(v: Double): String = js.native

  val natural_arrow_keys: js.UndefOr[Boolean] = js.native
  val ticks: js.UndefOr[js.Array[Double]] = js.native
  val ticks_positions: js.UndefOr[js.Array[Double]] = js.native
  val ticks_labels: js.UndefOr[js.Array[String]] = js.native
  val ticks_snap_bounds: js.UndefOr[Double] = js.native
  val scale: js.UndefOr[String] = js.native
  val focus: js.UndefOr[Boolean] = js.native
}


object SliderOptions extends SliderOptionsBuilder(noOpts) {

  type SliderTooltip = String
  val SHOW: SliderTooltip = "show"
  val HIDE: SliderTooltip = "hide"
  val ALWAYS: SliderTooltip = "always"
}

class SliderOptionsBuilder(val dict: OptMap) extends JSOptionBuilder[SliderOptions, SliderOptionsBuilder](new SliderOptionsBuilder(_)) {
  def id(v: String) = jsOpt("id", v)

  def min(v: Double) = jsOpt("min", v)

  def max(v: Double) = jsOpt("max", v)

  def step(v: Double) = jsOpt("step", v)

  def precision(v: Double) = jsOpt("precision", v)

  def orientation(v: String) = jsOpt("orientation", v)

  def value(v: scala.Double | js.Array[scala.Double]) = jsOpt("value", v)

  def range(v: Boolean) = jsOpt("range", v)

  def selection(v: String) = jsOpt("selection", v)

  def tooltip(v: SliderTooltip) = jsOpt("tooltip", v)

  def tooltip_split(v: Boolean) = jsOpt("tooltip_split", v)

  def handle(v: String) = jsOpt("handle", v)

  def reversed(v: Boolean) = jsOpt("reversed", v)

  def enabled(v: Boolean) = jsOpt("enabled", v)

  def natural_arrow_keys(v: Boolean) = jsOpt("natural_arrow_keys", v)

  def ticks(v: js.Array[Double]) = jsOpt("ticks", v)

  def ticks_positions(v: js.Array[Double]) = jsOpt("ticks_positions", v)

  def ticks_labels(v: js.Array[String]) = jsOpt("ticks_labels", v)

  def ticks_snap_bounds(v: Double) = jsOpt("ticks_snap_bounds", v)

  def scale(v: String) = jsOpt("scale", v)

  def focus(v: Boolean) = jsOpt("focus", v)
}

@js.native
trait ChangeValue extends js.Object {
  var oldValue: Double = js.native
  var newValue: Double = js.native
}

object Slider {
  type SliderValue = Double | js.Array[Double]
  type SliderEvent = String

  // This event fires when the slider is dragged
  lazy val STOP_SLIDER = "slide"

  // This event fires when dragging starts
  lazy val SLIDE_START = "slidestart"


  // This event fires when the dragging stops or has been clicked on
  lazy val SLIDE_STOP = "slideStop"


  // This event fires when the slider value has changed
  lazy val CHANGE = "change"


  // This event fires when the slider is enabled
  lazy val SLIDE_ENABLED = "slideEnabled"


  // This event fires when the slider is disabled
  lazy val SLIDE_DISABLED = "slideDisabled"
}

@js.native
@JSGlobal
class Slider(var element: Element | String = js.native, var options: SliderOptions = js.native) extends js.Object {

  def this(element: Element | String) = this(element, SliderOptions)

  def getValue(): SliderValue = js.native

  def setValue(newValue: SliderValue, triggerSlideEvent: Boolean = ???, triggerChangeEvent: Boolean = ???): js.Dynamic = js.native

  def destroy(): js.Dynamic = js.native

  def disable(): js.Dynamic = js.native

  def enable(): js.Dynamic = js.native

  def isEnabled(): Boolean = js.native

  def setAttribute(attribute: String, value: js.Any): js.Dynamic = js.native

  def getAttribute(attribute: String): js.Dynamic = js.native

  def refresh(): js.Dynamic = js.native

  def relayout(): js.Dynamic = js.native

  def on(event: Slider.SliderEvent, callback: js.Function0[Unit]): Slider = js.native
}
