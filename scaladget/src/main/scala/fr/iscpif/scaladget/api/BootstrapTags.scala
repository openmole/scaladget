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
import org.querki.jquery._
import fr.iscpif.scaladget.stylesheet.{all=> sheet}
import fr.iscpif.scaladget.bootstrap._
import sheet._
import rx._

@JSExport("BootstrapTags")
object BootstrapTags {
  bstags =>

  implicit def formTagToNode(tt: HtmlTag): org.scalajs.dom.Node = tt.render

  /*implicit class BootstrapTypedTag[+Output <: raw.Element](t: TypedTag[Output]) {
    def +++(m: Seq[Modifier]) = t.copy(modifiers = t.modifiers :+ m.toSeq)
  }*/

  def uuID: String = java.util.UUID.randomUUID.toString

  // INPUT
  def input(content: String) = tags.input(formControl, value := content)

  def inputGroup(modifierSeq: ModifierSeq) = div(modifierSeq +++ sheet.inputGroup)

  def inputGroupButton = span("input-group-btn")

  def inputGroupAddon = span("input-group-addon")

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


  // LABEL
  def label(content: String, label: ModifierSeq): TypedTag[HTMLSpanElement] = span(label)(content)


  // SELECT (to be used with button class aggregators )
  def select(id: String, contents: Seq[(String, String)], buttonStyle: ButtonStyle) = buttonGroup()(
    tags.a(buttonStyle +++ dropdownToggle, data("toggle") := "dropdown", href := "#")(
      "Select", span("caret")),
    ul(dropdownMenu)(
      for (c ← contents) yield {
        tags.li(tags.a(
          href := "#")(c._2)
        )
      }
    )
  )


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
    button(text, buttonStyle, todo)(cursor := "pointer", `type` := "button")(span(glyphicon))

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
    button(s"$content ", buttonStyle, todo)(span("badge")(badgeValue))


  //BUTTON GROUP
  def buttonGroup(mod: ModifierSeq = Seq()) = div(mod +++ btnGroup)

  def buttonToolBar = div(btnToolbar)(role := "toolbar")


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
    new NavItem(id, tags.div(content).render, ontrigger, todo, extraRenderPair, active)

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

  def scrollableDiv(element: Div = tags.div.render, scrollMode: AutoScroll = BottomScroll): ScrollableDiv = ScrollableDiv(element, scrollMode)

  trait Scrollable {

    def scrollMode: Var[AutoScroll]

    def sRender: HTMLElement

    def view: HTMLElement = tags.div(sRender).render

    def setScrollMode = {
      val scrollHeight = sRender.scrollHeight
      val scrollTop = sRender.scrollTop.toInt
      scrollMode() =
        if ((scrollTop + sRender.offsetHeight.toInt) > scrollHeight) BottomScroll
        else NoScroll(scrollTop)
    }

    def doScroll = scrollMode() match {
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

  case class ScrollableDiv(_element: Div, _scrollMode: AutoScroll) extends Scrollable {
    val scrollMode: Var[AutoScroll] = Var(_scrollMode)
    val child: Var[Node] = Var(tags.div)
    val tA = div(height := "100%")(Rx {
      child()
    }, onscroll := { (e: Event) ⇒ setScrollMode })

    def setChild(d: Div) = child() = d

    val sRender = tA.render

  }

  //table
  // !! add vert-align for td tags


  //Input group


  // LABELED FIELD
  def labeledField(labelString: String, element: HTMLElement) = {
    val ID = uuID
    form(formHorizontal)(
      div(controlGroup)(
        label(labelString, controlLabel)(`for` := ID),
        div(controls)(
          tags.div(id := ID)(element)
        )
      )
    )
  }


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
  def exclusiveButtonGroup(style: ModifierSeq, selectionStyle: ModifierSeq)(buttons: ExclusiveButton*) = new ExclusiveGroup(style, selectionStyle, buttons)

  def twoStatesGlyphButton(
                            glyph1: ModifierSeq,
                            glyph2: ModifierSeq,
                            todo1: () ⇒ Unit,
                            todo2: () ⇒ Unit,
                            preString: String = "",
                            preGlyph: ModifierSeq = Seq()
                          ) = new TwoStatesGlyphButton(glyph1, glyph2, todo1, todo2, preString, preGlyph)


  sealed trait ExclusiveButton {
    def action: () ⇒ Unit
  }

  trait ExclusiveGlyphButton extends ExclusiveButton {
    def glyph: Glyphicon
  }

  trait ExclusiveStringButton extends ExclusiveButton {
    def title: String
  }

  case class TwoStatesGlyphButton(glyph: ModifierSeq,
                                  glyph2: ModifierSeq,
                                  action: () ⇒ Unit,
                                  action2: () ⇒ Unit,
                                  preString: String,
                                  preGlyph: ModifierSeq
                                 ) extends ExclusiveButton {
    val selected = Var(glyph)

    val div: Modifier = Rx {
      glyphButton(preString, preGlyph, selected(), () ⇒ {
        if (selected() == glyph) {
          selected() = glyph2
          action2()
        }
        else {
          selected() = glyph
          action()
        }
      })
    }
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

    def twoGlyphStates(
                        glyph1: ModifierSeq,
                        glyph2: ModifierSeq,
                        todo1: () ⇒ Unit,
                        todo2: () ⇒ Unit,
                        preString: String = "",
                        preGlyph: ModifierSeq = Seq()
                      ) = twoStatesGlyphButton(glyph1, glyph2, todo1, todo2, preString, preGlyph)
  }

  class ExclusiveGroup(style: ModifierSeq, selectionStyle: ModifierSeq, buttons: Seq[ExclusiveButton]) {
    val selected = Var(buttons.head)

    def buttonBackground(b: ExclusiveButton) = {
      val base: ModifierSeq = (if (b == selected()) selectionStyle else btn_default)
      base +++ twoGlyphButton
    }


    val div: Modifier = Rx {
      tags.div(style, btnGroup)(
        for (b ← buttons) yield {
          b match {
            case s: ExclusiveStringButton ⇒ button(s.title, buttonBackground(s) +++ stringInGroup, action(b, s.action))
            case g: ExclusiveGlyphButton ⇒ glyphButton("", buttonBackground(g), g.glyph, action(b, g.action))
            case ts: TwoStatesGlyphButton ⇒ twoStatesGlyphButton(ts.glyph, ts.glyph2, action(ts, ts.action), action(ts, ts.action2), ts.preString, buttonBackground(ts) +++ ts.preGlyph).div
          }
        }
      )
    }

    private def action(b: ExclusiveButton, a: () ⇒ Unit) = () ⇒ {
      if (selected() != b) selected() = b
      a()
    }


    def reset = selected() = buttons.head
  }

}