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


object FormDemo extends Demo {

  val sc = sourcecode.Text {

    case class MyElement(name: String)

    val inputStyle = width := "150"
    val loginValue = Var("Mathieu")

    val elements = Seq(
      MyElement("Male"),
      MyElement("Female")
    )

    val loginInput = inputTag(loginValue.now).amend(
      placeholder := "Login",
      width := "150",
      inContext { thisNode => onInput.map(_ => thisNode.ref.value) --> loginValue }
    )


    val passInput = inputTag("").amend(placeholder := "Password", `type` := "password", inputStyle)
    val cityInput = inputTag("").amend(placeholder := "City", inputStyle)

    val genderDD = elements.options(1, btn_success, (m: MyElement) => m.name, decorations = Map(elements(0)-> glyph_settings)).selector

    div(
      vForm(width := "200",
        loginInput.withLabel("Login"),
        passInput.withLabel("Password")
      ),
      hForm(Seq(paddingTop := "20", width := "500"),
        cityInput.withLabel("City"),
        genderDD
      ),
      span(marginTop := "20", child.text <-- loginValue.signal.map { lv => s"Login : $lv" })
    )


  }

  val elementDemo = new ElementDemo {
    def title: String = "Form"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
