package demo

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
import scaladget.stylesheet.{all => sheet}
import scaladget.api.{BootstrapTags => bs}
import scalatags.JsDom.all._
import sheet._
import bs._

object TableDemo extends Demo {
  val sc = sourcecode.Text {

    val table = bs.table.
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow("My first 1", "My first 1", "My first 3").
      addRow("My second 1", "My second 2", "My second 3")

    div(
      table.render(),
      table.render(inverse_table),
      table.render(hover_table),
      table.render(headerStyle = default_header),
      table.
      addRowElement(a(href := "http://openmole.org", "My third 1"), span("My third 2"), span("My third 3")).
      addRow("My fourth 1", "My fourth 2", "My fourth 3").render(striped_table)
    ).render
  }


  val elementDemo = new ElementDemo {
    def title: String = "Table"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
