//package scaladget.bootstrapnative
//
//import scaladget.tools.Utils._
//import bsn._
//import scaladget.bootstrapnative.Table.BSTableStyle
//import com.raquo.laminar.api.L._
//import org.scalajs.dom.raw.HTMLTableRowElement
//
//object Table {
//
//  case class BSTableStyle(tableStyle: HESetters = emptySetter,
//                          headerStyle: HESetters = emptySetter,
//                          selectionColor: String = "#e1e1e1")
//
//  case class Header(values: Seq[String])
//
//  case class Row(values: Seq[HtmlElement], rowStyle: HESetters = emptySetter)
//
//  sealed trait Cell {
//    def value: HtmlElement
//
//    def cellIndex: Int
//  }
//
//  case class VarCell(value: HtmlElement, cellIndex: Int) extends Cell
//
//  case class FixedCell(value: HtmlElement, cellIndex: Int) extends Cell
//
//  def collectVar(cells: Seq[Cell]) = cells.collect { case v: VarCell => v }
//
////  def reactiveRow(cells: Seq[Cell], rowStyle: HESetters = emptySetter) =
////    ReactiveRow(uuID.short("rr"), cells, rowStyle)
////
////  case class ReactiveRow(uuid: ID, cells: Seq[Cell] = Seq(), rowStyle: HESetters = emptySetter) {
////
////    lazy val tr = tr(idAttr := uuid)(cells.map { c =>
////      td(c.value)
////    }
////      //      ,
////      //      backgroundColor := Rx {
////      //        if (Some(row) == selected()) tableStyle.selectionColor else ""
////      //      }
////    )(rowStyle)
////    //    (onclick := { () =>
////    //      table.selected() = Some(this)
////    //    }
////    //    )
////
////
////    def varCells = (uuid, collectVar(cells))
////  }
//
//  def subID(id: ID) = s"${id}sub"
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
//
//
////  implicit def rowToReactiveRow(r: Row): ReactiveRow = reactiveRow(r.values.zipWithIndex.map { v => VarCell(v._1, v._2) }, r.rowStyle)
//
//}
//
//import Table._
//
//case class Table(rows: Seq[Row],
//                 subRow: Option[ID => Table.SubRow] = None,
//                 headers: Option[Table.Header] = None,
//                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod)) {
//
//  val selected: Var[Option[Row]] = Var(None)
//  var previousState: Seq[(ID, Seq[VarCell])] = Seq()
//  val inDOM: Var[Seq[ID]] = Var(Seq())
//
//  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))
//
//  def style(tableStyle: HESetter = default_table, headerStyle: HESetters = emptySetter) = {
//    copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle))
//  }
//
//  private def buildSubRow(rr: Row, sr: ID => SubRow) = {
//    val sub = sr(rr.uuid)
//    tr(
//      idAttr := subID(rr.uuid), /*, rowStyle*/
//      td(
//        colSpan := 999, padding := 0, borderTop := "0px solid black",
//        sub.expander
//      )
//    )
//  }
//
//  private def addRowInDom(r: Row) = {
//    tableBody.appendChild(r.tr)
//    inDOM() = inDOM.now :+ r.uuid
//
//    subRow.foreach { sr =>
//      inDOM() = inDOM.now :+ subID(r.uuid)
//      tableBody.appendChild(buildSubRow(r, sr))
//    }
//  }
//
//  private def updateValues(element: HTMLTableRowElement, values: Seq[(HtmlElement, Int)]) = {
//    for (
//      (el, ind) <- values
//    ) yield {
//      val old = element.childNodes(ind)
//      element.replaceChild(td(el), old)
//    }
//  }
//
//
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
//
//  def findIndex(reactiveRow: Row): Option[Int] = findIndex(reactiveRow.uuid)
//
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
//
//  lazy val tableBody = tbody
//
//  lazy val render = {
//
//    table(bsTableStyle.tableStyle,
//      thead(bsTableStyle.headerStyle,
//        tr(
//          headers.map {
//            h =>
//              h.values.map {
//                th(_)
//              }
//          })),
//      tableBody
//    )
//  }
//
//}
