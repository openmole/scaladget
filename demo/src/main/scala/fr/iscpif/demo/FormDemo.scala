package fr.iscpif.demo

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

import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import scalatags.JsDom.all._
import org.scalajs.dom.raw.Element
import sheet._


object FormDemo extends Demo {

  val sc = sourcecode.Text {

    // Vertical form
    div(
      bs.vForm(width := 200)(
        bs.labeledInput("Login", "Login"),
        bs.labeledInput("Password", "Pass")
      ),
      bs.hForm(sheet.paddingTop(20) +++ (width := 500))(
        bs.labeledInput("Login", "Login", inputStyle = (width := 150)),
        bs.labeledInput("Password", "Pass", inputStyle = (width := 150))
      )
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Form"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
