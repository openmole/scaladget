package scaladget.bootstrapnative

import org.scalajs.dom.raw._
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

  case class Row(values: Seq[TypedTag[HTMLElement]], rowStyle: ModifierSeq = emptyMod)

  sealed trait Cell {
    def value: TypedTag[HTMLElement]

    def cellIndex: Int
  }

  case class VarCell(value: TypedTag[HTMLElement], cellIndex: Int) extends Cell

  case class FixedCell(value: TypedTag[HTMLElement], cellIndex: Int) extends Cell

  def collectVar(cells: Seq[Cell]) = cells.collect { case v: VarCell => v }

  def reactiveRow(cells: Seq[Cell], rowStyle: ModifierSeq = emptyMod) =
    ReactiveRow(uuID.short("rr"), cells, rowStyle)

  case class ReactiveRow(uuid: ID, cells: Seq[Cell] = Seq(), rowStyle: ModifierSeq = emptyMod) {

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


    def varCells = (uuid, collectVar(cells))
  }

  type RowType = (String, Int) => TypedTag[HTMLElement]

  case class SubRow(subTypedTag: TypedTag[HTMLElement], trigger: Rx[Boolean] = Rx(false)){
    def expander = trigger.expand(subTypedTag)
  }

  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(r.values.zipWithIndex.map { v => VarCell(v._1, v._2) }, r.rowStyle)

}

import Table._


case class Table(reactiveRows: Rx.Dynamic[Seq[ReactiveRow]],
                 subRow: Option[ID => Table.SubRow] = None,
                 headers: Option[Table.Header] = None,
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod)) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val selected: Var[Option[ReactiveRow]] = Var(None)
  var previousState: Seq[(ID, Seq[VarCell])] = Seq()

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  //  private def insertRowInDom(row: ReactiveRow) = {
  //    row.rows.foreach { n =>
  //      tableBody.insertBefore(n, tableBody.firstChild)
  //    }
  //  }

  private def buildSubRow(rr: ReactiveRow, element: HTMLElement) = {
    println("BUILD SUB ROW " + element)
    tags.tr(id := s"${rr.uuid}sub" /*, rowStyle*/)(
      tags.td(colspan := 999, padding := 0, borderTop := "0px solid black")(
        element
      )
    ).render
  }

  private def addRowInDom(r: ReactiveRow) = {
    val aSubRow = subRow.map { sr =>
      sr(r.uuid)
    }

    Seq(Some(r.tr), aSubRow.map { s => buildSubRow(r, s.expander) }).flatten.foreach { node =>
      tableBody.appendChild(node)
    }
  }

  private def updateValues(element: HTMLTableRowElement, values: Seq[(TypedTag[HTMLElement], Int)]) = {
    for (
      (el, ind) <- values
    ) yield {
      val old = element.childNodes(ind)
      element.replaceChild(td(el), old)
    }
  }


  reactiveRows.trigger {
    val inBody = bodyIds
    val rowsAndSubs = reactiveRows.now.map {
      r =>
        Seq(r.uuid, s"${
          r.uuid
        }sub")
    }.flatten
    val varCells = reactiveRows.now.map {
      _.varCells
    }

    (rowsAndSubs.length - bodyIds.length) match {
      case x if x > 0 =>
        // CASE ADD
        reactiveRows.now.foreach {
          rr =>
            if (!inBody.contains(rr.uuid)) {
              addRowInDom(rr)
            }
        }
      case x if x < 0 =>
        // CASE DELETE
        inBody.foreach {
          id =>
            if (!rowsAndSubs.contains(id)) {
              findIndex(id).foreach {
                tableBody.deleteRow
              }
            }
        }
      case _ =>
        // CASE UPDATE
        val di = varCells diff previousState
        di.foreach {
          m =>
            findIndex(m._1).map {
              i =>
                updateValues(tableBody.rows(i).asInstanceOf[HTMLTableRowElement], m._2.map {
                  v => (v.value, v.cellIndex)
                })
            }
        }
    }
    previousState = varCells
  }

  def bodyIds = {
    val size = tableBody.rows.length
    if (size > 0) {
      (0 to size - 1).map {
        i =>
          tableBody.rows(i).id
      }
    } else Seq()
  }


  def findIndex(reactiveRow: ReactiveRow): Option[Int] = findIndex(reactiveRow.uuid)

  def findIndex(id: ID): Option[Int] = {
    val lenght = tableBody.rows.length

    def findIndex0(currentIndex: Int, found: Boolean): Option[Int] = {
      if (found) Some(currentIndex - 1)
      else if (currentIndex == lenght) None
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
          headers.map {
            h =>
              h.values.map {
                th(_)
              }
          })),
      tableBody
    )
  }

}
