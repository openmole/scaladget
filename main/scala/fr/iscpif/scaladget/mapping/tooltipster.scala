package fr.iscpif.scaladget.mapping.tooltipster

import scala.scalajs.js
import org.querki.jsext._
import org.querki.jquery._
import js.annotation._

@js.native
trait Tooltipster extends js.Object {
  def tooltipster(options: TooltipsterOptions): JQuery = js.native
}

@js.native
trait TooltipsterOptions extends  js.Object
object TooltipsterOptions extends TooltipsterOptionBuilder(noOpts)


class TooltipsterOptionBuilder(val dict:OptMap) extends
JSOptionBuilder[TooltipsterOptions, TooltipsterOptionBuilder](new TooltipsterOptionBuilder(_)) {

  /**
   * Determines how the tooltip will animate in and out. Feel free to modify or create custom transitions in the tooltipster.css file. In IE9 and 8, all animations default to a JavaScript generated, fade animation. Default: 'fade'
   * fade, grow, swing, slide, fall
   */
  def animation(v: String) = jsOpt("animation", v)
  /**
   * Adds the "speech bubble arrow" to the tooltip. Default: true
   */
  def arrow(v: Boolean) = jsOpt("arrow", v)
  /**
   * Select a specific color for the "speech bubble arrow". Default: will inherit the tooltip's background color
   * hex code / rgb
   */
  def arrowColor(v: js.Any) = jsOpt("arrowColor", v)
  /**
   * If autoClose is set to false, the tooltip will never close unless you call the 'close' method yourself. Default: true
   */
  def autoClose(v: Boolean) = jsOpt("autoClose", v)
  /**
   * If set, this will override the content of the tooltip. Default: null
   * type String, jQuery object
   */
  def content(v: js.Any) = jsOpt("content", v)
  /**
   * If the content of the tooltip is provided as a String, it is displayed as plain text by default. If this content should actually be interpreted as HTML, set this option to true. Default: false
   */
  def contentAsHTML(v: Boolean) = jsOpt("contentAsHTML", v)
  /**
   * If you provide a jQuery object to the 'content' option, this sets if it is a clone of this object that should actually be used. Default: true
   */
  def contentCloning(v: Boolean) = jsOpt("contentCloning", v)
  /**
   * Delay how long it takes (in milliseconds) for the tooltip to start animating in. Default: 200
   */
  def delay(v: Int) = jsOpt("delay", v)
  /**
   * Set a minimum width for the tooltip. Default: 0 (auto width)
   */
  def minWidth(v: Int) = jsOpt("minWidth", v)
  /**
   * Set a max width for the tooltip. If the tooltip ends up being smaller than the set max width, the tooltip's width will be set automatically. Default: 0 (no max width)
   */
  def maxWidth(v: Int) = jsOpt("maxWidth", v)
  /**
   * If using the iconDesktop or iconTouch options, this sets the content for your icon. Default: '(?)'
   * type String, jQuery object
   */
  def icon(v: js.Any) = jsOpt("icon", v)
  /**
   * If you provide a jQuery object to the 'icon' option, this sets if it is a clone of this object that should actually be used. Default: true
   */
  def iconCloning(v: Boolean) = jsOpt("iconCloning", v)
  /**
   * Generate an icon next to your content that is responsible for activating the tooltip on non-touch devices. Default: false
   */
  def iconDesktop(v: Boolean) = jsOpt("iconDesktop", v)
  /**
   * If using the iconDesktop or iconTouch options, this sets the class on the icon (used to style the icon). Default: 'tooltipster-icon'
   */
  def iconTheme(v: String) = jsOpt("iconTheme", v)
  /**
   * Generate an icon next to your content that is responsible for activating the tooltip on touch devices (tablets, phones, etc). Default: false
   */
  def iconTouch(v: Boolean) = jsOpt("iconTouch", v)
  /**
   * Give users the possibility to interact with the tooltip. Unless autoClose is set to false, the tooltip will still close if the user moves away from or clicks out of the tooltip. Default: false
   */
  def interactive(v: Boolean) = jsOpt("interactive", v)
  /**
   * If the tooltip is interactive and activated by a hover event, set the amount of time (milliseconds) allowed for a user to hover off of the tooltip activator (origin) on to the tooltip itself - keeping the tooltip from closing. Default: 350
   */
  def interactiveTolerance(v: Int) = jsOpt("interactiveTolerance", v)
  /**
   * Offsets the tooltip (in pixels) farther left/right from the origin. Default: 0
   */
  def offsetX(v: Int) = jsOpt("offsetX", v)
  /**
   * Offsets the tooltip (in pixels) farther up/down from the origin. Default: 0
   */
  def offsetY(v: Int) = jsOpt("offsetY", v)
  /**
   * If true, only one tooltip will be allowed to be active at a time. Non-autoclosing tooltips will not be closed though. Default: false
   */
  def onlyOne(v: Boolean) = jsOpt("onlyOne", v)
  /**
   * Set the position of the tooltip. Default: 'top'
   * right, left, top, top-right, top-left, bottom, bottom-right, bottom-left
   */
  def position(v: String) = jsOpt("position", v)
  /**
   * Will reposition the tooltip if the origin moves. As this option may have an impact on performance, we suggest you enable it only if you need to. Default: false
   */
  def positionTracker(v: Boolean) = jsOpt("positionTracker", v)
  /**
   * Set the speed of the animation. Default: 350
   */
  def speed(v: Int) = jsOpt("speed", v)
  /**
   * How long the tooltip should be allowed to live before closing. Default: 0 (disabled)
   */
  def timer(v: Int) = jsOpt("timer", v)
  /**
   * Set the theme used for your tooltip. Default: 'tooltipster-default'
   */
  def theme(v: String) = jsOpt("theme", v)
  /**
   * If set to false, tooltips will not show on pure-touch devices, unless you open them yourself with the 'show' method. Touch gestures on devices which also have a mouse will still open the tooltips though. Default: true
   */
  def touchDevices(v: Boolean) = jsOpt("touchDevices", v)
  /**
   * Set how tooltips should be activated and closed. See the advanced section to learn how to build custom triggers. Default: 'hover'
   * hover, click, custom
   */
  def trigger(v: String) = jsOpt("trigger", v)
  /**
   * If a tooltip is open while its content is updated, play a subtle animation when the content changes. Default: true
   */
  def updateAnimation(v: Boolean) = jsOpt("updateAnimation", v)

}