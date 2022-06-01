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

import com.raquo.laminar.DomApi
import scaladget.ace._
import com.raquo.laminar.api.L._
import com.raquo.laminar.nodes.ReactiveElement
import org.scalajs.dom.{HTMLDivElement, MouseEvent}
import scaladget.bootstrapnative.bsn._
import scaladget.bootstrapnative.Popup._
import scaladget.bootstrapnative.Tools.MyPopoverBuilder

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {

    val editorHeight = "200"
    val lineHeight = "13"

    val editorDiv = div(idAttr := "editor", height := editorHeight, paddingRight := "20", zIndex := 0)

    val editor = ace.edit(editorDiv.ref)
    val session = editor.getSession()

    session.setBreakpoint(4)
    session.setBreakpoint(1)


    scalamode
    githubtheme
    extLanguageTools

    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")

    session.on("changeScrollTop", (y: Any) => println("scrolled " + y))

    editor.container.style.lineHeight = s"${lineHeight}px"
    editor.renderer.updateFontSize()

    session.setValue("def fib(n):\rval axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\nAA\n\n\n\n")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      "enableLiveAutocompletion" -> true
    ))

    val errorMessage: Var[Option[String]] = Var(None)

    def buildBP = {

      val breakpoints = scaladget.ace.Utils.getBreakPointElements(editorDiv).toSeq
      breakpoints.foreach { bp =>
        bp._1.addEventListener("click", { (e: MouseEvent) =>
          errorMessage.update(m =>
            m match {
              case None => Some("45ableBasi45ableBasicAutocompletinn45ableBasicAutocompletinn" + bp._2)
              case _ => None
            })
        })
      }
    }


    div(
      div("Youhou !", fontWeight.bold),
      errorMessage.signal.map { x => x.isDefined }.expand(div(child <-- errorMessage.signal.map {
        m=> div(m.getOrElse(""))
      }, backgroundColor := "orange", color := "white", height := "50")),
      editorDiv,
      button(btn_primary_outline, "Build", onClick --> { _ => buildBP })
    )

  }

  val elementDemo = new ElementDemo {
    def title: String = "Ace"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
