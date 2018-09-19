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
    val editorDiv = div(id := "editor", height := 200, paddingRight := 20).render

    ace.require("ace/ext/language_tools")

    val editor = ace.edit(editorDiv)
    val session = editor.getSession()


    session.setValue("val axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)")
    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      "enableSnippets" -> true,
      "enableLiveAutocompletion" -> true
    ))

    val nbLines: Var[(Int, Int)] = Var((editor.getFirstVisibleRow.toInt, editor.getLastVisibleRow.toInt))

    session.on("change", (x) => {
      nbLines() = (editor.getFirstVisibleRow.toInt, editor.getLastVisibleRow.toInt)
    })

    session.on("changeScrollTop", x => {
      Popover.current.now.foreach { p =>
        Popover.toggle(p)
      }
      nbLines() = (editor.renderer.getScrollTopRow.toInt, editor.renderer.getScrollBottomRow.toInt)
    })

    val errors = Seq(0, 2, 13, 21, 29, 35, 43, 54)

    def buildManualPopover(i: Int, title: String, position: PopupPosition) = {
      if (errors.contains(i)) {
        lazy val pop1 = div(i)(`class` := "error").popover(
          title,
          position,
          Manual
        )
        session.addMarker(scaladget.ace.Utils.rangeFor(i, 0, i, 5), "myMarker", "", false)
        lazy val pop1Render = pop1.render

        pop1Render.onclick = { (e: Event) =>
          if (Popover.current.now == pop1) Popover.hide
          else {
            Popover.current.now.foreach {p=>
              Popover.toggle(p)
            }
            Popover.toggle(pop1)
          }
          e.stopPropagation
        }

        pop1Render
      } else div(height := 13, opacity := 0).render
    }


    val errorDiv = div(`class` := "uuu")(
      Rx {
        val topMargin = if (session.getScrollTop() > 0) marginTop := -8 else marginTop := 0
        div(topMargin)(
          (nbLines()._1 until nbLines()._2).map { i =>
            buildManualPopover(i, s"YYY $i", Popup.Left)
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
