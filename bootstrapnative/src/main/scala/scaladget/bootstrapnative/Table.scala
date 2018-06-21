package scaladget.bootstrapnative

import java.util.UUID

import org.scalajs.dom.raw.{HTMLElement, HTMLTableRowElement}
import scalatags.JsDom.{TypedTag, tags}
import scalatags.JsDom.all._
import scaladget.tools._
import bsn._
import org.scalajs.dom.html.{TableRow, TableSection}
import rx._
import scaladget.bootstrapnative.Table.BSTableStyle

object Table {

  case class BSTableStyle(tableStyle: TableStyle, headerStyle: ModifierSeq, selectionColor: String = "#e1e1e1")

  case class Header(values: Seq[String])

  case class Row(values: Seq[TypedTag[HTMLElement]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None)

  def reactiveRow(values: Rx[Seq[TypedTag[HTMLElement]]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) =
    ReactiveRow(uuID.short("rr"), values, rowStyle, subRow)

  case class ReactiveRow(uuid: ID, values: Rx[Seq[TypedTag[HTMLElement]]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) {

    val tr = tags.tr(id := uuid)(values.now.map {
      tags.td(_)
    }
      //      ,
      //      backgroundColor := Rx {
      //        if (Some(row) == selected()) tableStyle.selectionColor else ""
      //      }
    )
      //    (onclick := { () =>
      //      table.selected() = Some(this)
      //    }
      //    )
      .render

    val subtr = subRow.map { sr =>
      tags.tr(rowStyle)(
        tags.td(colspan := 999, padding := 0, borderTop := "0px solid black")(
          sr.render
        )
      ).render
    }

    def rows = Seq(Some(tr), subtr).flatten

    values.trigger {
      while (tr.firstChild != null) {
        tr.removeChild(tr.firstChild)
      }
      values.now.foreach { x =>
        tr.appendChild(tags.td(x.render))
      }
    }
  }

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)) {
    val stableDiv = div(subTypedTag)

    def render = trigger.expand(stableDiv)
  }

  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(Rx(r.values), r.rowStyle, r.subRow)

}

import Table._

case class Table(headers: Option[Table.Header] = None,
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod),
                 autoDelete: Option[ID=> Rx[Boolean]] = None) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val selected: Var[Option[ReactiveRow]] = Var(None)

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(row: ReactiveRow): Table = {
    row.rows.foreach { r =>
      tableBody.appendChild(r)
    }
    setAutoDelete(row)
    this
  }

  def withAutoDelete(ad: (ID)=> Rx[Boolean]) = copy(autoDelete = Some(ad))

  def insertRow(row: ReactiveRow): Table = {
    row.rows.foreach { n =>
      tableBody.insertBefore(n, tableBody.firstChild)
    }
    setAutoDelete(row)
    this
  }

  private def setAutoDelete(row: ReactiveRow) =
    autoDelete.foreach { ad =>
    val reactive = ad(row.uuid)
    reactive.trigger {
      if (reactive.now) {
        delete(row)
      }
    }
  }

  def delete(row: ReactiveRow) = {
    findIndex(row).foreach { ind =>
      tableBody.deleteRow(ind)
      row.subRow.foreach { x =>
        tableBody.deleteRow(ind)
      }
    }
  }

  def findIndex(reactiveRow: ReactiveRow): Option[Int] = findIndex(reactiveRow.uuid)

  def findIndex(id: ID): Option[Int] = {
    val lenght = tableBody.rows.length

    def findIndex0(currentIndex: Int, found: Boolean): Option[Int] = {
      if (found) Some(currentIndex - 1)
      else if (currentIndex == lenght - 1) None
      else {
        findIndex0(currentIndex + 1, tableBody.rows(currentIndex).id == id)
      }
    }

    findIndex0(0, false)
  }

  val tableBody = tags.tbody.render

  lazy val render = {

    tags.table(bsTableStyle.tableStyle)(
      tags.thead(bsTableStyle.headerStyle)(
        tags.tr(
          headers.map { h =>
            h.values.map {
              th(_)
            }
          })),
      tableBody
    )
  }

}
