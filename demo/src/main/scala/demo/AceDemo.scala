package demo

/*
 * Copyright (C) 29/08/16 // mathieu.leclaire@openmole.org
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

import scaladget.ace._
import org.scalajs.dom.raw._
import scaladget.bootstrapnative.{Popup, bsn}
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import bsn._
import scaladget.tools._
import org.scalajs.dom
import scaladget.bootstrapnative.Popup.{Manual, PopupPosition}
import rx._

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {
    val numberOfLines = 15
    val lineHeight = 13

    val editorDiv = div(id := "editor", height := numberOfLines * lineHeight, paddingRight := 20).render

    ace.require("ace/ext/language_tools")

    val editor = ace.edit(editorDiv)
    val session = editor.getSession()

    editor.container.style.lineHeight = s"${lineHeight}px"
    editor.renderer.updateFontSize

    session.setValue("val axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)")
    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      "enableSnippets" -> true,
      "enableLiveAutocompletion" -> true
    ))

    val sT = Var(0.0)

    def updateScrollTop = sT.update(editor.renderer.getScrollTop)

    session.on("change", (x) => {
      updateScrollTop
    })

    session.on("changeScrollTop", x => {
      updateScrollTop
    })

    val errors = Seq(1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 21, 29, 35, 43, 54)

    def buildManualPopover(line: Int, y: Double, title: String, position: PopupPosition) = {
      lazy val pop1 = div(line)(`class` := "errorBackground", top := y).popover(
        title,
        position,
        Manual
      )
      lazy val pop1Render = pop1.render

      pop1Render.onclick = { (e: Event) =>
        Popover.hide
        Popover.toggle(pop1)
        e.stopPropagation
      }
      pop1Render
    }

    val errorDiv = div(`class` := "errorForeground")(
      Rx {
        val scrollAsLines = sT() / lineHeight
        val max = numberOfLines + scrollAsLines
        div(
          for {
            i <- errors.filter { e =>
              e > scrollAsLines && e < max
            }
          } yield {
            buildManualPopover(i, (i - scrollAsLines - 1) * lineHeight, s"YYY $i", Popup.Left)
          }
        )
      }
    )

    div(
      errorDiv,
      editorDiv
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Ace"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 6
  }
}
