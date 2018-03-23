package demo

/*
 * Copyright (C) 24/08/16 // mathieu.leclaire@openmole.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import org.scalajs.dom.Element
import org.scalajs.dom.raw.{Event, HTMLButtonElement, HTMLElement, MouseEvent}

import scaladget.bootstrapnative.bsn._
import scaladget.tools._
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import rx._

object PopoverDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapnative.Popup._

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      marginRight := 5
    )

    //SIMPLE POPOVERS
    val simplePopovers = div(
      h2("Simple popovers"),
      div(paddingTop := 20)("Simple popovers containing text, or simple content with no events to be fired and with basic trigger modes (click, hover)."),
      button("Left", buttonStyle).popover(vForm(width := 100)(label("Nice content", label_danger).render, span("A important message").render), Left, title = Some("Check this !")).render,
      button("Title", buttonStyle).popover("Popover on hover with Title", Top, title = Some("Pop title")).render,
      button("Dismissable", buttonStyle).popover("Dismissible Popover on hover with Title", Top, HoverPopup, Some("Pop title"), true).render,
      inputTag("")(width := 320, marginTop := 10, placeholder := "Bottom (click)").popover("Tooltip on click on bottom", Bottom, ClickPopup).render
    )


    //MANUAL POPOVERS
    val BUTTON1_ID = uuID.short("b")
    val BUTTON2_ID = uuID.short("b")

    def actions(element: HTMLElement): Boolean = {
      element.id match {
        case BUTTON1_ID =>

          println(s"button 1 with ID $BUTTON1_ID clicked")
          element.parentNode.replaceChild(span("YO"), element)

          true
        case BUTTON2_ID =>
          println(s"button 2 with ID $BUTTON2_ID clicked")
          true
        case _ =>
          println("unknown")
          false
      }
    }

    def buildManualPopover(trigger: TypedTag[HTMLButtonElement], title: String, position: PopupPosition) = {

      val but1 = button("button1", btn_primary)(id := BUTTON1_ID, margin := 10)
      val but2 = button("button2", btn_primary)(id := BUTTON2_ID, margin := 10)
      lazy val pop1 = trigger.popover(
        div(
          span(
            but1,
            but2
          ).render
        ).toString,
        position,
        Manual
      )
      lazy val pop1Render = pop1.render

      pop1Render.onclick = { (e: Event) =>
        if (Popover.current.now == pop1) Popover.hide
        else Popover.toggle(pop1)
        e.stopPropagation
      }

      pop1Render
    }


    val manualPopovers = div(
      h2("Manual popovers"),
      div(paddingTop := 20)("Manual popovers, ie popovers built with custom interaction rules. " +
        "Here an exemple with a set of exclusive popovers, which keep alive when clicking on them."),
      div(paddingTop := 10)(
        (1 until 100).map { i =>
          buildManualPopover(
            button(s"Button ${i.toString}", buttonStyle), "Popover on click on bottom", Left)
        }
      )
    )

    org.scalajs.dom.document.body.onclick = { (e: Event) =>
      if (!actions(e.target.asInstanceOf[HTMLElement]))
        if (!e.target.asInstanceOf[HTMLElement].className.contains("popover-content"))
          Popover.hide
    }
    div(simplePopovers, manualPopovers).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Popover"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 9
  }
}