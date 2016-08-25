package fr.iscpif.demo


import fr.iscpif.scaladget.api.DropDown
import fr.iscpif.scaladget.stylesheet.all
import org.scalajs.dom.Element

/*
 * Copyright (C) 22/08/16 // mathieu.leclaire@openmole.org
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
import sheet._
import bs._

object SelectDemo {

  val sc = sourcecode.Text {
    import fr.iscpif.scaladget.api.DropDown.OptionElement

    // Define a toy case class containing at least a name attribute
    case class MyElement(name: String)

    // Define the option sequence
    val elements = Seq(
      MyElement("First element"),
      MyElement("Second Element"),
      MyElement("Third Element")
    )

    div(
      elements.map { e =>
        DropDown.option(e)
      }.dropdown(_.name, 1, btn_success).selector,
      bs.vForm(width := 200)(
        bs.labeledInput("Login", "Login"),
        bs.labeledInput("Password", "Pass")
      ).dropdown("Form", btn_primary +++ sheet.marginLeft(10), () => println("Dropdown closed"))
    ).render

  }


  val elementDemo = new ElementDemo {
    def title: String = "Dropdown"

    def code: String = sc.source

    def element: Element = sc.value
  }

}
