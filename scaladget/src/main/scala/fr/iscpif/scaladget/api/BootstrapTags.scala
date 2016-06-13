package fr.iscpif.scaladget.api

/*
 * Copyright (C) 27/05/15 // mathieu.leclaire@openmole.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import org.scalajs.dom.html.Div
import org.scalajs.dom.raw._
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.{TypedTag, tags ⇒ tags}
import scalatags.JsDom.all._
import fr.iscpif.scaladget.tools.JsRxTags._
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.bootstrap._
import org.querki.jquery._
import sheet._
import rx._
import Popup._
import Select._

@JSExport("BootstrapTags")
object BootstrapTags {
  bstags =>

  implicit def formTagToNode(tt: HtmlTag): org.scalajs.dom.Node = tt.render

  /*implicit class BootstrapTypedTag[+Output <: raw.Element](t: TypedTag[Output]) {
    def +++(m: Seq[Modifier]) = t.copy(modifiers = t.modifiers :+ m.toSeq)
  }*/

  def uuID: String = java.util.UUID.randomUUID.toString

  // INPUT
  def input(content: String = "") = tags.input(formControl, value := content)

  def inputGroup(modifierSeq: ModifierSeq = emptyMod) = div(modifierSeq +++ sheet.inputGroup)

  def inputGroupButton = span(toClass("input-group-btn"))

  def inputGroupAddon = span(toClass("input-group-addon"))

  val input_group_lg = "input-group-lg"

  def fileInputMultiple(todo: HTMLInputElement ⇒ Unit) = {
    lazy val input: HTMLInputElement = tags.input(id := "fileinput", `type` := "file", multiple)(onchange := { () ⇒
      todo(input)
    }).render
    input
  }

  def fileInput(todo: HTMLInputElement ⇒ Unit) = {
    lazy val input: HTMLInputElement = tags.input(id := "fileinput", `type` := "file")(onchange := { () ⇒
      todo(input)
    }).render
    input
  }


  // CHECKBOX
  def checkbox(default: Boolean) = tags.input(`type` := "checkbox", if (default) checked)

  trait Displayable {
    def name: String
  }

  // BUTTONS
  // default button with default button style and displaying a text
  def button(content: String, todo: () ⇒ Unit): TypedTag[HTMLButtonElement] = button(content, btn_default, todo)

  // displaying a text, with a button style and an action
  def button(content: String, buttonStyle: ModifierSeq, todo: () ⇒ Unit): TypedTag[HTMLButtonElement] =
    tags.button(buttonStyle, `type` := "button", onclick := { () ⇒ todo() })(content)

  // displaying an HTHMElement, with a a button style and an action
  def button(content: TypedTag[HTMLElement], buttonStyle: ModifierSeq, todo: () ⇒ Unit): TypedTag[HTMLButtonElement] =
    button("", buttonStyle, todo)(content)

  // displaying a text with a button style and a glyphicon
  def glyphButton(text: String, buttonStyle: ModifierSeq = Seq(), glyphicon: ModifierSeq = Seq(), todo: () ⇒ Unit = () => {}): TypedTag[HTMLButtonElement] =
    button(text, buttonStyle, todo)(pointer, `type` := "button")(span(glyphicon))

  // Clickable span containing a glyphicon and a text
  def glyphSpan(glyphicon: ModifierSeq, onclickAction: () ⇒ Unit = () ⇒ {}, text: String = ""): TypedTag[HTMLSpanElement] =
    span(glyphicon, aria.hidden := "true", onclick := { () ⇒ onclickAction() })(text)


  // PROGRESS BAR
  def progressBar(barMessage: String, ratio: Int): TypedTag[HTMLDivElement] =
    div(progress)(
      div(sheet.progressBar)(width := ratio.toString() + "%")(
        barMessage
      )
    )


  // BADGE
  def badge(content: String, badgeValue: String, buttonStyle: ModifierSeq, todo: () => Unit) =
    button(s"$content ", buttonStyle, todo)(span(toClass("badge"))(badgeValue))


  //BUTTON GROUP
  def buttonGroup(mod: ModifierSeq = Seq()) = div(mod +++ btnGroup)

  def buttonToolBar = div(btnToolbar)(role := "toolbar")

  def tototo = div


  /////TO BE REMOVED  ----
  //MODAL
  type Dialog = TypedTag[HTMLDivElement]
  type ModalID = String

  def modalDialog(ID: ModalID, typedTag: TypedTag[_]*): Dialog =
    div(modal +++ fade)(id := ID,
      div(sheet.modalDialog)(
        div(modalContent)(
          typedTag)
      )
    )

  def headerDialog = div(modalHeader +++ modalInfo)

  def bodyDialog = div(modalBody)

  def footerDialog = div(modalFooter)

  //modal jQuery events
  private def modalQuery(id: ModalID, query: String) = $("#" + id).modal(query)

  private def hasClass(id: ModalID, clazz: String): Boolean = $("#" + id).hasClass(clazz)

  def showModal(id: ModalID) = modalQuery(id, "show")

  def hideModal(id: ModalID) = modalQuery(id, "hide")

  def isModalVisible(id: ModalID): Boolean = hasClass(id, "in")


  // NAVS
  class NavItem[T <: HTMLElement](val navid: String,
                                  contentDiv: T,
                                  ontrigger: () ⇒ Unit,
                                  val todo: () ⇒ Unit = () ⇒ {},
                                  extraRenderPair: Seq[Modifier] = Seq(),
                                  active: Boolean = false) {
    val activeString = {
      if (active) "active" else ""
    }

    val render = li(role := "presentation", id := navid, `class` := activeString)(tags.a(href := "", onclick := { () ⇒
      ontrigger()
      false
    }
    )(contentDiv))(
      extraRenderPair: _*)

  }

  def dialogStringNavItem(id: String, content: String, ontrigger: () ⇒ Unit = () ⇒ {}, todo: () ⇒ Unit = () ⇒ {}): NavItem[HTMLDivElement] =
    navItem(id, content, ontrigger, todo, Seq(data("toggle") := "modal", data("target") := "#" + id + "PanelID"))

  def navItem(id: String, content: String, ontrigger: () ⇒ Unit = () ⇒ {}, todo: () ⇒ Unit = () ⇒ {}, extraRenderPair: Seq[Modifier] = Seq(), active: Boolean = false) =
    new NavItem(id, div(content).render, ontrigger, todo, extraRenderPair, active)

  def dialogGlyphNavItem(id: String,
                         glyphIcon: Glyphicon,
                         ontrigger: () ⇒ Unit = () ⇒ {},
                         todo: () ⇒ Unit = () ⇒ {}): NavItem[HTMLSpanElement] = dialogNavItem(id, glyphSpan(glyphIcon).render, ontrigger, todo)

  def dialogNavItem[T <: HTMLElement](id: String,
                                      tTag: T,
                                      ontrigger: () ⇒ Unit = () ⇒ {},
                                      todo: () ⇒ Unit = () ⇒ {}): NavItem[T] =
    new NavItem(id, tTag, ontrigger, todo, Seq(data("toggle") := "modal", data("target") := "#" + id + "PanelID"))


  def nav(uuid: String, classPair: ClassAttrPair, contents: NavItem[_ <: HTMLElement]*): TypedTag[HTMLElement] =
    ul(toClass("nav "), classPair, id := uuid, role := "tablist")(
      contents.map { c ⇒
        c.render(scalatags.JsDom.attrs.onclick := { () ⇒
          $("#" + uuid + " .active").removeClass("active")
          $("#mainNavItemID").addClass("active")
          c.todo()
        })
      }: _*)


  ///// -- TO BE REMOVED

  // POUPUS, TOOLTIS, DIALOGS

  implicit class PopableTypedTag(element: TypedTag[org.scalajs.dom.raw.HTMLElement]) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    def popup(innerDiv: TypedTag[org.scalajs.dom.raw.HTMLElement],
              position: PopupPosition = Bottom,
              popupStyle: ModifierSeq = whitePopup,
              arrowStyle: ModifierSeq = noArrow,
              onclose: () => Unit = () => {},
              condition: () => Boolean = () => true) =
      Popup(element.render, innerDiv, ClickPopup, position, popupStyle, arrowStyle, onclose, condition).popup


    def tooltip(innerDiv: TypedTag[org.scalajs.dom.raw.HTMLElement],
                position: PopupPosition = Bottom,
                popupStyle: ModifierSeq = blackPopup,
                arrowStyle: ModifierSeq = blackBottomArrow,
                onclose: () => Unit = () => {},
                condition: () => Boolean = () => true
               ) =
      Popup(element.render, innerDiv, HoverPopup, position, popupStyle, arrowStyle, onclose, condition).popup

    def dialog(innerDiv: TypedTag[org.scalajs.dom.raw.HTMLElement],
               popupStyle: ModifierSeq = dialogStyle,
               onclose: () => Unit = () => {},
               condition: () => Boolean = () => true
              ) =
      Popup(element.render, innerDiv, DialogPopup, Bottom, popupStyle, noArrow, onclose, condition).popup

  }


  //SELECT
  implicit class SelectableSeqWithStyle[T](s: Seq[SelectElement[T]]) {
    def select(default: Option[T],
               naming: T => String,
               key: ModifierSeq = emptyMod,
               onclickExtra: () ⇒ Unit = () ⇒ {}) = Select(s, default, naming, key, onclickExtra)

  }

  implicit class SelectableSeq[T](s: Seq[T]) {
    def select(default: Option[T],
               naming: T => String,
               key: ModifierSeq = emptyMod,
               onclickExtra: () ⇒ Unit = () ⇒ {}) = SelectableSeqWithStyle(s.map { el =>
      SelectElement(el, emptyMod)
    }).select(default, naming, key, onclickExtra)

  }


  // JUMBOTRON
  def jumbotron(modifiers: ModifierSeq) =
    div(container +++ themeShowcase)(role := "main")(
      div(sheet.jumbotron)(
        p(modifiers)
      )
    )


  // SCROLL TEXT AREA
  // Define text area, which scrolling can be automated in function of content change:
  object ScrollableTextArea {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    sealed trait AutoScroll

    //The scroll is always in top position
    object TopScroll extends AutoScroll

    //The scroll is always in bottom position
    object BottomScroll extends AutoScroll

    //The scroll is not set and remains at scrollHeight
    case class NoScroll(scrollHeight: Int) extends AutoScroll

  }


  import ScrollableTextArea._

  // TEXT AREA
  def textArea(nbRow: Int) = tags.textarea(formControl, rows := nbRow)

  def scrollableText(text: String = "", scrollMode: AutoScroll = TopScroll): ScrollableText = ScrollableText(text, scrollMode)

  def scrollableDiv(element: Div = div.render, scrollMode: AutoScroll = BottomScroll): ScrollableDiv = ScrollableDiv(element, scrollMode)

  trait Scrollable {

    def scrollMode: Var[AutoScroll]

    def sRender: HTMLElement

    def view: HTMLElement = div(sRender).render

    def setScrollMode = {
      val scrollHeight = sRender.scrollHeight
      val scrollTop = sRender.scrollTop.toInt
      scrollMode() =
        if ((scrollTop + sRender.offsetHeight.toInt) > scrollHeight) BottomScroll
        else NoScroll(scrollTop)
    }

    def doScroll = scrollMode.now match {
      case BottomScroll ⇒ sRender.scrollTop = sRender.scrollHeight
      case n: NoScroll ⇒ sRender.scrollTop = n.scrollHeight
      case _ ⇒
    }
  }

  case class ScrollableText(initText: String, _scrollMode: AutoScroll) extends Scrollable {
    val scrollMode: Var[AutoScroll] = Var(_scrollMode)
    val tA = textArea(20)(initText, onscroll := { (e: Event) ⇒ setScrollMode })
    val sRender = tA.render

    def setContent(out: String) = {
      sRender.value = out
    }
  }

  case class ScrollableDiv(_element: Div, _scrollMode: AutoScroll)(implicit ctx: Ctx.Owner) extends Scrollable {
    val scrollMode: Var[AutoScroll] = Var(_scrollMode)
    val child: Var[Node] = Var(div)

    val tA = div(height := "100%")(Rx {
      child()
    }, onscroll := { (e: Event) ⇒ setScrollMode })

    def setChild(d: Div) = child() = d

    val sRender = tA.render
  }


  // LABELED FIELD
  case class ElementGroup(e1: TypedTag[HTMLElement], e2: TypedTag[HTMLElement])

  def inLineForm(elements: ElementGroup*) = {
    val ID = uuID
    form(formInline)(
      for {
        e <- elements
      } yield {
        div(formGroup)(
          e.e1(`for` := ID, sheet.marginLeft(5)),
          e.e2(formControl, sheet.marginLeft(5), id := ID)
        )
      }
    )
  }

  def group(e1: TypedTag[HTMLElement], e2: TypedTag[HTMLElement]) = ElementGroup(e1, e2)


  // PANELS
  def panel(heading: String) =
    div(sheet.panel +++ panelDefault)(
      div(panelHeading)(heading),
      div(panelBody)
    )

  def alert(content: String, todook: () ⇒ Unit, todocancel: () ⇒ Unit) = {
    tags.div(role := "alert")(
      content,
      div(sheet.paddingTop(20))(
        buttonGroup(sheet.floatLeft)(
          button("OK", btn_danger, todook),
          button("Cancel", btn_default, todocancel)
        )
      )
    )
  }


  // EXCLUSIVE BUTTON GROUPS
  def exclusiveButtonGroup(style: ModifierSeq, defaultStyle: ModifierSeq = btn_default, selectionStyle: ModifierSeq = btn_default)(buttons: ExclusiveButton*) = new ExclusiveGroup(style, defaultStyle, selectionStyle, buttons)

  def twoStatesGlyphButton(glyph1: ModifierSeq,
                           glyph2: ModifierSeq,
                           todo1: () ⇒ Unit,
                           todo2: () ⇒ Unit,
                           preGlyph: ModifierSeq = Seq()
                          ) = TwoStatesGlyphButton(glyph1, glyph2, todo1, todo2, preGlyph)

  def twoStatesSpan(glyph1: ModifierSeq,
                    glyph2: ModifierSeq,
                    todo1: () ⇒ Unit,
                    todo2: () ⇒ Unit,
                    preString: String,
                    buttonStyle: ModifierSeq = emptyMod
                   ) = TwoStatesSpan(glyph1, glyph2, todo1, todo2, preString, buttonStyle)

  sealed trait ExclusiveButton {
    def action: () ⇒ Unit
  }

  trait ExclusiveGlyphButton extends ExclusiveButton {
    def glyph: Glyphicon
  }

  trait ExclusiveStringButton extends ExclusiveButton {
    def title: String
  }

  trait TwoStates extends ExclusiveButton

  case class TwoStatesGlyphButton(glyph: ModifierSeq,
                                  glyph2: ModifierSeq,
                                  action: () ⇒ Unit,
                                  action2: () ⇒ Unit,
                                  preGlyph: ModifierSeq
                                 ) extends TwoStates {
    val cssglyph = glyph +++ sheet.paddingLeft(3)

    lazy val div = {
      glyphButton("", preGlyph, cssglyph, action)
    }
  }

  case class TwoStatesSpan(glyph: ModifierSeq,
                           glyph2: ModifierSeq,
                           action: () ⇒ Unit,
                           action2: () ⇒ Unit,
                           preString: String,
                           buttonStyle: ModifierSeq = emptyMod
                          ) extends TwoStates {
    val cssglyph = glyph +++ sheet.paddingLeft(3)

    lazy val cssbutton: ModifierSeq = Seq(
      backgroundColor := "transparent",
      sheet.paddingTop(8),
      border := "none",
      width := "auto"
    )

    lazy val div = button(preString, buttonStyle +++ cssbutton +++ pointer, action)(
      span(cssglyph)
    )

  }

  object ExclusiveButton {
    def string(t: String, a: () ⇒ Unit) = new ExclusiveStringButton {
      def title = t

      def action = a
    }

    def glyph(g: Glyphicon, a: () ⇒ Unit) = new ExclusiveGlyphButton {
      def glyph = g

      def action = a
    }

    def twoGlyphButtonStates(
                              glyph1: ModifierSeq,
                              glyph2: ModifierSeq,
                              todo1: () ⇒ Unit,
                              todo2: () ⇒ Unit,
                              preGlyph: ModifierSeq
                            ) = twoStatesGlyphButton(glyph1, glyph2, todo1, todo2, preGlyph)

    def twoGlyphSpan(
                      glyph1: ModifierSeq,
                      glyph2: ModifierSeq,
                      todo1: () ⇒ Unit,
                      todo2: () ⇒ Unit,
                      preString: String,
                      buttonStyle: ModifierSeq = emptyMod
                    ) = twoStatesSpan(glyph1, glyph2, todo1, todo2, preString, buttonStyle)
  }

  class ExclusiveGroup(style: ModifierSeq,
                       defaultStyle: ModifierSeq,
                       selectionStyle: ModifierSeq,
                       buttons: Seq[ExclusiveButton]) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    val selected = Var(buttons.head)
    val selectedAgain = Var(false)

    def buttonBackground(b: ExclusiveButton) = (if (b == selected.now) btn +++ selectionStyle else btn +++ defaultStyle)

    def glyphButtonBackground(b: ExclusiveButton) = buttonBackground(b) +++ twoGlyphButton

    def stringButtonBackground(b: ExclusiveButton) = buttonBackground(b) +++ stringButton

    def glyphForTwoStates(ts: TwoStates, mod: ModifierSeq) = (ts == selected.now, mod, emptyMod)

    val div: Modifier = Rx {
      selected()
      tags.div(style +++ btnGroup)(
        for (b ← buttons) yield {
          b match {
            case s: ExclusiveStringButton ⇒ button(s.title, stringButtonBackground(s) +++ stringInGroup, action(b, s.action))
            case g: ExclusiveGlyphButton ⇒ glyphButton("", glyphButtonBackground(g), g.glyph, action(b, g.action))
            case ts: TwoStatesGlyphButton ⇒
              if (selectedAgain()) twoStatesGlyphButton(glyphForTwoStates(ts, ts.glyph2), ts.glyph, action(ts, ts.action2), action(ts, ts.action), glyphButtonBackground(ts) +++ ts.preGlyph).div
              else twoStatesGlyphButton(glyphForTwoStates(ts, ts.glyph), ts.glyph2, action(ts, ts.action), action(ts, ts.action2), glyphButtonBackground(ts) +++ ts.preGlyph).div
            case ts: TwoStatesSpan ⇒
              if (selectedAgain()) twoStatesSpan(glyphForTwoStates(ts, ts.glyph2), ts.glyph, action(ts, ts.action2), action(ts, ts.action), ts.preString, glyphButtonBackground(ts)).div
              else twoStatesSpan(glyphForTwoStates(ts, ts.glyph), ts.glyph2, action(ts, ts.action), action(ts, ts.action2), ts.preString, glyphButtonBackground(ts)).div
          }
        }
      )
    }

    private def action(b: ExclusiveButton, a: () ⇒ Unit) = () ⇒ {
      selectedAgain() = if (b == selected.now) !selectedAgain.now else false
      selected() = b
      a()
    }


    def reset = selected() = buttons.head
  }


  // FORMS
  def labeledInput(title: String,
                   default: String = "",
                   pHolder: String = "",
                   labelStyle: ModifierSeq = emptyMod,
                   inputStyle: ModifierSeq = emptyMod) =
    new LabeledInput(title, default, pHolder, labelStyle, inputStyle)

}