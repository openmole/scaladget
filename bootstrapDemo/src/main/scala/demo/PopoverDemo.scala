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


import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

object PopoverDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapnative.Popup._

    val buttonStyle: HESetters = Seq(
      btn_secondary,
      marginRight := "5"
    )

    val open = Var(false)

    //SIMPLE POPOVERS
    lazy val simplePopovers = div(
      h2("Simple popovers"),
      div(paddingTop := "20", "Simple popovers containing text, or simple content with no events to be fired and with basic trigger modes (click, hover)."),
      //  button("Left", buttonStyle).popover(vForm(width := 100)(label("Nice content", label_danger).render, span("A important message").render), Left, title = Some("Check this !")).render,
      button("Title", buttonStyle).popover(div(button(btn_primary, "Hey"), div("Popover on hover with Title")), Top, ClickPopup, title = Some("Pop title")).render,
      button("Title 2", buttonStyle).popover(div("Popover on hover with Title", color := "red"), Top, ClickPopup, title = Some("Pop title")).render,
      button("Dismissable", buttonStyle).popover(div("An other popover"), Top, HoverPopup, Some("Pop title"), true).render,
      inputTag("").amend(width := "320", marginTop := "10", placeholder := "Bottom (click)").popover(div("Tooltip on click on bottom"), Bottom, ClickPopup).render,
      div(cls := "flex-row", justifyContent.right,
        div(
          cls <-- open.signal.map { o =>
            if (o) "button-open" else "button-close"
          },
          inputTag("blablaba").amend(onSubmit --> { _ => open.update(!_) })
        ),
        button("Open", buttonStyle, onClick --> { _ => open.update(!_) })
      )
    )

    simplePopovers
  }

  val elementDemo = new ElementDemo {
    def title: String = "Popover"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 9
  }
}