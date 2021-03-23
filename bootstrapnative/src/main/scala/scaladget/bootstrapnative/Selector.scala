package scaladget.bootstrapnative

/*
 * Copyright (C) 13/01/15 // mathieu.leclaire@openmole.org
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

import scaladget.bootstrapnative.bsn._
import scaladget.tools.Utils._
import com.raquo.laminar.api.L._
import org.scalajs.dom.raw._

object Selector {

  def options[T](contents: Seq[T],
                 defaultIndex: Int = 0,
                 key: HESetters = emptySetters,
                 naming: T => String,
                 onclose: () => Unit = () => {},
                 onclickExtra: Modifier[HtmlElement] =  emptyMod,
                 decorations: Map[T, HESetters] = Map(),
                 fixedTitle: Option[String] = None) = new Options(contents, defaultIndex, key, naming, onclose, onclickExtra, decorations, fixedTitle)

  def dropdown[T <: HTMLElement](content: HtmlElement,
                                 buttonText: String,
                                 buttonIcon: HESetters,
                                 buttonSetters: HESetters,
                                 allSetters: HESetters,
                                 dropdownSetters: HESetters,
                                 onclose: () => Unit) = {
    lazy val trigger = bsn.buttonIcon(buttonText, buttonSetters :+ dropdownToggle, buttonIcon).amend(span(caret, marginLeft := "4"))

    new Dropdown(
      content,
      trigger,
      allSetters,
      dropdownSetters,
      onclose)
  }

  def dropdown[T <: HTMLElement](content: HtmlElement,
                                 trigger: HtmlElement,
                                 allHESetters: HESetters,
                                 dropdownSetters: HESetters,
                                 onclose: () => Unit): Dropdown[T] = new Dropdown(
    content,
    trigger,
    allHESetters,
    dropdownSetters,
    onclose
  )


  class Dropdown[T <: HTMLElement](content: HtmlElement,
                                   trigger: HtmlElement,
                                   allHESetters: HESetters,
                                   dropdownSetters: HESetters,
                                   onclose: () => Unit) {

    val open = Var(false)

    lazy val render = div(
      allHESetters,
      buttonGroup.amend(
        cls <-- open.signal.map { s => if (s) "open" else "" },
        trigger.amend(dataAttr("toggle") := "dropdown", aria.hasPopup := true, role := "button", aria.expanded <-- open.signal, tabIndex := 0,
          onClick.mapTo(!open.now) --> open),
        div(dropdownMenu, dropdownSetters, padding := "10", content)
      )
    )


    //        contentRender.addEventListener("mousedown", (e: Event) => {
    //      e.stopPropagation
    //    })


    render.ref.onClickOutside(() => {
      close
    })

    def close[T <: HTMLElement]: Unit = {
      onclose()
      open.set(false)
    }
  }

  class Options[T](private val _contents: Seq[T],
                   defaultIndex: Int = 0,
                   key: HESetters = emptySetters,
                   naming: T => String,
                   onclose: () => Unit,
                   onclickExtra: Modifier[HtmlElement] =  emptyMod,
                   decorations: Map[T, HESetters],
                   fixedTitle: Option[String]) {

    implicit def tToString(t: T): String = naming(t)

    val contents = Var(_contents)
    val opened = Var(false)
    val autoID = java.util.UUID.randomUUID.toString

    val content: Var[Option[T]] = Var(_contents.size match {
      case 0 ⇒ None
      case _ ⇒
        if (defaultIndex < _contents.size) Some(_contents(defaultIndex))
        else _contents.headOption
    })

    def setContents(cts: Seq[T], onset: () ⇒ Unit = () ⇒ {}) = {
      contents.set(cts)
      content.set(cts.headOption)
      onset()
    }

    def emptyContents = {
      contents.set(Seq())
      content.set(None)
    }

    def set(t: T) = content.set(Some(t))

    def get: Option[T] = content.now

    def getOrElse(t: T) = get.getOrElse(t)

    def isContentsEmpty = contents.now.isEmpty

    def close = opened.set(false)

    selector.ref.onClickOutside(() => close)

    lazy val selector = {
      div(
        buttonGroup.amend(
          cls <-- opened.signal.map { s =>
            if (s) "open"
            else ""
          },
          child <-- content.signal.map { c =>
            buttonIcon(fixedTitle.getOrElse(c.map {
              naming
            }.getOrElse("") + " "), key :+ dropdownToggle,
              c.map { ct =>
                decorations.getOrElse(ct, emptySetters)
              }.getOrElse(emptySetters), {
                opened.set(!opened.now)
                onclickExtra
              }
            ).amend(
              dataAttr("toggle") := "dropdown", aria.hasPopup := true, role := "button", aria.expanded <-- opened.signal, tabIndex := 0,
              span(caret, marginLeft := "4")
            )
          }
        ),
        ul(dropdownMenu, role := "menu",
          for {
            c <- contents.now
          } yield {
            val line = li(
              a(href := "#",
                span(
                  span(decorations.getOrElse(c, emptySetters).toSeq),
                  span(s" ${naming(c)}")
                ))
            )
            line.amend(onMouseDown.stopPropagation --> { _ =>
             // e.stopPropagation
              content.set(Some(c))
              //opened.set(!opened.now)
              opened.update(!_)
              onclose()
            })
            line
          }
        )
      )
    }

  }

}

