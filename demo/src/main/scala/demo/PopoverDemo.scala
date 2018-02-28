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
import org.scalajs.dom.raw.{Event, HTMLButtonElement, HTMLElement}

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

    val popovers: Var[Seq[Popover]] = Var(Seq())

    def buildManualPopover(trigger: TypedTag[HTMLButtonElement], title: String, position: PopupPosition) = {
      lazy val pop1 = trigger.popover(title, position, Manual)
      val pop1Render = pop1.render

      pop1Render.onclick = { (e: Event) =>
        popovers.now.foreach {
          _.hide
        }
        pop1.toggle
        e.stopPropagation
      }

      org.scalajs.dom.document.body.onclick = { (e: Event) =>
        if (!e.target.asInstanceOf[HTMLElement].className.contains("popover-content")) popovers.now.foreach {
          _.hide
        }
      }
      popovers() = popovers.now :+ pop1
      pop1Render
    }


    div(
      div(
        button("Left", buttonStyle).popover(vForm(width := 100)(label("Nice content", label_danger).render, span("A important message").render), Left, title = Some("Check this !")).render,
        button("Title", buttonStyle).popover("Popover on hover with Title", Top, title = Some("Pop title")).render,
        button("Dismissable", buttonStyle).popover("Dismissible Popover on hover with Title", Top, HoverPopup, Some("Pop title"), true).render,
        inputTag("")(width := 320, marginTop := 10, placeholder := "Bottom (click)").popover("Tooltip on click on bottom", Bottom, ClickPopup).render
      ),
      div(paddingTop := 20)("Manual popovers, ie popovers built with custom interaction rules. " +
        "Here an exemple with a set of exclusive popovers, which keep alive when clicking on them"),
      div(paddingTop := 10)(
        buildManualPopover(button("Left (click)", buttonStyle), "Popover on click on bottom", Left),
        buildManualPopover(button("Right (click)", buttonStyle), "Popover on clic k on bottom", Right),
        buildManualPopover(button("Bottom (click)", buttonStyle), "Popover on clic k on bottom", Bottom)
      )
    ).render
  }

  //  def buildManualPopover(trigger: TypedTag[HTMLButtonElement], title: String, position: PopupPosition) = {
  //    lazy val pop1 = trigger.popover(title, position, Manual)
  //    val pop1Render = pop1.render
  //
  //    trigger.render.onclick = { (e: Event) =>
  //      pop1.toggle
  //      e.stopPropagation
  //    }
  //
  //    org.scalajs.dom.document.body.onclick = { (e: Event) =>
  //      println(e.target.asInstanceOf[HTMLElement])
  //      println(trigger.render)
  //      println(e.target.asInstanceOf[HTMLElement] != trigger.render)
  //      // println("contains popover " + e.target.asInstanceOf[HTMLElement].className.contains("popover"))
  //      println("id compare " +e.target.asInstanceOf[HTMLElement].id != pop1.uid )
  //      // if (e.target.asInstanceOf[HTMLElement] != trigger.render && !e.target.asInstanceOf[HTMLElement].className.contains("popover")) pop1.hide
  //      if (e.target.asInstanceOf[HTMLElement] != trigger.render && e.target.asInstanceOf[HTMLElement].id != pop1.uid) pop1.hide
  //    }
  //    pop1Render
  //  }

  val elementDemo = new ElementDemo {
    def title: String = "Popover"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 9
  }
}