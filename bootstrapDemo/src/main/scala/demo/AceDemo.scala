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
import org.scalajs.dom.HTMLDivElement
import scaladget.bootstrapnative.bsn._
import scaladget.bootstrapnative.Popup._
import scaladget.bootstrapnative.Tools.MyPopoverBuilder

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {

    val editorHeight = "200"
    val lineHeight = "13"

    val editorDiv = div(idAttr := "editor", height := editorHeight, paddingRight := "20")

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

    session.setValue("def fib(n):\rval axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      "enableLiveAutocompletion" -> true
    ))

    def buildBP = {

      val breakpoints = scaladget.ace.Utils.getBreakPointElements(editorDiv).toSeq
      breakpoints.foreach { bp =>

        val clickableElement = div(height := "13", width := "41", marginTop := "-13", marginLeft := "-19")

        render(bp._1, clickableElement)

        MyPopoverBuilder(
          clickableElement,
          div("popover ! " + bp._2, color := "black"),
        ).render
      }
    }


    div(
      div("Youhou !", fontWeight.bold),
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
