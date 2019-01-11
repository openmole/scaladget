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

import org.scalajs.dom.raw._
import scaladget.acediff._
import scaladget.bootstrapnative.bsn
import scalatags.JsDom.all._

import scala.scalajs.js

object AceDiffDemo extends Demo {


  val sc = sourcecode.Text {
    val editorDiv = div(width := 600, height := 200, marginTop := 50, position := "absolute").render

    org.scalajs.dom.document.body.appendChild(editorDiv)
    val right: LR = LR.content("ooof")
    val left: LR = LR.content("ooof\nuuf")
    val diff = aceDiff().element(editorDiv).left(left).right(right).build

    val rightButton = button(bsn.btn_default, "RIGHT", onclick := {()=> println(diff.getEditors().right.session.getValue)})

    div(
      rightButton,
      editorDiv
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "AceDiff"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 6
  }
}
