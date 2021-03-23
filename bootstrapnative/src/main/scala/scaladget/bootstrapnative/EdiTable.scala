//package scaladget.bootstrapnative
//
//
//import org.scalajs.dom.raw._
//import scalaJsDom.{TypedTag, styles, tags}
//import scalaJsDom.all._
//import scaladget.tools.Stylesheet
//import scaladget.tools.JsRxTags._
//import bsn._
//import rx._
//import scaladget.bootstrapnative.Table.{BSTableStyle, StaticSubRow, SubRow}
//import com.raquo.laminar.api.L._
//
//
//trait EditableCell[T] {
//  val editMode: Var[Boolean]
//
//  def editable: Boolean
//
//  def editionAllowed = editMode.map{_ && editable}
//
//  def switch = editMode.update(!editMode.now)
//
//  def build: TypedTag[HTMLElement]
//
//  def get: T
//}
//
//
//case class TextCell(value: String, title: Option[String] = None, editing: Boolean = false, editable: Boolean = true) extends EditableCell[String] {
//  val editMode = Var(editing)
//
//  val editor = inputTag(value).render
//
//  def build = {
//    val t = title.getOrElse("")
//    div(EdiTable.rowFlex, styles.alignContent.flexStart)(
//      div(fontWeight.bold, marginRight := 10, width := 90)(t),
//      Rx {
//        if (editionAllowed()) div(editor)
//        else div(value)
//      }
//    )
//  }
//
//  def get = editor.value
//}
//
//
//case class PasswordCell(value: String, title: Option[String] = None, editing: Boolean = false, editable: Boolean = true) extends EditableCell[String] {
//
//  val editMode = Var(editing)
//
//  val editor = inputTag(value)(`type` := "password").render
//
//  def build = {
//    val t = title.getOrElse("")
//    div(EdiTable.rowFlex, styles.alignContent.flexStart)(
//      div(fontWeight.bold, marginRight := 10, width := 90)(t),
//        Rx {
//          if (editionAllowed()) div(editor)
//          else div(value.map { c => raw("&#9679") })
//        }
//    )
//  }
//
//  def get = editor.value
//}
//
//
//case class LabelCell(current: String,
//                     options: Seq[String],
//                     filter: String => Boolean = (f: String) => true,
//                     optionStyle: String => HESetter = (s: String) => label_default,
//                     title: Option[String] = None,
//                     editing: Boolean = false,
//                     editable: Boolean = true) extends EditableCell[String] {
//
//  import scaladget.bootstrapnative.Selector._
//
//  val editMode = Var(editing)
//
//  val editor = options.options(naming = (o => o.toString))
//
//  def build = {
//    val t = title.getOrElse("")
//    div(EdiTable.rowFlex, styles.alignContent.flexStart)(
//      div(fontWeight.bold, marginRight := 10, width := 90)(t),
//        Rx {
//          if (editionAllowed() && !options.isEmpty) editor.selector
//          else if (filter(current)) label(current.toString, optionStyle(current))(styles.display.flex, styles.justifyContent.center, styles.alignContent.center, padding := 5, margin := 1).render
//          else div().render
//        }
//    )
//  }
//
//  def get = editor.content.now.getOrElse(current)
//}
//
//
//case class TriggerCell(trigger: TypedTag[HTMLElement], editing: Boolean = false, editable: Boolean = true) extends EditableCell[Unit] {
//  val editMode = Var(editing)
//
//  def build = trigger(Stylesheet.pointer)
//
//  def get = {}
//}
//
//
//case class GroupCell(build: TypedTag[HTMLElement], editableCells: EditableCell[_]*) extends EditableCell[Unit] {
//
//  val editMode = Var(false)
//
//  val editable = true
//
//  editMode.triggerLater {
//    editableCells.foreach { ec =>
//      ec.editMode() = !ec.editMode.now
//    }
//  }
//
//  override def switch = {
//    editableCells.foreach {
//      _.switch
//    }
//  }
//
//  def get = {}
//}
//
//case class EditableRow(cells: Seq[EditableCell[_]]) {
//  def switchEdit = {
//    cells.foreach { c =>
//      c.editMode() = !c.editMode.now
//    }
//  }
//
//  def get = {}
//}
//
//case class ExpandableRow(editableRow: EditableRow, subRow: StaticSubRow) {
//
//}
//
//object EdiTable {
//  implicit def seqCellsToRow(s: Seq[EditableCell[_]]): EditableRow = EditableRow(s)
//
//  val rowFlex = Seq(display.flex, flexDirection.row)
//
//  val columnFlex = Seq(display.flex, flexDirection.column, justifyContent.center)
//}
//
//case class EdiTable(headers: Seq[String],
//                    cells: Seq[ExpandableRow],
//                    bsTableStyle: BSTableStyle = BSTableStyle(bordered_table, emptyMod)
//                   ) {
//
//  lazy val tableBody = tbody
//
//  cells.foreach { row =>
//    tableBody.appendChild(
//      tr(row.editableRow.cells.map
//    import scaladget.tools._{ c =>
//        td(c.build)
//      }))
//
//    tableBody.appendChild(buildSubRow(row.subRow))
//  }
//
//  private def buildSubRow(sub: StaticSubRow) =
//    tr(
//      td(colSpan := 999, padding := 0, borderTop := "0px solid black")(
//        sub.expander
//
//      )
//    )
//
//
//  lazy val render = {
//    table(bsTableStyle.tableStyle)(
//      thead(bsTableStyle.headerStyle)(
//        tr(headers.map {
//          th(_)
//        })),
//      tableBody
//    )
//  }
//}
