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

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import scaladget.bootstrapnative.Table.{BSTableStyle, BasicRow}
import scaladget.tools.Utils._

object TableDemo extends Demo {
  val sc = sourcecode.Text {

    val rowFlex = Seq(display.flex, flexDirection.row, justifyContent.spaceAround, alignItems.center)
    val columnFlex = Seq(display.flex, flexDirection.column, justifyContent.flexStart)


    val triggerSignal = Var(false)
    val subTrigger = button(btn_primary, "Expand", onClick --> { _ => triggerSignal.update(!_) })
    val row = BasicRow(Seq(span("0.5"), span("0.01"), subTrigger))

    val triggerSignal2 = Var(false)
    val subTrigger2 = button(btn_primary, "Expand", onClick --> { _ => triggerSignal2.update(!_) })
    val row2 = BasicRow(Seq(span("0.5"), span("0.01"), subTrigger2))

    val subContent = div(
      height := "80", rowFlex, paddingLeft := "20",
      "I am a sub",
      inputTag().amend(placeholder := "Your text here")
    )

    val subContent2 = div(height := "80", padding := "15",
      exclusiveRadio(
        Seq(ToggleState("One", "One",btn_primary_string, ()=> println("ONE")), ToggleState("Two", "Two", btn_primary_string, ()=> println("TWO")), ToggleState("Three", "Three", btn_primary_string, ()=> println("THREE"))),
        btn_secondary_string,
        1
      ).element
    )

    lazy val tableWithSubs = elementTable()
      .addHeaders("First", "Second", "Actions")
      .addRow(span("0.0"), span("0.99"), span("0.88"))
      .addRow(row).expandTo(subContent, triggerSignal.signal)
      .addRow(span("0.0"), span("0.99"), span("0.88"))
      .addRow(span("0.0"), span("0.99"), span("0.88"))
      .addRow(row2).expandTo(subContent2, triggerSignal2.signal)
      .addRow(span("0.0"), span("0.99"), span("0.88"))
      .addRow(span("0.0"), span("0.99"), span("0.88"))
      .render


    lazy val tableWithData = dataTable()
      .addHeaders("Data 1", "Data 2", "Data 3")
      .addRow("0.0", "0.103", "88.6")
      .addRow("500", "153", "486")
      .addRow("7.0", "3", "77.56")
      .addRow("309", "3.14", "2218")
      .style(striped_table)
      .sortable
      .render

    lazy val tableWithDataDark = dataTable()
      .addHeaders("Data 1", "Data 2", "Data 3")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .style(inverse_table)
      .sortable
      .render


    lazy val filterInput = inputTag("").amend(marginBottom := "20",
      inContext { context =>
        onInput --> { _ =>
          tableWithData.setFilter(context.ref.value)
        }
      }
    )

    div(
      tableWithSubs.render,
      filterInput,
      tableWithData.render,
      div(height := "200px", overflow.auto, tableWithDataDark.render)
    )

  }


  val elementDemo = new ElementDemo {
    def title: String = "Table"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
