package fr.iscpif.demo

/*
 * Copyright (C) 05/01/17 // mathieu.leclaire@openmole.org
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
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import scalatags.JsDom.all._
import sheet._
import bs._

object TabDemo extends Demo {
  val sc = sourcecode.Text {

    val div1 = div("Lorem ipsum dolor sit amet, " +
      "consectetur adipiscing elit, sed do eiusmod tempor " +
      "incididunt ut labore et dolore magna aliqua. " +
      "Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
      "laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
      "irure dolor in reprehenderit in voluptate velit esse cillum dolore " +
      "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
      "sunt in culpa qui officia deserunt mollit anim id est laborum.", padding := 10)


    val div2 = vForm(bs.input("")(placeholder := "Name").render.withLabel("Your name"))

    Tabs(sheet.pills).
      add("My first", div1).
      add("My second", div2, true).
      render

  }


  val elementDemo = new ElementDemo {
    def title: String = "Tab"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
