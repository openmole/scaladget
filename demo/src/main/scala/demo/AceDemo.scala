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
import com.raquo.laminar.api.L._
import com.github.uosis.laminar.webcomponents.material._

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {

    val editorHeight = "200"
    val lineHeight = "13"

    val editorDiv = div(idAttr := "editor", height := editorHeight, paddingRight := "20")
    //val visibleLines = editorHeight / lineHeight

    val editor = ace.edit(editorDiv.ref)
    val session = editor.getSession()

    scalamode
    githubtheme
    extLanguageTools

    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")

    editor.container.style.lineHeight = s"${lineHeight}px"
    editor.renderer.updateFontSize

    session.setValue("def fib(n):\rval axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      // "enableSnippets" -> true,
      "enableLiveAutocompletion" -> true
    ))

    //    val sT = Var(0.0)
    //
    //    def updateScrollTop = sT.update(editor.renderer.getScrollTop)

    //    session.on("change", (x) => {
    //      updateScrollTop
    //    })
    //
    //    session.on("changeScrollTop", x => {
    //      updateScrollTop
    //    })

    val errors = Seq(1, 2, 3, 4, 5, 9, 10, 11, 12, 13, 14, 15, 16, 21, 29, 35, 43, 54)

    //    def buildManualPopover(line: Int, y: Double, title: String, position: PopupPosition) = {
    //      lazy val pop1 = div(line)(`class` := "errorBackground", top := y).popover(
    //        title,
    //        position,
    //        Manual
    //      )
    //      lazy val pop1Render = pop1.render
    //
    //      pop1Render.onclick = { (e: Event) =>
    //        Popover.hide
    //        Popover.toggle(pop1)
    //        e.stopPropagation
    //      }
    //      pop1Render
    //    }

    //    val errorDiv = div(`class` := "errorForeground")(
    //      Rx {
    //        val scrollAsLines = sT() / lineHeight
    //        val max = visibleLines + scrollAsLines
    //        div(
    //          for {
    //            i <- errors.filter { e =>
    //              e > scrollAsLines && e < max
    //            }
    //          } yield {
    //            buildManualPopover(i, (i - scrollAsLines - 1) * lineHeight, s"YYY $i", Popup.Left)
    //          }
    //        )
    //      }
    //    )
    println("IDETIOR DIV " + editorDiv)
    //editor.focus()
    div(
      //errorDiv,
      div("Youhou !", fontWeight.bold),
      editorDiv
    )
    //
  }

  val elementDemo = new ElementDemo {
    def title: String = "Ace"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
