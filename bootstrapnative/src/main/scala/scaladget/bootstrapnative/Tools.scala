package scaladget.bootstrapnative

import com.raquo.laminar.api.L._
import scaladget.bootstrapnative.Popup.{Bottom, HoverPopup, PopupPosition, PopupType}

object Tools {


  case class MyPopoverBuilder(element: HtmlElement,
                              popoverElement: HtmlElement,
                              position: PopupPosition = Popup.Right,
                              trigger: PopupType = HoverPopup) {

    private val open = Var(false)

    private def openTrigger = open.update(!_)

    element.amend(
      trigger match {
        case HoverPopup => onMouseOver --> { _ => openTrigger }
        case _ => onClick --> { _ => openTrigger }
      },
      onMouseLeave --> { _ => open.set(false) }
    )

    def render = {
      if (!org.scalajs.dom.document.body.classList.contains("mypopover")) {

        val elementBox = element.ref.getBoundingClientRect()
        val scrollTop = org.scalajs.dom.document.body.scrollTop
        val scrollLeft = org.scalajs.dom.document.body.scrollLeft
        val popoverPadding = 7

        val ppWithArrow = div(
          display.flex, flexDirection.row,
          padding := popoverPadding.toString,
          top := (elementBox.top + scrollTop - popoverElement.ref.clientHeight - 2 * popoverPadding).toString,
          left := (elementBox.left + 51 + scrollLeft).toString,
          div(cls := "left-arrow"),
          cls := "mypopover bs-fade",
          opacity <-- open.signal.map { o => if (o) 1 else 0 },
          popoverElement
        )
        com.raquo.laminar.api.L.render(org.scalajs.dom.document.body, ppWithArrow)
      }
      element
    }
  }
}
