package scaladget.bootstrapnative

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import scaladget.bootstrapnative.Table.Column
import scaladget.tools.Utils._

import scala.util.Try

object Sorting {
  trait Sorting

  object NoSorting extends Sorting

  object PhantomSorting extends Sorting

  object AscSorting extends Sorting

  object DescSorting extends Sorting

  case class SortingStatus(col: Int, sorting: Sorting)

  val defaultSortingStatus = SortingStatus(0, AscSorting)

  def sortInt(seq: Seq[String]) = Try(
    seq.map(_.toInt).zipWithIndex.sortBy(_._1).map(_._2)
  ).toOption

  def sortDouble(seq: Seq[String]) = Try(
    seq.map(_.toDouble).zipWithIndex.sortBy(_._1).map(_._2)
  ).toOption

  def sortString(seq: Seq[String]): Seq[Int] = seq.zipWithIndex.sortBy(_._1).map(_._2)

  def sort(s: Column): Seq[Int] = {
    sortInt(s.values) match {
      case Some(i: Seq[_]) => i
      case None => sortDouble(s.values) match {
        case Some(d: Seq[_]) => d
        case None => sortString(s.values)
      }
    }
  }
}

import Sorting._

object Table {

  type RowID = String
  val SELECTION_COLOR = "#52adf233"

  val centerCell = Seq(verticalAlign.middle, textAlign.center)

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
      td(_, centerCell)
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

  case class Column(values: Seq[String])

  case class DataRow(values: Seq[String]) extends Row {
    def tds: Seq[HtmlElement] = values.map { v =>
      td(span(v), centerCell)
    }
  }

  def column(index: Int, rows: Seq[DataRow]): Column = Column(rows.map {
    _.values(index)
  })

  def headerRender(headers: Option[Table.Header] = None,
                   headerStyle: HESetters = emptySetters,
                   sortingDiv: Option[Int => Span] = None) =
    thead(headerStyle,
      tr(
        headers.map {
          h =>
            h.values.zipWithIndex.map { case (v, id) =>
              th(centerCell, v, sortingDiv.map { f => f(id) }.getOrElse(emptyNode))
            }
        }))


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
                       bsTableStyle: BSTableStyle = Table.BSTableStyle(default_table),
                       sorting: Boolean = false) {

    type Filter = String
    val rows = Var(initialRows)
    val filterString: Var[Filter] = Var("")
    val selected: Var[Option[RowID]] = Var(None)


    //SORTING
    val nbColumns = initialRows.headOption.map(_.values.length).getOrElse(0)

    val sortingStatus: Var[SortingStatus] = Var(defaultSortingStatus)

    def columnSort(filteredRows: Seq[DataRow], sortingStatus: SortingStatus) = {
      val col = column(sortingStatus.col, filteredRows)
      val indexes: Seq[Int] = {
        val sorted = Sorting.sort(col)
        sortingStatus.sorting match {
          case DescSorting => sorted.reverse
          case _ => sorted
        }
      }

      for (
        i <- indexes
      ) yield filteredRows(i)
    }

    def sortingGlyph(sortingStatus: SortingStatus) =
      sortingStatus.sorting match {
        case PhantomSorting => glyph_sort_down_alt
        case AscSorting => glyph_sort_down_alt
        case DescSorting => glyph_sort_down
        case _ => ""
      }

    def sortingGlyphOpacity(n: Int, selectedColumun: Int): Double = {
      if (n == selectedColumun) 1
      else 0.4
    }

    val sortingDiv = (n: Int) =>
      // val ss = sortingStatuses()
      span(cursor.pointer, float.right, fontSize := "23",
        opacity <-- sortingStatus.signal.map { ss => sortingGlyphOpacity(n, ss.col) },
        cls <-- sortingStatus.signal.map(ss => sortingGlyph(ss)),
        onClick --> { _ =>
          if (sorting) {
            sortingStatus.update(ss =>
              SortingStatus(n,
                ss.sorting match {
                  case DescSorting | PhantomSorting => AscSorting
                  case AscSorting => DescSorting
                  case _ => PhantomSorting
                })
            )
          }
        }
      )

    //FILTERING
    def rowFilter = (r: DataRow, filter: Filter) => r.values.mkString("|").toUpperCase.contains(filter)

    def setFilter(s: Filter) = filterString.set(s.toUpperCase)


    //RENDERING
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
      headerRender(headers, bsTableStyle.headerStyle, Some(sortingDiv)),
      tbody(overflow.auto,
        children <-- rows.signal.combineWith(filterString.signal).combineWith(sortingStatus.signal).map {
          case (dr, f, s) =>
            val filteredRows = dr.filter { d => rowFilter(d, f) }
            columnSort(filteredRows, s)
        }.split(_.rowID)(rowRender)
      )
    )

  }

  case class DataTableBuilder(initialRows: Seq[Seq[String]],
                              headers: Option[Table.Header] = None,
                              bsTableStyle: BSTableStyle = Table.BSTableStyle(bordered_table),
                              sorting: Boolean = false) {

    def addHeaders(hs: String*) = copy(headers = Some(Header(hs)))

    def addRow(values: String*) = copy(initialRows = initialRows :+ values)

    def style(tableStyle: HESetter = default_table, headerStyle: HESetters = emptySetters, rowStyle: HESetters = emptySetters, selectionColor: String = SELECTION_COLOR) = {
      copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle, rowStyle, selectionColor))
    }

    def sortable = copy(sorting = true)

    lazy val render = DataTable(initialRows.map(DataRow(_)), headers, bsTableStyle, sorting)
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

}