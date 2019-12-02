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
import org.scalajs.dom.raw.{Event, HTMLElement}
import scaladget.bootstrapnative.Table.{BSTableStyle, ReactiveRow, StaticSubRow, SubRow}
import scaladget.bootstrapnative.{EdiTable, EditableCell, EditableRow, ExpandableRow, GroupCell, LabelCell, PasswordCell, Table, TextCell, TriggerCell, bsn}
import scaladget.bootstrapnative.EdiTable._
import scaladget.bootstrapnative.bsn._
import scaladget.tools._
import scalatags.JsDom.all._
import rx._
import scalatags.JsDom
import scalatags.JsDom._

object TableDemo extends Demo {
  val sc = sourcecode.Text {

    val table = bsn.dataTable.
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

    type Role = String
    val admin: Role = "Admin"
    val user: Role = "User"


    val roles = Seq(user, admin)
    val roleFilter = (r: Role) => r == admin

    type Status = String
    val running: Status = "Running"
    val off: Status = "Off"
    val error: Status = "Error"


    val rowFlex = Seq(styles.display.flex, flexDirection.row, justifyContent.spaceAround, alignItems.center)
    val columnFlex = Seq(styles.display.flex, flexDirection.column, styles.justifyContent.center)

    def save(expandableRow: ExpandableRow, name: TextCell, email: TextCell, password: PasswordCell, role: LabelCell, status: Status) = {
      rows() = rows.now.updated(rows.now.indexOf(expandableRow), buildExpandable(name.get, email.get, password.get, role.get, status, true))
    }

    def closeAll(except: ExpandableRow) = rows.now.filterNot{_ == except}.foreach{_.subRow.trigger() = false}

    def buildExpandable(userName: String, userEmail: String, userPassword: String, userRole: Role, userStatus: Status, expanded: Boolean = false): ExpandableRow = {
      val aVar = Var(expanded)

      def roleStyle(s: Role) =
        if (s == admin) label_success
        else label_default

      val name = TextCell(userName, Some("Name"))
      val email = TextCell(userEmail, Some("Email"))
      val password = PasswordCell(userPassword, Some("Password"))
      val role = LabelCell(userRole, roles, optionStyle = roleStyle, title = Some("Role"))

      val rowEdit = Var(false)

      val buttonStyle: ModifierSeq = Seq(
        fontSize := 22,
        color := "#23527c",
        opacity := 0.8
      )

      lazy val aSubRow: StaticSubRow = StaticSubRow({
        div(height := 120, rowFlex)(
          groupCell.build(margin := 25),
        )
      }, aVar)

      def statusStyle(s: Status) =
        if (s == running) label_success
        else if (s == off) label_default
        else label_danger

      lazy val expandableRow: ExpandableRow = ExpandableRow(EditableRow(Seq(
        TriggerCell(a(userName, onclick := { () =>
          closeAll(expandableRow)
          aVar() = !aVar.now
        })),
        LabelCell(userStatus, Seq(), optionStyle = statusStyle),
      )), aSubRow)

      lazy val groupCell: GroupCell = GroupCell(
        div(rowFlex, width := "100%")(
          name.build(padding := 10),
          email.build(padding := 10),
          password.build(padding := 10),
          role.build(padding := 10),
          span(
            Rx {
              if (rowEdit()) glyphSpan(glyph_save +++ buttonStyle +++ toClass("actionIcon"), () => {
                rowEdit.update(!rowEdit.now)
                save(expandableRow, name, email, password, role, userStatus)
                })
              else glyphSpan(glyph_edit2 +++ buttonStyle +++ toClass("actionIcon"), () => {
                //button("Edit", btn_default, onclick := { () =>
                rowEdit.update(!rowEdit.now)
                groupCell.switch
              })
            }
          )
        ), name, email, password, role)

      expandableRow
    }


    lazy val rows = Var(Seq(
      buildExpandable("Bobi", "bobi@me.com", "mypass", admin, running),
      buildExpandable("Barbara", "barb@gmail.com", "toto", user, off)
    ))

    val headerStyle: ModifierSeq = Seq(
      height := 40.85
    )

    val editablePanel = div(
      Rx {
        div(styles.display.flex, flexDirection.row, styles.justifyContent.center)(
          EdiTable(Seq("Name", "Status"), rows()).render(width := "90%")
        )
      }
    )


    div(
      filterInput,
      filteredTable.render,
      editablePanel.render,
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

    override def codeWidth: Int = 6
  }
}
