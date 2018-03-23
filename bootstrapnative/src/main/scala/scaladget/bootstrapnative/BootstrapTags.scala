package scaladget.bootstrapnative

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

import scalatags.JsDom.{TypedTag, tags}
import scalatags.JsDom.all._
import rx._

import Popup._
import scala.scalajs.js
import scalatags.JsDom
import scaladget.bootstrapnative
import scaladget.bootstrapnative.Alert.ExtraButton
import scaladget.bootstrapnative.SelectableButtons._
import bsnsheet._
import scaladget.tools._

object BootstrapTags extends BootstrapTags

trait BootstrapTags {
  bstags =>

  implicit def formTagToNode(tt: HtmlTag): org.scalajs.dom.Node = tt.render

  type BS = TypedTag[_ <: HTMLElement]

  type ID = String

  implicit class ShortID(id: ID) {
    def short: String = id.split('-').head

    def short(prefix: String): String = s"$prefix$short"
  }

  def uuID: ID = java.util.UUID.randomUUID.toString

  type Input = ConcreteHtmlTag[org.scalajs.dom.raw.HTMLInputElement]

  def inputTag(content: String = "") = tags.input(bsnsheet.formControl, scalatags.JsDom.all.value := content)

  def inputGroup(modifierSeq: ModifierSeq = emptyMod) = div(modifierSeq +++ bsnsheet.inputGroup)

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

  // displaying a text with a button style and a glyphicon
  def buttonIcon(text: String = "", buttonStyle: ModifierSeq = btn_default, glyphicon: ModifierSeq = Seq(), todo: () ⇒ Unit = () => {}): TypedTag[HTMLButtonElement] = {
    val iconStyle = if (text.isEmpty) Seq(paddingTop := 3, paddingBottom := 3).toMS else (marginLeft := 5).toMS
    tags.button(btn +++ buttonStyle, `type` := "button", onclick := { () ⇒ todo() })(
      span(
        span(glyphicon +++ iconStyle),
        span(s" $text")
      )
    )
  }

  def linkButton(content: String, link: String, buttonStyle: ModifierSeq = btn_default, openInOtherTab: Boolean = true) =
    a(buttonStyle, href := link, role := "button", target := {
      if (openInOtherTab) "_blank" else ""
    })(content)

  // Clickable span containing a glyphicon and a text
  def glyphSpan(glyphicon: ModifierSeq, onclickAction: () ⇒ Unit = () ⇒ {}, text: String = ""): TypedTag[HTMLSpanElement] =
    span(glyphicon +++ pointer, aria.hidden := "true", onclick := { () ⇒ onclickAction() })(text)


  // Close buttons
  def closeButton(dataDismiss: String, todo: () => Unit = () => {}) = button("", onclick := todo)(toClass("close"), aria.label := "Close", data.dismiss := dataDismiss)(
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
      div(bsnsheet.progressBar)(width := ratio.toString() + "%")(
        barMessage
      )
    )


  // BADGE
  def badge(badgeValue: String, badgeStyle: ModifierSeq = emptyMod) = span(toClass("badge") +++ badgeStyle +++ (marginLeft := 4).toMS)(badgeValue)

  //BUTTON GROUP
  def buttonGroup(mod: ModifierSeq = emptyMod) = div(mod +++ btnGroup)

  def buttonToolBar = div(btnToolbar)(role := "toolbar")

  //MODAL
  type ModalID = String


  object ModalDialog {
    def apply(modifierSeq: ModifierSeq = emptyMod,
              onopen: () => Unit = () => {},
              onclose: () => Unit = () => {}) = new ModalDialog(modifierSeq, onopen, onclose)

    val headerDialogShell = div(modalHeader +++ modalInfo)

    val bodyDialogShell = div(modalBody)

    val footerDialogShell = div(modalFooter)

    def closeButton(modalDialog: ModalDialog, modifierSeq: ModifierSeq, content: String) =
      tags.button(modifierSeq, content, onclick := { () =>
        modalDialog.hide
      })
  }

  class ModalDialog(modifierSeq: ModifierSeq, onopen: () => Unit, onclose: () => Unit) {

    val headerDialog: Var[TypedTag[_]] = Var(tags.div)
    val bodyDialog: Var[TypedTag[_]] = Var(tags.div)
    val footerDialog: Var[TypedTag[_]] = Var(tags.div)

    lazy val dialog = {
      val d = div(modal +++ fade)(`class` := "modal fade",
        tabindex := "-1", role := "dialog", aria.hidden := "true")(
        div(bsn.modalDialog +++ modifierSeq)(
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

    lazy val modalMapping = new Modal(dialog)

    def header(hDialog: TypedTag[_]): Unit = headerDialog() = ModalDialog.headerDialogShell(hDialog)

    def body(bDialog: TypedTag[_]): Unit = bodyDialog() = ModalDialog.bodyDialogShell(bDialog)

    def footer(fDialog: TypedTag[_]): Unit = footerDialog() = ModalDialog.footerDialogShell(fDialog)

    def show = {
      modalMapping.show
      onopen()
    }

    def hide = {
      modalMapping.hide
      onclose()
    }

    def isVisible = dialog.className.contains(" in")
  }


  // NAVS
  case class NavItem[T <: HTMLElement](contentDiv: T,
                                       val todo: () ⇒ Unit = () ⇒ {},
                                       extraRenderPair: Seq[Modifier] = Seq(),
                                       activeDefault: Boolean = false,
                                       toRight: Boolean = false) {

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

    def right = copy(toRight = true)
  }

  def navItem[T <: HTMLElement](content: T,
                                todo: () => Unit = () => {},
                                extraRenderPair: Seq[Modifier] = Seq(),
                                activeDefault: Boolean = false) = {
    new NavItem(content, todo, extraRenderPair, activeDefault)
  }

  def stringNavItem(content: String, todo: () ⇒ Unit = () ⇒ {}, activeDefault: Boolean = false): NavItem[HTMLElement] =
    navItem(span(content).render, todo, activeDefault = activeDefault)

  def navBar(classPair: ModifierSeq, contents: NavItem[_ <: HTMLElement]*) = new NavBar(classPair, None, contents)

  case class NavBarBrand(src: String, modifierSeq: ModifierSeq, todo: () => Unit, alt: String)

  case class NavBar(classPair: ModifierSeq, brand: Option[NavBarBrand], contents: Seq[NavItem[_ <: HTMLElement]]) {

    val navId = uuID.short("n")

    def render: TypedTag[HTMLElement] = {

      val sortedContents = contents.partition {
        _.toRight
      }

      def buildUL(cts: Seq[NavItem[_ <: HTMLElement]], modifier: ModifierSeq = emptyMod) =
        ul(nav +++ navbar_nav +++ modifier)(
          cts.map { c ⇒
            c.render(scalatags.JsDom.attrs.onclick := { () ⇒
              contents.foreach {
                _.active() = false
              }
              c.active() = true
            })
          }: _*)

      val content = div(toClass("navbar-collapse collapse"), aria.expanded := false, id := navId)(
        buildUL(sortedContents._2),
        buildUL(sortedContents._1, navbar_right)
      )

      JsDom.tags2.nav(navbar +++ navbar_default +++ classPair)(
        div(toClass("container-fluid"))(
          for {
            b <- brand
          } yield {
            div(navbar_header)(
              button(`type` := "button", `class` := "navbar-toggle", data("toggle") := "collapse", data("target") := s"#$navId")(
                span(toClass("icon-bar")),
                span(toClass("icon-bar")),
                span(toClass("icon-bar"))
              ),
              a(navbar_brand, href := "#", padding := 0)(
                img(b.modifierSeq +++ pointer, alt := b.alt, src := b.src, onclick := {
                  () => b.todo()
                })
              )
            )
          }, content
        )
      )
    }

    def withBrand(src: String, modifierSeq: ModifierSeq = emptyMod, todo: () => Unit = () => {}, alt: String = "") = copy(brand = Some(NavBarBrand(src, modifierSeq, todo, alt)))

  }

  // Nav pills
  case class NavPill(name: String, badge: Option[Int], todo: () => Unit)


  type TypedContent = String

  object Popover {
    val current: Var[Option[Popover]] = Var(None)

    def show(popover: Popover): Unit = {
      current() = Some(popover)
      popover.show
    }

    def hide: Unit = {
      current.now.foreach { c =>
        c.hide
      }
      current() = None
    }

    def toggle(popover: Popover): Unit = {
      current.now match {
        case None => show(popover)
        case _ => hide
      }
    }
  }

  // POUPUS, TOOLTIPS
  class Popover(element: TypedTag[org.scalajs.dom.raw.HTMLElement],
                innerElement: TypedContent,
                position: PopupPosition = Bottom,
                trigger: PopupType = HoverPopup,
                title: Option[TypedContent] = None,
                dismissible: Boolean = false) {

    lazy val render = element(
        data("toggle") := "popover",
        data("content") := innerElement,
        data("placement") := position.value,
        data("trigger") := {
          trigger match {
            case ClickPopup => "focus"
            case Manual => "manual"
            case _ => "hover"
          }
        },
        title match {
          case Some(t: TypedContent) => data("title") := t
          case _ =>
        },
        data("dismissible") := {
          dismissible match {
            case true => "true"
            case _ => "false"
          }
        }
      ).render

    lazy val popover: bootstrapnative.Popover =
      new bootstrapnative.Popover(render)

    def show = popover.show

    def hide = popover.hide

    def toggle = popover.toggle

  }

  object Tooltip {
    def cleanAll = {
      val list = org.scalajs.dom.document.getElementsByClassName("tooltip")
      for (nodeIndex ← 0 to (list.length - 1)) {
        val element = list(nodeIndex)
        if (!js.isUndefined(element)) element.parentNode.removeChild(element)
      }
    }
  }

  class Tooltip(element: TypedTag[org.scalajs.dom.raw.HTMLElement],
                text: String,
                position: PopupPosition = Bottom,
                condition: () => Boolean = () => true) {

    val elementRender = {
      if (condition())
        element(
          data("placement") := position.value,
          data("toggle") := "tooltip",
          data("original-title") := text
        )
      else element
    }.render

    elementRender.onmouseover = (e: Event) => {
      tooltip
    }

    lazy val tooltip = new bootstrapnative.Tooltip(elementRender)

    def render = elementRender

    def hide = tooltip.hide
  }


  implicit def TypedTagToTypedContent(tc: TypedTag[_]): TypedContent = tc.toString

  implicit class PopableTypedTag(element: TypedTag[org.scalajs.dom.raw.HTMLElement]) {

    def tooltip(text: String,
                position: PopupPosition = Bottom,
                condition: () => Boolean = () => true) = {
      new Tooltip(element, text, position, condition).render
    }

    def popover(text: TypedContent,
                position: PopupPosition = Bottom,
                trigger: PopupType = HoverPopup,
                title: Option[TypedContent] = None,
                dismissible: Boolean = false
               ) = {
      new Popover(element, text, position, trigger, title, dismissible)
    }
  }


  //DROPDOWN
  implicit class SelectableSeqWithStyle[T](s: Seq[T]) {
    def options(defaultIndex: Int = 0,
                key: ModifierSeq = emptyMod,
                naming: T => String,
                onclose: () => Unit = () => {},
                onclickExtra: () ⇒ Unit = () ⇒ {},
                decorations: Map[T, ModifierSeq] = Map(),
                fixedTitle: Option[String] = None) = Selector.options(s, defaultIndex, key, naming, onclose, onclickExtra, decorations, fixedTitle)

  }

  implicit class SelectableTypedTag[T <: HTMLElement](tt: TypedTag[T]) {


    def dropdown(buttonText: String = "",
                 buttonModifierSeq: ModifierSeq = emptyMod,
                 buttonIcon: ModifierSeq = emptyMod,
                 allModifierSeq: ModifierSeq = emptyMod,
                 dropdownModifierSeq: ModifierSeq = emptyMod,
                 onclose: () => Unit = () => {}) = Selector.dropdown(tt, buttonText, buttonIcon, buttonModifierSeq, allModifierSeq, dropdownModifierSeq, onclose)

    def dropdownWithTrigger(trigger: TypedTag[_ <: HTMLElement],
                            allModifierSeq: ModifierSeq = emptyMod,
                            dropdownModifierSeq: ModifierSeq = emptyMod,
                            onclose: () => Unit = () => {}) = Selector.dropdown(tt, trigger, allModifierSeq, dropdownModifierSeq, onclose)

  }


  // JUMBOTRON
  def jumbotron(modifiers: ModifierSeq) =
    div(container +++ themeShowcase)(role := "main")(
      div(bsnsheet.jumbotron)(
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
    val tA = textArea(20)(initText, spellcheck := false, onscroll := { (e: Event) ⇒ setScrollMode })
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
    val ID = uuID.short
    form(formInline)(
      for {
        e <- elements
      } yield {
        div(formGroup)(
          e.e1(`for` := ID, marginLeft := 5),
          e.e2(formControl, marginLeft := 5, id := ID)
        )
      }
    )
  }

  def group(e1: TypedTag[HTMLElement], e2: TypedTag[HTMLElement]) = ElementGroup(e1, e2)


  // PANELS
  def panel(bodyContent: String = "", heading: Option[String] = None) =
    div(bsnsheet.panelClass +++ panelDefault)(
      heading.map { h => div(panelHeading)(h) }.getOrElse(div),
      div(panelBody)(bodyContent)
    )


  // ALERTS
  def successAlerts(title: String, content: Seq[String], triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(bsnsheet.alert_success, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def infoAlerts(title: String, content: Seq[String], triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_info, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def warningAlerts(title: String, content: Seq[String], triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_warning, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def dangerAlerts(title: String, content: Seq[String], triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    new Alert(alert_danger, title, content, triggerCondition, todocancel)(otherButtons: _*).render

  def successAlert(title: String, content: String, triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    successAlerts(title, Seq(content), triggerCondition, todocancel)(otherButtons.toSeq: _*)

  def infoAlert(title: String, content: String, triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    infoAlerts(title, Seq(content), triggerCondition, todocancel)(otherButtons.toSeq: _*)

  def warningAlert(title: String, content: String, triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    warningAlerts(title, Seq(content), triggerCondition, todocancel)(otherButtons.toSeq: _*)

  def dangerAlert(title: String, content: String, triggerCondition: Rx.Dynamic[Boolean] = Rx(true), todocancel: () ⇒ Unit = () => {})(otherButtons: ExtraButton*) =
    dangerAlerts(title, Seq(content), triggerCondition, todocancel)(otherButtons.toSeq: _*)


  implicit class TagCollapserOnClick[S <: TypedTag[HTMLElement]](trigger: S) {
    def expandOnclick[T <: TypedTag[HTMLElement]](inner: T) = {

      val collapser = new Collapser(inner, trigger)
      div(
        collapser.triggerTag,
        collapser.innergTag
      )
    }
  }

  implicit class TagCollapserDynamicOnCondition(triggerCondition: Rx.Dynamic[Boolean]) {
    def expand[T <: TypedTag[HTMLElement]](inner: T, trigger: T = tags.div()) = {
      val collapser = new Collapser(inner, div, triggerCondition.now)
      Rx {
        collapser.switchTo(triggerCondition())
      }

      div(
        trigger.render,
        collapser.innergTag
      )
    }
  }


  implicit class TagCollapserVarOnCondition(triggerCondition: Var[Boolean]) {
    def expand[T <: TypedTag[HTMLElement]](inner: T, trigger: T = tags.div()) = {
      val collapser = new Collapser(inner, div, triggerCondition.now)
      Rx {
        collapser.switchTo(triggerCondition())
      }

      div(
        trigger.render,
        collapser.innergTag
      )
    }
  }

  def collapser(rawInnerTag: TypedTag[HTMLElement], rawTriggerTag: TypedTag[HTMLElement], initExpand: Boolean = false) =
    new Collapser(rawInnerTag, rawTriggerTag, initExpand)

  // COLLAPSERS
  class Collapser(rawInnerTag: TypedTag[HTMLElement], rawTriggerTag: TypedTag[HTMLElement], initExpand: Boolean = false) {

    private val hrefID = uuID.short("c")
    val triggerTag = a(data("toggle") := "collapse", href := s"#${hrefID}", aria.expanded := true, aria.controls := hrefID)(rawTriggerTag).render
    val innergTag = div(ms("collapse"), id := hrefID, aria.expanded := initExpand, role := presentation_role)(rawInnerTag).render


    private lazy val collapserMapping = new Collapse(triggerTag)


    def switch: Unit = collapserMapping.toggle


    def switchTo(b: Boolean) = {
      if (b) collapserMapping.show
      else collapserMapping.hide
    }

  }


  // TABS
  case class Tab(title: String, content: BS, active: Boolean, onclickExtra: () => Unit, onRemoved: Tab => Unit = Tab => {}, tabID: String = uuID.short("t"), refID: String = uuID.short("r")) {

    content.copy()

    def activeClass = if (active) (ms("active"), ms("active in")) else (ms(""), ms(""))
  }

  case class Tabs(tabs: Var[Seq[Tab]], closable: Boolean = false, onCloseExtra: Tab => Unit = Tab => {}) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    def add(title: String, content: BS, active: Boolean = false, onclickExtra: () => Unit = () => {}, onAddedTab: Tab => Unit = Tab => {}, onRemovedTab: Tab => Unit = Tab => {}): Tabs =
      add(Tab(title, content, active, onclickExtra, onRemovedTab), onAddedTab)

    def add(tab: Tab, onAdded: Tab => Unit): Tabs = {
      val ts = Tabs(Var(tabs.now :+ tab))
      onAdded(tab)
      ts
    }

    def remove(tab: Tab) = {
      tabs() = {
        tabs.now.filterNot {
          _.tabID == tab.tabID
        }
      }
      tab.onRemoved(tab)
    }

    def closable(isClosable: Boolean, onclose: (Tab) => Unit) = copy(closable = isClosable, onCloseExtra = onclose)


    lazy val tabClose: ModifierSeq = Seq(
      relativePosition,
      fontSize := 20,
      color := "#222",
      right := -10
    )

    private def cloneRender = Tabs(Var(tabs.now.map {
      _.copy(tabID = uuID.short("t"), refID = uuID.short("r"))
    }))

    private def rendering(navStyle: NavStyle = pills) = Rx {
      val existsOneActive = tabs().map {
        _.active
      }.exists(_ == true)
      val theTabs = {
        val ts = tabs()
        if (!existsOneActive && !ts.isEmpty) ts.head.copy(active = true) +: ts.tail
        else ts
      }

      val tabList = ul(navStyle, tab_list_role)(
        theTabs.map { t =>
          li(presentation_role +++ t.activeClass._1)(
            a(id := t.tabID,
              href := s"#${t.refID}",
              tab_role,
              data("toggle") := "tab",
              data("height") := true,
              aria.controls := t.refID,
              onclick := t.onclickExtra
            )(button(ms("close") +++ tabClose, `type` := "button", onclick := { () ⇒ remove(t) })(raw("&#215")), t.title)
          )
        }).render

      val tabDiv = div(
        tabList,
        div(tab_content +++ (paddingTop := 10).toMS)(
          theTabs.map { t =>
            div(id := t.refID, tab_pane +++ fade +++ t.activeClass._2, tab_panel_role, aria.labelledby := t.tabID)(t.content)
          }
        )
      )

      import net.scalapro.sortable._
      Sortable(tabList)
      tabDiv.render
    }

    def render(navStyle: NavStyle = pills): HTMLDivElement = div(
      {
        cloneRender.rendering(navStyle)
      }
    ).render
  }


  def tabs = Tabs.apply(Var(Seq()))

  // Tables
  case class Row(values: Seq[String])

  case class BSTableStyle(tableStyle: TableStyle, headerStyle: ModifierSeq)

  trait Sorting

  object NoSorting extends Sorting

  object PhantomSorting extends Sorting

  object AscSorting extends Sorting

  object DescSorting extends Sorting

  case class SortingStatus(col: Int, sorting: Sorting)

  case class Table(headers: Seq[String] = Seq(),
                   rows: Seq[Row] = Seq(),
                   bsTableStyle: BSTableStyle = BSTableStyle(default_table, emptyMod),
                   sorting: Boolean = false) {

    val filteredRows = Var(rows)
    val nbColumns = rows.headOption.map {
      _.values.length
    }.getOrElse(0)

    val sortingStatuses = Var(
      Seq.fill(nbColumns)(
        if (sorting) SortingStatus(0, PhantomSorting) else SortingStatus(0, NoSorting))
    )


    def addHeaders(hs: String*) = copy(headers = hs)

    def addRow(row: Row): Table = copy(rows = rows :+ row)

    def addRow(row: String*): Table = addRow(Row(row))

    def sortable = copy(sorting = true)

    //    def addRowElement(typedTags: TypedTag[_ <: HTMLElement]*): Table = addRow(Row(typedTags.map {
    //      td(_)
    //    }))

    type RowType = (String, Int) => TypedTag[HTMLElement]

    private def fillRow(row: Seq[String], rowType: RowType) = tags.tr(
      for (
        (cell, id) <- row.zipWithIndex
      ) yield {
        rowType(cell, id)
      }
    )

    def filter(containedString: String) = {
      filteredRows() = rows.filter { r =>
        r.values.mkString("").contains(containedString)
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

    def sort(col: Int, sorting: Sorting): Sorting = {
      filteredRows() = {
        val sort = filteredRows.now.sortBy(_.values(col))
        sorting match {
          case DescSorting => sort.reverse
          case _ => sort
        }
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
              fillRow(r.values, (s: String, _) => td(s))
            }
          )
        }
      )
    }
  }


  def table = new Table

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
    val cssglyph = glyph +++ (paddingLeft := 3).toMS

    lazy val div = {
      buttonIcon("", preGlyph, cssglyph, action)
    }
  }

  case class TwoStatesSpan(glyph: ModifierSeq,
                           glyph2: ModifierSeq,
                           action: () ⇒ Unit,
                           action2: () ⇒ Unit,
                           preString: String,
                           buttonStyle: ModifierSeq = emptyMod
                          ) extends TwoStates {
    val cssglyph = glyph +++ (paddingLeft := 3).toMS

    lazy val cssbutton: ModifierSeq = Seq(
      paddingTop := 8,
      border := "none"
    )

    lazy val div = button(preString, onclick := action)(buttonStyle +++ cssbutton +++ pointer)(
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
            case s: ExclusiveStringButton ⇒ button(s.title, onclick := action(b, s.action))(stringButtonBackground(s) +++ stringInGroup)
            case g: ExclusiveGlyphButton ⇒ buttonIcon("", glyphButtonBackground(g), g.glyph, action(b, g.action))
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
      val label: TypedTag[HTMLLabelElement] = tags.label(title)(labelStyle, paddingRight := 5)

      val tag: T = m
    }
  }

  private def insideForm[T <: HTMLElement](formTags: FormTag[T]*) =
    for {
      ft <- formTags
    } yield {
      div(formGroup, paddingRight := 5)(
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

}