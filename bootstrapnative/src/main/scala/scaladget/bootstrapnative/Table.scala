package scaladget.bootstrapnative

import org.scalajs.dom.raw.{Element, HTMLElement}
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

  def reactiveRow(values: Seq[TypedTag[HTMLElement]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) =
    ReactiveRow(uuID.short("rr"), values, rowStyle, subRow)

  case class ReactiveRow(uuid: ID, values: Seq[TypedTag[HTMLElement]] = Seq(), rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) {

    lazy val tr = tags.tr(id := uuid)(values.map {
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

    lazy val subtr = subRow.map { sr =>
      tags.tr(id:= uuid, rowStyle)(
        tags.td(colspan := 999, padding := 0, borderTop := "0px solid black")(
          sr.render
        )
      ).render
    }

    def rows = Seq(Some(tr), subtr).flatten
  }

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)) {
    val stableDiv = div(subTypedTag)

    def render = trigger.expand(stableDiv)
  }

  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(r.values, r.rowStyle, r.subRow)


  def updateValues(element: Element, values: Seq[TypedTag[HTMLElement]]) = {
    while (element.firstChild != null) {
      element.removeChild(element.firstChild)
    }
    values.foreach { x =>
      element.appendChild(tags.td(x.render))
    }
  }
}

import Table._

case class Table(rows: Rx[Seq[ReactiveRow]],
                 updater: Option[ID=> Rx[Seq[TypedTag[HTMLElement]]]] = None,
                 headers: Option[Table.Header] = None,
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod),
                ) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val selected: Var[Option[ReactiveRow]] = Var(None)

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  private def insertRowInDom(row: ReactiveRow) = {
    row.rows.foreach { n =>
      tableBody.insertBefore(n, tableBody.firstChild)
    }
  }

  private def addRowInDom(row: ReactiveRow) = {
    row.rows.foreach { r =>
      tableBody.appendChild(r)
      setUpdater(row.uuid)
    }
  }

  rows.trigger {
    val inBody = getIndexedTrIds

    rows.now.foreach { rr =>
      if (!inBody.values.toSeq.contains(rr.uuid)) {
        addRowInDom(rr)

      }
    }

    inBody.foreach {
      case (index, id) =>
        if (!rows.now.map {
          _.uuid
        }.contains(id)) {
          tableBody.deleteRow(index)
        }
    }
  }


  def setUpdater(id: ID) = {
    updater.map{u=>
      val rx = u(id)
      rx.triggerLater{
        findIndex(id).map{i=>
          Table.updateValues(tableBody.rows(i), rx.now)
        }
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

  def deleteID(id: ID) = {
    rows.now.filter {
      _.uuid == id
    }.foreach { rr =>
      delete(rr)
    }
  }

  def getIndexedTrIds = {
    val size = tableBody.rows.length
    if (size > 0) {
      (0 to size - 1).map { i =>
        i -> tableBody.rows(i).id
      }.toMap
    } else Map()
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

    if(lenght == 0) None
    else findIndex0(0, false)
  }

  lazy val tableBody = tags.tbody.render

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
