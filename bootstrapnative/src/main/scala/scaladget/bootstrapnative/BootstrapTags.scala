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
import net.scalapro.sortable.{EventS, SortableProps}
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
  case class Popover(element: TypedTag[org.scalajs.dom.raw.HTMLElement],
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
      collapser.div
    }
  }

  implicit class TagCollapserWithReactive(r: Rx[Boolean]) {
    def expand[T <: TypedTag[HTMLElement]](inner: T) = {

      println("R " + r)
      val collapser = new Collapser(inner, initExpand = r.now)
      val dynamic = new DynamicCollapser(collapser, r)

      collapser.div

    }
  }

  // COLLAPSERS
  class Collapser(rawInnerTag: TypedTag[HTMLElement], rawTriggerTag: TypedTag[HTMLElement] = div(), initExpand: Boolean = false) {

    private val hrefID = uuID.short("c")

    val classTrigger = if (initExpand) "" else "collapsed"
    val classInner = "collapse" + {
      if (initExpand) " in" else ""
    }

    val triggerTag = a(data("toggle") := "collapse", `class` := classTrigger, href := s"#${hrefID}", aria.expanded := initExpand, aria.controls := hrefID)(rawTriggerTag).render
    val innergTag = tags.div(ms(classInner), id := hrefID, aria.expanded := initExpand, role := presentation_role)(rawInnerTag).render

    val div = tags.div(
      triggerTag,
      innergTag
    )
  }

  class DynamicCollapser(val collapser: Collapser, expand: Rx[Boolean]) {
    expand.trigger {
      collapser.triggerTag.click()
    }

  }


  // TABS
  case class Tab(title: String, content: BS, onclickExtra: () => Unit = () => {}, tabID: String = uuID.short("t"), refID: String = uuID.short("r"))

  object Tabs {
    def tabs(initialTabs: Seq[Tab] = Seq()) = TabHolder(initialTabs, false, 0, None, (tab: Tab) => {})


    def defaultSortOptions = (ts: Var[Seq[Tab]], setActive: Int => Unit) => new SortableProps {
      override val onEnd = scala.scalajs.js.defined {
        (event: EventS) ⇒
          val oldI = event.oldIndex.asInstanceOf[Int]
          val newI = event.newIndex.asInstanceOf[Int]
          ts() = ts.now.updated(oldI, ts.now(newI)).updated(newI, ts.now(oldI))
          setActive(newI)
      }
    }

    case class TabHolder(tabs: Seq[Tab], isClosable: Boolean, initIndex: Int, sortableOptions: Option[(Var[Seq[Tab]], Int => Unit) => SortableProps], onActivation: Tab => Unit) {
      def add(title: String, content: BS, onclickExtra: () => Unit = () => {}, onAddedTab: Tab => Unit = Tab => {}): TabHolder =
        add(Tab(title, content, onclickExtra), onAddedTab)

      def add(tab: Tab, onAdded: Tab => Unit): TabHolder = {
        copy(tabs = this.tabs :+ tab)
      }

      def closable = copy(isClosable = true)

      def initialIndex(index: Int) = copy(initIndex = index)

      def withSortableOptions(options: (Var[Seq[Tab]], Int => Unit) => SortableProps) = copy(sortableOptions = Some(options))

      def onActivation(onActivation: Tab => Unit = Tab => {}) = copy(onActivation = onActivation)

      def build = {
        val ts = Var(tabs)
        Tabs(Var(tabs), isClosable, tabs.lift(initIndex), sortableOptions.getOrElse(defaultSortOptions), onActivation)
      }
    }

  }

  case class Tabs(tabs: Var[Seq[Tab]], isClosable: Boolean, initActive: Option[Tab], sortableOptions: (Var[Seq[Tab]], Int => Unit) => SortableProps, onActivation: Tab => Unit = Tab => {}, onCloseExtra: Tab => Unit = Tab => {}, onRemoved: Tab => Unit = Tab => {}) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
    val active: Var[Option[Tab]] = Var(initActive)


    def setActive(index: Int): Unit = {
      tabs.now.lift(index).foreach {
        setActive
      }
    }

    def setActive(tab: Tab): Unit = {
      active() = Some(tab)
      active.now.foreach {
        onActivation
      }
    }

    def activeClass(activeTab: Option[Tab]) =
      (t: Tab) => activeTab match {
        case Some(act: Tab) =>
          if (t == act) ("active", "active in")
          else ("", "")
        case _ => ("", "")
      }


    def remove(tab: Tab) = {
      tabs() = {
        tabs.now.filterNot {
          _.tabID == tab.tabID
        }
      }

      tabs.now.headOption.foreach {
        setActive
      }

      onRemoved(tab)
    }

    //def onclose(f: (Tab) => Unit) = copy(onCloseExtra = f)

    lazy val tabClose: ModifierSeq = Seq(
      relativePosition,
      fontSize := 20,
      color := "#222",
      right := -10
    )


    lazy val render = div(
      Rx {
        val tabList = ul(pills, tab_list_role)({
          val actClass = activeClass(active())
          tabs().map { t =>
            li(presentation_role +++ ms(actClass(t)._1))(
              a(id := t.tabID,
                tab_role,
                pointer,
                data("toggle") := "tab",
                data("height") := true,
                aria.controls := t.refID,
                onclick := { () =>

                  setActive(t)
                } //t.onclickExtra
              )(if (isClosable) button(ms("close") +++ tabClose, `type` := "button", onclick := { (e: Event) ⇒ remove(t); e.stopPropagation() })(raw("&#215")) else span, t.title)
            )
          }
        }).render

        val tabDiv = div(
          div(tab_content +++ (paddingTop := 10).toMS)({
            val actClass = activeClass(active())
            tabs().map { t =>
              div(id := t.refID, tab_pane +++ fade +++ ms(actClass(t)._2)
              )(t.content)
            }
          }
          )
        )

        import net.scalapro.sortable._
        Sortable(tabList, sortableOptions(tabs, setActive))

        div(
          tabList,
          tabDiv
        )
      }

    )

    initActive.foreach {
      setActive
    }
  }

  //TABLE
  def dataTable = new DataTable

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