package scaladget.bootstrapnative

import rx._
import scalatags.JsDom.all._
import scaladget.tools._

object ToggleButton {
  def apply(default: Boolean, valueOn: String = "ON", valueOff: String = "OFF"): ToggleButton = new ToggleButton(default, valueOn, valueOff)
}

class ToggleButton(default: Boolean, valueOn: String = "ON", valueOff: String = "OFF") {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val position = Var(default)

  position.trigger {
    mainDiv.style.marginLeft = toggleMargin(position.now)
  }

  def toggleMargin(b: Boolean) = if (b) "0px" else "-53px"

  lazy val mainDiv = div(`class` := "bootstrap-switch-container", width := 159, marginLeft := toggleMargin(default))(
    span(`class` := "bootstrap-switch-handle-on bootstrap-switch-primary", width := 53, valueOn, onclick := { () =>position() = false}),
    span(`class` := "bootstrap-switch-label", width := 150, raw("&nbsp"), onclick := { () =>position() = !position.now }),
    span(`class` := "bootstrap-switch-handle-off bootstrap-switch-default", width := 53, valueOff, onclick := { () => position() = true}),
    input(id := "switch-state", checked := "checked", `type` := "checkbox")
  ).render

  lazy val render = div(`class` := Rx {
    s"bootstrap-switch-id-switch-state bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-animate ${if (position()) "bootstrap-switch-on" else "bootstrap-switch-off"}"
  }, width := 106)(mainDiv)
}
