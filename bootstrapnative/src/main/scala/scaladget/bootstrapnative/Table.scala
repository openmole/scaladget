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

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)) {
    def render = trigger.expand(subTypedTag)
  }

}

import Table._

case class Table(headers: Option[Table.Header] = None,
                 rows: Seq[Table.Row] = Seq(),
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod)) {


  val selected: Var[Option[Row]] = Var(None)
  val nbColumns = rows.headOption.map {
    _.values.length
  }.getOrElse(0)

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(row: Row): Table = copy(rows = rows :+ row)

  val render = tags.table(bsTableStyle.tableStyle)(
    tags.thead(bsTableStyle.headerStyle)(
      tags.tr(
        headers.map { h =>
          h.values.map {
            th(_)
          }
        })),
    tags.tbody(
      (for (r <- rows) yield {
        Seq(Some(tags.tr(r.values.map {
          tags.td(_)
        },
          backgroundColor := Rx {
            if (Some(row) == selected()) bsTableStyle.selectionColor else ""
          }
        )(onclick := { () =>
          selected() = Some(r)
        })),
          r.subRow.map { sr =>
            tags.tr(r.rowStyle)(
              tags.td(colspan := nbColumns, padding := 0, borderTop := "0px solid black")(
                sr.render
              )
            )
          }
        )
      }.flatten
        )
      //  (Seq(Some(fillRow(r.values, (s: String, _) => td(s))))

    )
  )

}
