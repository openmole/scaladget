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

    val table = dataTable().
      addHeaders("Title 1", "Title 2", "Title 3").
      addRow("0.1", "158", "3").
      addRow("0.006", "bb", "236").
      addRow("21", "zz", "302").
      addRow("151", "a", "33")


    //  val filteredTable = table.style(bordered_table).sortable
    //
    //    lazy val filterInput = inputTag("")(marginBottom := 20).render
    //    filterInput.oninput = (e: Event) ⇒ {
    //      filteredTable.filter(filterInput.value)
    //    }

    type Role = String
    val admin: Role = "Admin"
    val user: Role = "User"


    val roles = Seq(user, admin)
    val roleFilter = (r: Role) => r == admin

    type Status = String
    val running: Status = "Running"
    val off: Status = "Off"
    val error: Status = "Error"


    val rowFlex = Seq(display.flex, flexDirection.row, justifyContent.spaceAround, alignItems.center)
    val columnFlex = Seq(display.flex, flexDirection.column, justifyContent.flexStart)


    val triggerSignal = Var(false)
    val subTrigger = button(btn_primary, "Expand", onClick --> { _ => triggerSignal.update(!_) })
    val row = BasicRow(Seq(span("0.5"), span("0.01"), subTrigger))

    val triggerSignal2 = Var(false)
    val subTrigger2 = button(btn_primary, "Expand", onClick --> { _ => triggerSignal2.update(!_) })
    val row2 = BasicRow(Seq(span("0.5"), span("0.01"), subTrigger2))

    val subContent = div(
      height := "80",
      "I am a sub",
      inputTag().amend(placeholder := "Your text here")
    )

    val subContent2 = div(height := "80",
      exclusiveRadio(
        Seq(ToggleState("One", btn_primary_string), ToggleState("Two", btn_primary_string), ToggleState("Three", btn_primary_string)),
        btn_secondary_string,
        ToggleState("Two", btn_primary_string),
        onToggled = _ => {}
      )
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
      .addRow("7.0", "3", "3488.6")
      .addRow("309", "3.14", "2218")
      .style(striped_table)
      .render

    lazy val tableWithDataDark = dataTable()
      .addHeaders("Data 1", "Data 2", "Data 3")
      .addRow("0.0", "0.103", "88.6")
      .addRow("309", "3.14", "2218")
      .style(inverse_table)
      .render


    lazy val filterInput = inputTag("").amend(marginBottom := "20",
      inContext { context =>
        onInput --> { _ =>
          tableWithData.setFilter(context.ref.value)
        }
      }
    )
    //    def save(expandableRow: ExpandableRow, name: TextCell, email: TextCell, password: PasswordCell, role: LabelCell, status: Status) = {
    //      rows() = rows.now.updated(rows.now.indexOf(expandableRow), buildExpandable(name.get, email.get, password.get, role.get, status))
    //    }
    //
    //    def closeAll(except: ExpandableRow) = rows.now.filterNot{_ == except}.foreach{_.subRow.trigger() = false}
    //
    //    def buildExpandable(userName: String, userEmail: String, userPassword: String, userRole: Role, userStatus: Status, edited: Boolean = false, expanded: Boolean = false): ExpandableRow = {
    //      val aVar = Var(expanded)
    //
    //
    //      def roleStyle(s: Role) =
    //        if (s == admin) label_success
    //        else label_default
    //
    //      val name = TextCell(userName, Some("Name"), edited)
    //      val email = TextCell(userEmail, Some("Email"), edited, editable = false)
    //      val password = PasswordCell(userPassword, Some("Password"), edited)
    //      val role = LabelCell(userRole, roles, optionStyle = roleStyle, title = Some("Role"), editing = edited)
    //
    //      val rowEdit = Var(edited)
    //
    //      val buttonStyle: HESetters = Seq(
    //        fontSize := "22",
    //        color := "#23527c",
    //        opacity := 0.8
    //      )
    //
    //      lazy val aSubRow: StaticSubRow = StaticSubRow({
    //        div(height := 250, rowFlex)(
    //          groupCell.build(margin := 25),
    //        )
    //      }, aVar)
    //
    //      def statusStyle(s: Status) =
    //        if (s == running) badge_success
    //        else if (s == off) badge_light
    //        else badge_danger
    //
    //      lazy val expandableRow: ExpandableRow = ExpandableRow(EditableRow(Seq(
    //        TriggerCell(a(userName, onclick := { () =>
    //          closeAll(expandableRow)
    //          aVar() = !aVar.now
    //        })),
    //        LabelCell(userStatus, Seq(), optionStyle = statusStyle),
    //      )), aSubRow)
    //
    //      lazy val groupCell: GroupCell = GroupCell(
    //        div(columnFlex, width := "100%")(
    //          name.build(padding := 10),
    //          email.build(padding := 10),
    //          password.build(padding := 10),
    //          role.build(padding := 10),
    //          span(
    //            Rx {
    //              if (rowEdit()) glyphSpan(glyph_save +++ buttonStyle +++ toClass("actionIcon"), () => {
    //                rowEdit.update(!rowEdit.now)
    //                save(expandableRow, name, email, password, role, userStatus)
    //                })
    //              else glyphSpan(glyph_edit2 +++ buttonStyle +++ toClass("actionIcon"), () => {
    //                //button("Edit", btn_default, onclick := { () =>
    //                rowEdit.update(!rowEdit.now)
    //                groupCell.switch
    //              })
    //            }
    //          )
    //        ), name, email, password, role)
    //
    //      expandableRow
    //    }
    //
    //
    //    lazy val rows = Var(Seq(
    //      buildExpandable("Bobi", "bobi@me.com", "mypass", admin, running, true),
    //      buildExpandable("Barbara", "barb@gmail.com", "toto", user, off)
    //    ))
    //
    //    val headerStyle: ModifierSeq = Seq(
    //      height := 40.85
    //    )
    //
    //    val editablePanel = div(
    //      Rx {
    //        div(display.flex, flexDirection.row, justifyContent.center)(
    //          EdiTable(Seq("Name", "Status"), rows()).render(width := "90%")
    //        )
    //      }
    //    )


    div(
      //  filterInput,
      //  filteredTable.render,
      // editablePanel.render,
      //      table.style(inverse_table).render,
      //      table.style(hover_table).render,
      //      table.style(headerStyle = default_header).render,
      //      table.
      //        addRow("My fourth 1", "My fourth 2", "My fourth 3").style(striped_table).render,

      tableWithSubs.render,
      filterInput,
      tableWithData.render,
      tableWithDataDark.render
    )

  }


  val elementDemo = new ElementDemo {
    def title: String = "Table"

    def code: String = sc.source

    def element: HtmlElement = sc.value

    override def codeWidth: Int = 6
  }
}
