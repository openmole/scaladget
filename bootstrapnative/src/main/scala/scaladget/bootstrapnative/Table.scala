package scaladget.bootstrapnative

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import scaladget.tools.Utils._

object Table {

  type RowID = String
val SELECTION_COLOR = "#52adf233"

  case class BSTableStyle(tableStyle: HESetters = emptySetters,
                          headerStyle: HESetters = emptySetters,
                          rowStyle: HESetters = emptySetters,
                          selectionColor: String = SELECTION_COLOR)

  case class Header(values: Seq[String])

  trait Row {
    val rowID: RowID = uuID.short("row")

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

  case class DataRow(values: Seq[String]) extends Row {
    def tds: Seq[HtmlElement] = values.map {v=>
      td(span(v))
    }
  }

  def headerRender(headers: Option[Table.Header] = None,
                   headerStyle: HESetters = emptySetters) =
    thead(headerStyle,
      tr(
        headers.map {
          h =>
            h.values.map {
              th(_)
            }
        }))
}

import Table._

case class DataTableBuilder(initialRows: Seq[Seq[String]],
                            headers: Option[Table.Header] = None,
                            bsTableStyle: BSTableStyle = Table.BSTableStyle(bordered_table)) {

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(values: String*) = copy(initialRows = initialRows :+ values)

  def style(tableStyle: HESetter = default_table, headerStyle: HESetters = emptySetters, rowStyle: HESetters = emptySetters, selectionColor: String = SELECTION_COLOR) = {
    copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle, rowStyle, selectionColor))
  }

  lazy val render = DataTable(initialRows.map( DataRow(_) ), headers, bsTableStyle)
}


case class ElementTableBuilder(initialRows: Seq[Row],
                               headers: Option[Table.Header] = None,
                               bsTableStyle: BSTableStyle = Table.BSTableStyle(bordered_table)) {

  def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

  def addRow(row: BasicRow): ElementTableBuilder = copy(initialRows = initialRows :+ row)

  def addRow(values: HtmlElement*): ElementTableBuilder = addRow(BasicRow(values))

  def style(tableStyle: HESetter = default_table, headerStyle: HESetters = emptySetters, rowStyle: HESetters = emptySetters, selectionColor: String = "#e1e1e1") = {
    copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle, rowStyle, selectionColor))
  }

  def expandTo(element: HtmlElement, signal: Signal[Boolean]) = {
    val lastRow = initialRows.last

    lastRow match {
      case br: BasicRow => copy(initialRows = initialRows.appended(ExpandedRow(element, signal)))
      case _ => this
    }
  }

  lazy val render = ElementTable(initialRows, headers, bsTableStyle)
}

case class ElementTable(initialRows: Seq[Row],
                        headers: Option[Table.Header] = None,
                        bsTableStyle: BSTableStyle = Table.BSTableStyle(default_table)) {


  val rows = Var(initialRows)
  val selected: Var[Option[RowID]] = Var(None)
  val expanded: Var[Seq[RowID]] = Var(Seq())


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

  val render =
    table(bsTableStyle.tableStyle,
      headerRender(headers, bsTableStyle.headerStyle),
      tbody(
        children <-- rows.signal.split(_.rowID)(rowRender)
      )
    )

}


case class DataTable(initialRows: Seq[DataRow],
                     headers: Option[Table.Header] = None,
                     bsTableStyle: BSTableStyle = Table.BSTableStyle(default_table)) {

  type Filter = String
  val rows = Var(initialRows)
  val filterString: Var[Filter] = Var("")
  val selected: Var[Option[RowID]] = Var(None)

  def rowFilter = (r: DataRow, filter: Filter)=>  r.values.mkString("|").toUpperCase.contains(filter)

  def setFilter(s: Filter) = filterString.set(s.toUpperCase)

  def rowRender(rowID: RowID, initialRow: Row, rowStream: Signal[Row]): HtmlElement =
    tr(bsTableStyle.rowStyle,
      backgroundColor <-- selected.signal.map {
        s =>
          if (Some(initialRow.rowID) == s) bsTableStyle.selectionColor else ""
      },
      onClick --> (_ => selected.set(Some(initialRow.rowID))),
      children <-- rowStream.map(r => r.tds)
    )

  def render = table(bsTableStyle.tableStyle,
    headerRender(headers, bsTableStyle.headerStyle),
    tbody(
        children <-- rows.signal.combineWith(filterString.signal).map{ case (dr,f) => dr filter {d=> rowFilter(d,f)}}.split(_.rowID)(rowRender)
    )
  )

}