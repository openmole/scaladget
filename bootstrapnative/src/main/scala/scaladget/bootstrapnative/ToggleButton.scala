//package scaladget.bootstrapnative
//
//
//import com.raquo.airstream.signal.Signal
//import com.raquo.laminar.api.L._
//
//object ToggleButton {
//  def apply(default: Boolean, valueOn: String = "ON", valueOff: String = "OFF", toggleModifier: Modifier[HtmlElement]): ToggleButton =
//    new ToggleButton(default, valueOn, valueOff, toggleModifier)
//}
//
//class ToggleButton(default: Boolean, valueOn: String = "ON", valueOff: String = "OFF", toggleModifier: Modifier[HtmlElement]) {
//
//  val position = Var(default)
//
//  position.signal.map { _ =>
//    println("MAP")
//    mainDiv.ref.style.marginLeft = toggleMargin(position.now)
//  }
//  //  position.triggerLater {
//  //    onToggled()
//  //    mainDiv.style.marginLeft = toggleMargin(position.now)
//  //  }
//
//  def toggleMargin(b: Boolean) = if (b) "0px" else "-53px"
//
//  lazy val mainDiv = div(cls := "bootstrap-switch-container", width := 159, marginLeft := toggleMargin(default),
//    span(cls := "bootstrap-switch-handle-on bootstrap-switch-primary", width := "53", toggleModifier, onClick --> position.signal.mapToValue(false)),
//    span(cls := "bootstrap-switch-label", width := "150", "&nbsp", toggleModifier, onClick --> position.signal.map {
//      !_
//    }),
//    span(cls := "bootstrap-switch-handle-off bootstrap-switch-default", width := "53", valueOff, toggleModifier, onClick --> position.signal.mapToValue(true)),
//    input(idAttr := "switch-state", checked := true, `type` := "checkbox")
//  )
//
//  lazy val render = div(cls := Rx {
//    s"bootstrap-switch-id-switch-state bootstrap-switch bootstrap-switch-wrapper bootstrap-switch-animate ${if (position()) "bootstrap-switch-on" else "bootstrap-switch-off"}"
//  }, width := 106)(mainDiv)
//}
