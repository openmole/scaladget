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

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {

    val editorHeight = "200"
    val lineHeight = "13"

    val editorDiv = div(idAttr := "editor", height := editorHeight, paddingRight := "20")

    val editor = ace.edit(editorDiv.ref)
    val session = editor.getSession()

    scalamode
    githubtheme
    extLanguageTools

    session.setMode("ace/mode/scala")
    editor.setTheme("ace/theme/github")

    editor.container.style.lineHeight = s"${lineHeight}px"
    editor.renderer.updateFontSize()

    session.setValue("def fib(n):\rval axx = 7\nval b = 8\nval c = a*b\n\nprintln(c)")
    editor.setOptions(js.Dynamic.literal(
      "enableBasicAutocompletion" -> true,
      "enableLiveAutocompletion" -> true
    ))
    div(
      div("Youhou !", fontWeight.bold),
      editorDiv
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Ace"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
