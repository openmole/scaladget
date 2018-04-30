package scaladget.bootstrapnative

import bsn._
import org.scalajs.dom.raw.HTMLElement
import scaladget.tools._
import rx._
import scalatags.JsDom.all._
import scalatags.JsDom.{TypedTag, tags}
import scala.util.Try

case class Row(values: Seq[String])

case class Column(values: Seq[String])

case class BSTableStyle(tableStyle: TableStyle, headerStyle: ModifierSeq, selectionColor: String = "#e1e1e1")

trait Sorting

object NoSorting extends Sorting

object PhantomSorting extends Sorting

object AscSorting extends Sorting

object DescSorting extends Sorting

case class SortingStatus(col: Int, sorting: Sorting)

object Table {

  def sortInt(seq: Seq[String]) = Try(
    seq.map {_.toInt}.zipWithIndex.sortBy {_._1}.map {_._2}
  ).toOption

  def sortDouble(seq: Seq[String]) = Try(
    seq.map {_.toDouble}.zipWithIndex.sortBy {_._1}.map {_._2}
  ).toOption

  def sortString(seq: Seq[String]): Seq[Int] = seq.zipWithIndex.sortBy {_._1}.map {_._2}

  def sort(s: Column): Seq[Int] = {
    sortInt(s.values) match {
      case Some(i: Seq[_]) => i
      case None => sortDouble(s.values) match {
        case Some(d: Seq[_]) => d
        case None => sortString(s.values)
      }
    }
  }

  def column(index: Int, rows: Seq[Row]): Column = Column(rows.map{_.values(index)})
}

import Table._

case class Table(headers: Row = Row(Seq()),
                 rows: Seq[Row] = Seq(),
                 bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod),
                 sorting: Boolean = false) {

  val filteredRows = Var(rows)
  val selected: Var[Option[Row]] = Var(None)
  val nbColumns = rows.headOption.map{_.values.length}.getOrElse(0)

  val sortingStatuses = Var(
    Seq.fill(nbColumns)(
      if (sorting) SortingStatus(0, PhantomSorting) else SortingStatus(0, NoSorting))
  )


  def addHeaders(hs: String*) = copy(headers = Row(hs))

  def addRow(row: Row): Table = copy(rows = rows :+ row)

  def addRow(row: String*): Table = addRow(Row(row))

  def sortable = copy(sorting = true)

  type RowType = (String, Int) => TypedTag[HTMLElement]

  private def fillRow(row: Row, rowType: RowType) = tags.tr(
    for (
      (cell, id) <- row.values.zipWithIndex
    ) yield {
      rowType(cell, id)
    },
    backgroundColor := Rx{
      if(Some(row) == selected()) bsTableStyle.selectionColor else ""
    }
  )(onclick := {()=>
    selected() = Some(row)
  })

  def filter(containedString: String) = {
    filteredRows() = rows.filter { r =>
      r.values.mkString("").toUpperCase.contains(containedString.toUpperCase)
    }
  }

  def style(tableStyle: TableStyle = default_table, headerStyle: ModifierSeq = emptyMod) = {
    copy(bsTableStyle = BSTableStyle(tableStyle, headerStyle))
  }

  val sortingDiv = (n: Int) => tags.span(
    Rx {
      val ss = sortingStatuses()
      span(pointer, floatRight, lineHeight := "25px",
        ss(n).sorting match {
          case PhantomSorting => Seq(opacity := "0.4") +++ glyph_sort_by_attributes
          case AscSorting => glyph_sort_by_attributes
          case DescSorting => glyph_sort_by_attributes_alt
          case _ => emptyMod
        }
      )
    },
    onclick := { () =>
      if (sorting) {
        val ss = sortingStatuses.now
        sortingStatuses() = ss.map {
          _.copy(sorting = PhantomSorting)
        }.updated(n, ss(n).copy(sorting = ss(n).sorting match {
          case DescSorting | PhantomSorting => sort(n, AscSorting)
          case AscSorting => sort(n, DescSorting)
          case _ => PhantomSorting
        }
        )
        )
      }
    }
  )

  def sort(colIndex: Int, sorting: Sorting): Sorting = {
    val col = column(colIndex, filteredRows.now)
    val indexes: Seq[Int] = {
      val sorted = Table.sort(col)
      sorting match {
        case DescSorting => sorted.reverse
        case _ => sorted
      }
    }

    filteredRows() = {
      for (
        i <- indexes
      ) yield filteredRows.now(i)
    }
    sorting
  }

  val render = {
    tags.table(bsTableStyle.tableStyle)(
      tags.thead(bsTableStyle.headerStyle)(
        fillRow(headers, (s: String, i: Int) => th(s, sortingDiv(i)))
      ),
      Rx {
        tags.tbody(
          for (r <- filteredRows()) yield {
            fillRow(r, (s: String, _) => td(s))
          }
        )
      }
    )
  }
}

