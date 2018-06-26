package scaladget.bootstrapnative

import org.scalajs.dom.raw.{Element, HTMLElement, HTMLTableRowElement, Node}
import scalatags.JsDom.{TypedTag, tags}
import scalatags.JsDom.all._
import scaladget.tools.{ModifierSeq, emptyMod}
import scaladget.tools.Utils._
import scaladget.tools.JsRxTags._
import bsn._
import rx._
import scaladget.bootstrapnative.Table.BSTableStyle

object Table {

  case class BSTableStyle(tableStyle: TableStyle, headerStyle: ModifierSeq, selectionColor: String = "#e1e1e1")

  case class Header(values: Seq[String])

  case class Row(values: Seq[TypedTag[HTMLElement]], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None)

  sealed trait Cell{
    def value: TypedTag[HTMLElement]
    def cellIndex: Int
  }
  case class VarCell(value: TypedTag[HTMLElement], cellIndex: Int) extends Cell
  case class FixedCell(value: TypedTag[HTMLElement], cellIndex: Int) extends Cell

  def collectVar(cells: Seq[Cell]) = cells.collect{case v: VarCell=> v}

  def reactiveRow(cells: Seq[Cell], rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) =
    ReactiveRow(uuID.short("rr"), cells, rowStyle, subRow)

  case class ReactiveRow(uuid: ID, cells: Seq[Cell] = Seq(), rowStyle: ModifierSeq = emptyMod, subRow: Option[SubRow] = None) {

    lazy val tr = tags.tr(id := uuid)(cells.map { c =>
      tags.td(c.value)
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
      tags.tr(id := s"${uuid}sub", rowStyle)(
        tags.td(colspan := 999, padding := 0, borderTop := "0px solid black")(
          sr.render
        )
      ).render
    }

    lazy val rows = {
      println("############$ ROZS " + Seq(Some(tr), subtr).flatten.map {
        _.id
      })
      Seq(Some(tr), subtr).flatten
    }

    def varCells = (uuid, collectVar(cells))
  }

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)) {
    val stableDiv = div(subTypedTag)

    val render = {
      trigger.expand(stableDiv)
    }
  }

  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(r.values.zipWithIndex.map{v=> VarCell(v._1, v._2)}, r.rowStyle, r.subRow)

  def updateValues(element: HTMLTableRowElement, values: Seq[(TypedTag[HTMLElement], Int)]) = {
    for (
      (el, ind) <- values
    ) yield {
      val old = element.childNodes(ind)
      println("REPLACE " + old + " by " + el)
      element.replaceChild(td(el), old)
    }
  }
}

import Table._


case class Table(rows: Rx[Seq[ReactiveRow]],
                 //  updater: Option[Rx[ID=> Seq[(Int,TypedTag[HTMLElement])]]] = None,
                 //  toBeApdated: Rx[ID]
                 headers: Option[Table.Header] = None,
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod),
                ) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val selected: Var[Option[ReactiveRow]] = Var(None)
  val previousState: Var[Seq[(ID, Seq[VarCell])]] = Var(rows.now.map{_.varCells})

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  private def insertRowInDom(row: ReactiveRow) = {
    row.rows.foreach { n =>
      tableBody.insertBefore(n, tableBody.firstChild)
    }
  }

  private def addRowInDom(row: ReactiveRow) = {
    println("ADD ROZ in DON " + row.rows.length)
    row.rows.foreach { r =>
      println("APPENDDD " + row.uuid)
      tableBody.appendChild(r)
    }
  }

  // CASE ADD
  rows.trigger {

    println("subrows " + rows.now.map{_.subRow})
    val inBody = getIndexedTrIds
    val varCells = rows.now.map{_.varCells}

    var modif = false
    rows.now.foreach { rr =>
      if (!inBody.values.toSeq.contains(rr.uuid)) {
        modif = true
        addRowInDom(rr)
      }
    }

    // CASE DELETE
    val rowsAndSubs = rows.now.map { r =>
      Seq(r.uuid, s"${r.uuid}sub")
    }.flatten
    inBody.foreach {
      case (index, id) =>
        if (!rowsAndSubs.contains(id)) {
          println("--  DELETE " + index)
          modif = true
          tableBody.deleteRow(index)
        }
    }

    //CASE UPDATE
    if (!modif) {
      val di = varCells diff previousState.now
      println("DIFF " + di)
      di.foreach { m =>
        println("--------UPDATE " + m)
        findIndex(m._1).map { i =>
          Table.updateValues(tableBody.rows(i).asInstanceOf[HTMLTableRowElement], m._2.map{v=> (v.value, v.cellIndex)})
        }
      }
    }

    previousState() = varCells
    println("END " + previousState.now)
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

    if (lenght == 0) None
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
