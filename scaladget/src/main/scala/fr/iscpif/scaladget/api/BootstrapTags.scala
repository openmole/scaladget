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

import java.util.UUID

import org.scalajs.dom.html.{Div, Input}
import org.scalajs.dom.raw._

import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.{TypedTag, tags}
import scalatags.JsDom.all._
import fr.iscpif.scaladget.tools.JsRxTags._
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import sheet.{ctx => _, _}
import rx._
import Popup._
import Dropdown._
import fr.iscpif.scaladget.api.Alert.ExtraButton
import fr.iscpif.scaladget.api.SelectableButtons.{CheckBoxSelection, RadioSelection}

import scalatags.JsDom

@JSExport("BootstrapTags")
object BootstrapTags {
  bstags =>

  def withBootstrapNative[T <: HTMLElement](f: => T) = {
    org.scalajs.dom.document.body.appendChild(f)
    org.scalajs.dom.document.body.appendChild(
      tags.script(`type` := "text/javascript", src := "js/bootstrap-native1.0.5.min.js"))
  }

  implicit def formTagToNode(tt: HtmlTag): org.scalajs.dom.Node = tt.render


  def uuID: String = java.util.UUID.randomUUID.toString

  type Input = ConcreteHtmlTag[org.scalajs.dom.raw.HTMLInputElement]

  def input(content: String = "") = tags.input(formControl, scalatags.JsDom.all.value := content)

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

  def checkboxes(modifierSeq: ModifierSeq = emptyMod)(checkBoxes: SelectableButton*): SelectableButtons =
    new SelectableButtons(modifierSeq, CheckBoxSelection, checkBoxes)

  def radios(modifierSeq: ModifierSeq = emptyMod)(radioButtons: SelectableButton*): SelectableButtons = {

    val allActive = radioButtons.toSeq.filter {
      _.active.now
    }.size

    val buttons = {
      if (radioButtons.size > 0) {
        if (allActive != 1) radioButtons.head.copy(defaultActive = true) +: radioButtons.tail.map {
          _.copy(defaultActive = false)
        }
        else radioButtons
      } else radioButtons
    }

    new SelectableButtons(modifierSeq, RadioSelection, buttons)
  }

  def selectableButton(text: String, defaultActive: Boolean = false, modifierSeq: ModifierSeq = btn_default, onclick: () => Unit = () => {}) =
    SelectableButton(text, defaultActive, modifierSeq, onclick)

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
  def button(text: String = "", buttonStyle: ModifierSeq = btn_default, glyphicon: ModifierSeq = Seq(), todo: () ⇒ Unit = () => {}): TypedTag[HTMLButtonElement] = {
    val iconStyle = if (text.isEmpty) paddingAll(top = 3, bottom = 3) else sheet.marginLeft(5)
    tags.button(text, btn +++ buttonStyle, `type` := "button", onclick := { () ⇒ todo() })(span(glyphicon +++ iconStyle))
  }

  // Clickable span containing a glyphicon and a text
  def glyphSpan(glyphicon: ModifierSeq, onclickAction: () ⇒ Unit = () ⇒ {}, text: String = ""): TypedTag[HTMLSpanElement] =
  span(glyphicon +++ pointer, aria.hidden := "true", onclick := { () ⇒ onclickAction() })(text)


  // Close buttons
  def closeButton(dataDismiss: String, todo: () => Unit = () => {}) = button("", todo)(toClass("close"), aria.label := "Close", data.dismiss := dataDismiss)(
    span(aria.hidden := true)(raw("&#215"))
  )

  //Label decorators to set the label size
  implicit class TypedTagLabel(lab: TypedTag[HTMLLabelElement]) {
    def size1(modifierSeq: ModifierSeq = emptyMod) = h1(modifierSeq)(lab)

    def size2(modifierSeq: ModifierSeq = emptyMod) = h2(modifierSeq)(lab)

    def size3(modifierSeq: ModifierSeq = emptyMod) = h3(modifierSeq)(lab)

    def size4(modifierSeq: ModifierSeq = emptyMod) = h4(modifierSeq)(lab)

    def size5(modifierSeq: ModifierSeq = emptyMod) = h5(modifierSeq)(lab)

    def size6(modifierSeq: ModifierSeq = emptyMod) = h6(modifierSeq)(lab)
  }


  // PROGRESS BAR
  def progressBar(barMessage: String, ratio: Int): TypedTag[HTMLDivElement] =
  div(progress)(
    div(sheet.progressBar)(width := ratio.toString() + "%")(
      barMessage
    )
  )


  // BADGE
  def badge(content: String, badgeValue: String, buttonStyle: ModifierSeq = emptyMod, todo: () => Unit = () => {}) =
  button(s"$content ", buttonStyle, todo)(span(toClass("badge"))(badgeValue))


  //BUTTON GROUP
  def buttonGroup(mod: ModifierSeq = emptyMod) = div(mod +++ btnGroup)

  def buttonToolBar = div(btnToolbar)(role := "toolbar")

  //MODAL
  type ModalID = String


  object ModalDialog {
    def apply() = new ModalDialog

    type HeaderDialog = TypedTag[_]
    type BodyDialog = TypedTag[_]
    type FooterDialog = TypedTag[_]

    val headerDialogShell = div(modalHeader +++ modalInfo)

    val bodyDialogShell = div(modalBody)

    val footerDialogShell = div(modalFooter)

    def actAndCloseButton(modalDialog: ModalDialog, modifierSeq: ModifierSeq, content: String, action: () => Unit = () => {}) = {
      tags.button(modifierSeq, content, onclick := { () =>
        action()
        modalDialog.close
      })
    }

    def closeButton(modalDialog: ModalDialog, modifierSeq: ModifierSeq, content: String) =
      actAndCloseButton(modalDialog, modifierSeq, content)
  }

  class ModalDialog {

    val headerDialog: Var[ModalDialog.HeaderDialog] = Var(tags.div)
    val bodyDialog: Var[ModalDialog.BodyDialog] = Var(tags.div)
    val footerDialog: Var[ModalDialog.FooterDialog] = Var(tags.div)

    val ID = uuID

    lazy val dialog = {
      val d = div(modal +++ fade)(id := ID, `class` := "modal fade",
        tabindex := "-1", role := "dialog", aria.labelledby := "myModalLabel", aria.hidden := "true")(
        div(sheet.modalDialog)(
          div(modalContent)(
            headerDialog.now,
            bodyDialog.now,
            footerDialog.now
          )
        )
      ).render

      org.scalajs.dom.document.body.appendChild(d)
      d
    }

    lazy val modalMapping = new fr.iscpif.scaladget.mapping.bootstrap.Modal(dialog)

    def header(hDialog: ModalDialog.HeaderDialog): Unit = headerDialog() = hDialog

    def body(bDialog: ModalDialog.BodyDialog): Unit = bodyDialog() = bDialog

    def footer(fDialog: ModalDialog.FooterDialog): Unit = footerDialog() = fDialog

    def trigger[T <: HTMLElement](element: TypedTag[T]) =
      element(id := "custom-modal-template", data("toggle") := "modal", data("target") := s"#$ID")

    def triggerButton(content: String, modifierSeq: ModifierSeq) =
      trigger(tags.button(modifierSeq)(content))


    def open = modalMapping.open

    def close = modalMapping.close

    def isVisible = dialog.className.contains(" in")
  }


  // NAVS
  class NavItem[T <: Modifier](contentDiv: T,
                               val todo: () ⇒ Unit = () ⇒ {},
                               extraRenderPair: Seq[Modifier] = Seq(),
                               activeDefault: Boolean = false) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    val active: Var[Boolean] = Var(activeDefault)

    val render = li(
      tags.a(href := "#",
        lineHeight := "35px",
        onclick := { () =>
          todo()
          false
        })(
        contentDiv,
        Rx {
          if (active()) span(toClass("sr-only"))("(current)")
          else span()
        }),
      `class` := Rx {
        if (active()) "active" else ""
      }
    )(extraRenderPair: _*)
  }

  def navItem[T <: Modifier](content: T,
                             todo: () => Unit = () => {},
                             extraRenderPair: Seq[Modifier] = Seq(),
                             activeDefault: Boolean = false) = {
    new NavItem(content, todo, extraRenderPair, activeDefault)
  }

  def stringNavItem(content: String, todo: () ⇒ Unit = () ⇒ {}, activeDefault: Boolean = false): NavItem[Modifier] =
    navItem(content, todo, activeDefault = activeDefault)

  def navBar(classPair: ModifierSeq, contents: NavItem[_ <: Modifier]*): TypedTag[HTMLElement] =

    JsDom.tags2.nav(navbar +++ navbar_default +++ classPair)(
      div(toClass("container-fluid"))(
        div(toClass("collapse") +++ navbar_collapse)(
          ul(nav +++ navbar_nav)(
            contents.map { c ⇒
              c.render(scalatags.JsDom.attrs.onclick := { () ⇒
                contents.foreach {
                  _.active() = false
                }
                c.active() = true
                c.todo()
              })
            }: _*)
        )
      )
    )


  // Nav pills
  case class NavPill(name: String, badge: Option[Int], todo: () => Unit)

  // def navPills()

  // POUPUS, TOOLTIPS
  implicit class PopableTypedTag(element: TypedTag[org.scalajs.dom.raw.HTMLElement]) {

    def tooltip(text: String,
                position: PopupPosition = Bottom) = {
      element(
        data("placement") := position.value,
        data("toggle") := "tooltip",
        data("original-title") := text
      )
    }

    def popover(text: String,
                position: PopupPosition = Bottom,
                trigger: PopupType = HoverPopup,
                title: Option[String] = None,
                dismissable: Boolean = false
               ) =
      element(
        data("placement") := position.value,
        data("toggle") := "popover",
        data("content") := text,
        trigger match {
          case ClickPopup => Seq(id := "popover-via-click", data("trigger") := "click")
          case _ =>
        },
        title match {
          case Some(t: String) => Seq(data("title") := t, data("dissmisibale") := "true")
          case _ =>
        },
        dismissable match {
          case true => data("dismiss") := true
          case _ =>
        }
      )
  }


  //DROPDOWN
  implicit class SelectableSeqWithStyle[T](s: Seq[OptionElement[T]]) {
    def dropdown(defaultIndex: Int = 0,
                 key: ModifierSeq = emptyMod,
                 onclickExtra: () ⇒ Unit = () ⇒ {}) = Dropdown(s, defaultIndex, key, onclickExtra)

  }

  implicit class SelectableTypedTag[T <: HTMLElement](tt: TypedTag[T]) {
    def dropdown(triggerButtonText: String,
                 buttonModifierSeq: ModifierSeq) = Dropdown(tt, triggerButtonText, buttonModifierSeq)
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


  // ALERTS
  def successAlert(title: String, content: String, triggerCondition: Rx.Dynamic[() => Boolean] = Rx(() => true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
  new Alert(alert_success, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def infoAlert(title: String, content: String, triggerCondition: Rx.Dynamic[() => Boolean] = Rx(() => true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_info, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def warningAlert(title: String, content: String, triggerCondition: Rx.Dynamic[() => Boolean] = Rx(() => true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_warning, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def dangerAlert(title: String, content: String, triggerCondition: Rx.Dynamic[() => Boolean] = Rx(() => true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_danger, title, content, triggerCondition, todocancel)(otherButtons: _*).render


  implicit class TagCollapserOnClick[S <: TypedTag[HTMLElement]](triggerTag: S) {
    def expandOnclick[T <: TypedTag[HTMLElement]](inner: T) = {
      val collapser = new Collapser[T](inner)
      val triggerTagRender = triggerTag.render

      triggerTagRender.onclick = (e: MouseEvent) => {
        collapser.switch
      }

      div(
        triggerTagRender,
        collapser.tag
      )
    }
  }

  implicit class TagCollapserOnCondition(triggerCondition: Rx.Dynamic[() => Boolean]) {
    def expand[T <: TypedTag[HTMLElement]](inner: T) = {
      val collapser = new Collapser[T](inner, triggerCondition.now())
      Rx {
        if (triggerCondition().apply) {
          collapser.switch
        }
      }
      collapser.tag
    }
  }

  // COLLAPSERS
  class Collapser[T <: TypedTag[HTMLElement]](innerTag: T, initExpand: Boolean = false) {
    val expanded = Var(initExpand)

    private val innerTagRender = innerTag.render

    val tag = div(collapseTransition +++ sheet.paddingTop(10))(innerTagRender).render

    def switch = {
      expanded() = !expanded.now
      tag.style.height = {
        if (expanded.now) (innerTagRender.clientHeight + 15).toString
        else "0"
      }
    }

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
      button("", preGlyph, cssglyph, action)
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
            case g: ExclusiveGlyphButton ⇒ button("", glyphButtonBackground(g), g.glyph, action(b, g.action))
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

  trait FormTag[+T <: HTMLElement] {
    def tag: T
  }

  trait LabeledFormTag[T <: HTMLElement] extends FormTag[T] {
    def label: TypedTag[HTMLLabelElement]
  }

  /*
  implicit def modifierToFormTag(m: Modifier): FormTag = new FormTag {
    val tag: T = m
  }*/
  implicit def htmlElementToFormTag[T <: HTMLElement](t: T): FormTag[T] = new FormTag[T] {
    val tag: T = t
  }


  implicit class LabelForModifiers[T <: HTMLElement](m: T) {
    def withLabel(title: String, labelStyle: ModifierSeq = emptyMod): LabeledFormTag[T] = new LabeledFormTag[T] {
      val label: TypedTag[HTMLLabelElement] = tags.label(title)(labelStyle +++ (sheet.paddingRight(5)))

      val tag: T = m
    }
  }

  private def insideForm[T <: HTMLElement](formTags: FormTag[T]*) =
    for {
      ft <- formTags
    } yield {
      div(formGroup +++ sheet.paddingRight(5))(
        ft match {
          case lft: LabeledFormTag[T] => lft.label
          case _ =>
        },
        ft.tag)
    }


  def vForm[T <: HTMLElement](formTags: FormTag[T]*): TypedTag[HTMLDivElement] = vForm(emptyMod)(formTags.toSeq: _*)

  def vForm[T <: HTMLElement](modifierSeq: ModifierSeq)(formTags: FormTag[T]*): TypedTag[HTMLDivElement] =
    div(modifierSeq +++ formVertical)(insideForm(formTags: _*))


  def hForm[T <: HTMLElement](formTags: FormTag[T]*): TypedTag[HTMLFormElement] = hForm(emptyMod)(formTags.toSeq: _*)

  def hForm[T <: HTMLElement](modifierSeq: ModifierSeq)(formTags: FormTag[T]*): TypedTag[HTMLFormElement] = {
    form(formInline +++ modifierSeq)(insideForm(formTags: _*))
  }

  //ACCORDION

  case class AccordionItem[T <: HTMLElement](title: String, content: TypedTag[T])

  def accordionItem[T <: HTMLElement](title: String, content: TypedTag[T]) = AccordionItem(title, content)

  def accordion[T <: HTMLElement](accordionItems: AccordionItem[T]*): TypedTag[HTMLDivElement] =
    accordion(emptyMod)(emptyMod)(accordionItems.toSeq: _*)

  def accordion[T <: HTMLElement](modifierSeq: ModifierSeq)(titleModifierSeq: ModifierSeq)(accordionItems: AccordionItem[T]*): TypedTag[HTMLDivElement] = {
    val accordionID = uuID
    div(
      modifierSeq,
      id := accordionID,
      role := "tablist",
      aria.multiselectable := "true",
      ms("panel-group"))(
      for {
        item <- accordionItems.toSeq
      } yield {
        val collapseID = uuID
        div(sheet.panel +++ sheet.panelDefault)(
          div(
            panelHeading,
            role := "tab"
          )(a(
            data("toggle") := "collapse",
            data("parent") := s"#$accordionID",
            href := collapseID,
            aria.expanded := true,
            aria.controls := collapseID,
            ms("collapsed"),
            display := "block",
            width := "100%",
            height := 25,
            titleModifierSeq
          )(item.title)),
          div(
            id := collapseID,
            ms("panel-collapse collapse"),
            role := "tabpanel",
            aria.expanded := false
          )(div(
            panelBody,
            item.content))
        )
      }
    )
  }

}