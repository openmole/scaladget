package scaladget.bootstrapnative

import org.scalajs.dom.raw.HTMLElement
import scalatags.JsDom.{TypedTag, tags}
import scalatags.JsDom.all._
import scaladget.tools._
import bsn._
import rx._
import scaladget.bootstrapnative.Table.BSTableStyle

object Table {

  case class BSTableStyle(tableStyle: TableStyle, headerStyle: ModifierSeq, selectionColor: String = "#e1e1e1")

  case class Header(values: Seq[String])

  case class Row(values: Seq[TypedTag[HTMLElement]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None)

  case class ReactiveRow(values: Rx[Seq[TypedTag[HTMLElement]]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None)

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)) {
    val stableDiv = div(subTypedTag)

    def render = trigger.expand(stableDiv)
  }

  implicit def rowToReactiveRow(r: Row): ReactiveRow = ReactiveRow(Rx(r.values), r.rowStyle, r.subRow)

}

import Table._

case class Table(headers: Option[Table.Header] = None,
                 // rows: Seq[Table.ReactiveRow] = Seq(),
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod)) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val selected: Var[Option[ReactiveRow]] = Var(None)

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(row: ReactiveRow): Table = {
    getRows(row).foreach { r =>
      tableBody.appendChild(r)
    }
    this
  }


  def insertRow(row: ReactiveRow): Table = {
    getRows(row).foreach { n =>
      tableBody.insertBefore(n, tableBody.firstChild)
    }
    this
  }

  def deleteRow(index: Int) = {
    if (tableBody.rows.length >= index)
      tableBody.deleteRow(index)
  }

  val tableBody = tags.tbody.render

  private def getRows(r: ReactiveRow) = {
    val aRow = tags.tr(r.values.now.map {
      tags.td(_)
    },
      backgroundColor := Rx {
        if (Some(row) == selected()) bsTableStyle.selectionColor else ""
      }
    )(onclick := { () =>
      selected() = Some(r)
    }).render

    r.values.trigger {
      while (aRow.firstChild != null) {
        aRow.removeChild(aRow.firstChild)
      }
      r.values.now.foreach { x =>
        aRow.appendChild(tags.td(x.render))
      }
    }

    Seq(Some(aRow), r.subRow.map { sr =>
      tags.tr(r.rowStyle)(
        tags.td(colspan := 999, padding := 0, borderTop := "0px solid black")(
          sr.render
        )
      ).render
    }).flatten
  }

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
