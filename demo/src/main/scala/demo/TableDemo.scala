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
import org.scalajs.dom.raw.{Event}

import scaladget.bootstrapnative.bsn
import scaladget.bootstrapnative.bsn._
import scalatags.JsDom.all._

object TableDemo extends Demo {
  val sc = sourcecode.Text {

    val table = bsn.table.
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow("0.1", "158", "3").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")


    val filteredTable = table.style(bordered_table).sortable

    lazy val filterInput = inputTag("")(marginBottom := 20).render
    filterInput.oninput = (e: Event) ⇒ {
      filteredTable.filter(filterInput.value)
    }

    div(
      filterInput,
      filteredTable.render,
      table.style(inverse_table).render,
      table.style(hover_table).render,
      table.style(headerStyle = default_header).render,
      table.
        addRow("My fourth 1", "My fourth 2", "My fourth 3").style(striped_table).render
    ).render

  }


  val elementDemo = new ElementDemo {
    def title: String = "Table"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
