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
import scaladget.bootstrapnative.bsn
import scalatags.JsDom.all._

import scalajs.js

object AceDemo extends Demo {


  val sc = sourcecode.Text {
    val editorDiv = div(id := "editor", height := 100, paddingRight := 20).render

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

    editor.session.addMarker(scaladget.ace.Utils.rangeFor(2, 0, 2, 5), "myMarker", "", false)
    editor.session.addMarker(scaladget.ace.Utils.rangeFor(4, 0, 4, 5), "myMarker", "", false)

    session.addGutterDecoration(2, "gutterDecoration")

    div(
      editorDiv,
      button(bsn.btn_primary, marginTop := 20, "Reset", onclick := { () =>
        session.removeGutterDecoration(2, "gutterDecoration")
      })
    ).render
  }


  val elementDemo = new ElementDemo {
    def title: String = "Ace"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 6
  }
}
