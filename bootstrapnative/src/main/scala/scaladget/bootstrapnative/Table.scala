package scaladget.bootstrapnative

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import scaladget.tools.Utils._

object Table {

  type RowID = String

  case class BSTableStyle(tableStyle: HESetters = emptySetters,
                          headerStyle: HESetters = emptySetters,
                          rowStyle: HESetters = emptySetters,
                          selectionColor: String = "#e1e1e1")

  case class Header(values: Seq[String])

  trait Row {
    def rowID: RowID = uuID.short("row")

    def tds: Seq[HtmlElement]
  }

  case class BasicRow(elements: Seq[HtmlElement]) extends Row {
    def tds = elements.map {
      td(_)
    }
  }

  case class ExpandedRow(element: HtmlElement, signal: Signal[Boolean]) extends Row {

    def tds: Seq[HtmlElement] =
      Seq(
        td(
          colSpan := 999, padding := "0", borderTop := "0px solid black",
          signal.expand(element)
        )
      )
  }

  //  implicit class TableToSub(t: Table) {
  //    def expandTo(element: HtmlElement, trigger: Signal[Boolean]) = {
  //      val lastRow = t.initialRows.last
  //      println("Last row " + lastRow)
  //      lastRow match {
  //        case br: BasicRow=>
  //          println("init: " + t.initialRows)
  //          val aa = t.initialRows.appended(ExpandableRow(br, SubRow(element, trigger)))
  //          println("AA  + " + aa)
  //          val oo = t.copy(initialRows = aa)
  //          println("initII: " + t.initialRows)
  //          oo
  //        case _=> t
  //      }
  //    }
  //  }

  //case class Cell(value: HtmlElement, cellIndex: Int)

  //  def reactiveRow(cells: Seq[Cell], rowStyle: HESetters = emptySetter) =
  //    ReactiveRow(uuID.short("rr"), cells, rowStyle)
  //
  //  case class ReactiveRow(uuid: ID, cells: Seq[Cell] = Seq(), rowStyle: HESetters = emptySetter) {
  //
  //    lazy val tr = tr(idAttr := uuid)(cells.map { c =>
  //      td(c.value)
  //    }
  //      //      ,
  //      //      backgroundColor := Rx {
  //      //        if (Some(row) == selected()) tableStyle.selectionColor else ""
  //      //      }
  //    )(rowStyle)
  //    //    (onclick := { () =>
  //    //      table.selected() = Some(this)
  //    //    }
  //    //    )
  //
  //
  //    def varCells = (uuid, collectVar(cells))
  //  }

  //  def subID(id: TableID) = s"${id}sub"
  //
  //  type RowType = (String, Int) => HtmlElement
  //
  //  case class SubRow(element: HtmlElement, trigger: Rx[Boolean] = Rx(false)) {
  //    val expander = div(Rx {
  //      trigger.expand(element())
  //    })
  //  }
  //
  //  case class StaticSubRow(element: HtmlElement, trigger: Var[Boolean] = Var(false)) {
  //    val expander = div(Rx {
  //      trigger.expand(element)
  //    })
  //  }
  //
  //  object StaticSubRow {
  //    def empty() = StaticSubRow(div, Var(false))
  //
  //  }
  //
  //  object SubRow {
  //    def empty() = SubRow(Var(div), Rx(true))
  //  }


  //  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(r.values.zipWithIndex.map { v => VarCell(v._1, v._2) }, r.rowStyle)

}

import Table._

case class TableBuilder(initialRows: Seq[Row],
                        headers: Option[Table.Header] = None,
                        bsTableStyle: BSTableStyle = Table.BSTableStyle(default_table)) {

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(row: Row) = copy(initialRows = initialRows :+ row)

  def addRow(values: String*) = copy(initialRows = initialRows :+ BasicRow(values.map {
    span(_)
  }))

  implicit class RowToSub(br: BasicRow) {
    def expandTo(element: HtmlElement, signal: Signal[Boolean]) = ExpandedRow(element, signal)
  }

  def expandTo(element: HtmlElement, signal: Signal[Boolean]) = {
    val lastRow = initialRows.last

    lastRow match {
      case br: BasicRow => copy(initialRows = initialRows.appended(ExpandedRow(element, signal)))
      case _ => this
    }
  }

  lazy val render = Table(initialRows, headers, bsTableStyle).render
}

case class Table(initialRows: Seq[Row],
                 headers: Option[Table.Header] = None,
                 bsTableStyle: BSTableStyle = Table.BSTableStyle(default_table)) {


  val rows = Var(initialRows)
  val selected: Var[Option[RowID]] = Var(None)
  val expanded: Var[Seq[RowID]] = Var(Seq())
  // var previousState: Seq[(ID, Seq[VarCell])] = Seq()
  // val inDOM: Var[Seq[ID]] = Var(Seq())


  def style(tableStyle: HESetter = default_table, headerStyle: HESetters = emptySetters) = {
    copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle))
  }


  def updateExpanded(rowID: RowID) = expanded.update { e =>
    if (e.contains(rowID)) e.filterNot(_ == rowID)
    else e.appended(rowID)
  }

  def rowRender(rowID: RowID, initialRow: Row, rowStream: Signal[Row]): HtmlElement = {

    initialRow match {
      case br: BasicRow =>
        tr(bsTableStyle.rowStyle,
          backgroundColor <-- selected.signal.map {
            s => if (Some(initialRow.rowID) == s) bsTableStyle.selectionColor else ""
          },
          onClick --> (_ => selected.set(Some(initialRow.rowID))),
          children <-- rowStream.map(r => r.tds)
        )
      case er: ExpandedRow =>
        tr(
          child <-- rowStream.map(rs => rs.tds.head)
        )
    }
  }

  val render = {
    table(bsTableStyle.tableStyle,
      thead(bsTableStyle.headerStyle,
        tr(
          headers.map {
            h =>
              h.values.map {
                th(_)
              }
          })),
      tbody(
        children <-- rows.signal.split(_.rowID)(rowRender)
      )
    )
  }
  //      ,
  //      backgroundColor := Rx {
  //        if (Some(row) == selected()) tableStyle.selectionColor else ""
  //      }
  //    (onclick := { () =>
  //      table.selected() = Some(this)
  //    }
  //    )
  //  private def addRowInDom(r: Row) = {
  //    tableBody.appendChild(r.tr)
  //    inDOM() = inDOM.now :+ r.uuid
  //
  //    subRow.foreach { sr =>
  //      inDOM() = inDOM.now :+ subID(r.uuid)
  //      tableBody.appendChild(buildSubRow(r, sr))
  //    }
  //  }

  //  private def updateValues(element: HtmlElement, values: Seq[(HtmlElement, Int)]) = {
  //    for (
  //      (el, ind) <- values
  //    ) yield {
  //      val old = element.childNodes(ind)
  //      element.replaceChild(td(el), old)
  //    }
  //  }


  //  rows.trigger {
  //    val inBody = inDOM.now
  //    val rowsAndSubs = rows.now.map {
  //      r =>
  //        Seq(r.uuid, subID(r.uuid))
  //    }.flatten
  //    val varCells = rows.now.map {
  //      _.varCells
  //    }
  //
  //    (rowsAndSubs.length - inBody.length) match {
  //      case x if x > 0 =>
  //        // CASE ADD
  //        rows.now.foreach {
  //          rr =>
  //            if (!inDOM.now.contains(rr.uuid)) {
  //              addRowInDom(rr)
  //            }
  //        }
  //      case x if x < 0 =>
  //        // CASE DELETE
  //        inBody.foreach {
  //          id =>
  //            if (!rowsAndSubs.contains(id)) {
  //              findIndex(id).foreach {
  //                inDOM() = inDOM.now.filterNot(_ == id)
  //
  //                tableBody.deleteRow
  //              }
  //            }
  //        }
  //      case _ =>
  //        // CASE UPDATE
  //        val di = varCells diff previousState
  //        di.foreach {
  //          m =>
  //            findIndex(m._1).map {
  //              i =>
  //                updateValues(tableBody.rows(i).asInstanceOf[HTMLTableRowElement], m._2.map {
  //                  v => (v.value, v.cellIndex)
  //                })
  //            }
  //        }
  //    }
  //    previousState = varCells
  //  }

  // def findIndex(reactiveRow: Row): Option[Int] = findIndex(reactiveRow.uuid)

  //  def findIndex(id: ID): Option[Int] = {
  //    val lenght = tableBody.rows.length
  //
  //    def findIndex0(currentIndex: Int, found: Boolean): Option[Int] = {
  //      if (found) Some(currentIndex - 1)
  //      else if (currentIndex == lenght) None
  //      else {
  //        findIndex0(currentIndex + 1, tableBody.rows(currentIndex).id == id)
  //      }
  //    }
  //
  //    if (lenght == 0) None
  //    else findIndex0(0, false)
  //  }

}
