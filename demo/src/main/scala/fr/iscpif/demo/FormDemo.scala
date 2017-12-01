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

import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLInputElement

import scaladget.bootstrapnative.{BootstrapTags => bs}
import bs._
import scaladget.bootstrapnative.all._
import scalatags.JsDom.all._
import scaladget.tools.stylesheet._
import scaladget.tools.JsRxTags._


object FormDemo extends Demo {

  val sc = sourcecode.Text {

    import rx._

    case class MyElement(name: String)

    val inputStyle: ModifierSeq = Seq(width := 150)
    val loginValue = Var("Mathieu")
    val elements = Seq(
      MyElement("Male"),
      MyElement("Female")
    )

    lazy val loginInput: HTMLInputElement =
      bs.input(loginValue.now)(placeholder := "Login", inputStyle, oninput := { () =>
        loginValue() = loginInput.value
      }).render

    val passInput = bs.input("")(placeholder := "Password", `type` := "password", inputStyle).render
    val cityInput = bs.input("")(placeholder := "City", inputStyle).render

    val genderDD = elements.options(1, btn_success, (m: MyElement) => m.name).selector

    div(
      bs.vForm(width := 200)(
        loginInput.withLabel("Login"),
        passInput.withLabel("Password")
      ),
      bs.hForm(Seq(paddingTop := 20, width := 500).toMS)(
        cityInput.withLabel("City"),
        genderDD
      ),
      Rx {
        span(marginTop := 20, s"Login : ${loginValue()}")
      }
    ).render

  }

  val elementDemo = new ElementDemo {
    def title: String = "Form"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
