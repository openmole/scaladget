package scaladget.bootstrapnative

import com.raquo.laminar.api.L.{*, given}
import com.raquo.laminar.nodes.ReactiveElement
import scaladget.bootstrapnative.bsn._
import scaladget.bootstrapnative.Popup.{Bottom, HoverPopup, PopupPosition, PopupType}

object Tools {


  case class MyPopoverBuilder(element: ReactiveElement[org.scalajs.dom.Element],
                              popoverElement: HtmlElement,
                              position: PopupPosition = Popup.Right,
                              trigger: PopupType = HoverPopup) {

    private val open = Var(false)

    private def openTrigger = open.update(!_)

    def render = {
      element.amend(
        onClick --> { _=> openTrigger},
        open.signal.expand(
        popoverElement.amend(
          onMouseLeave --> { _ => open.set(false) },
        )
      ))

    }
  }
}
