package fr.iscpif.scaladget.api

/*
 * Copyright (C) 19/04/16 // mathieu.leclaire@openmole.org
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

import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.tools.JsRxTags._
import sheet._
import rx._
import bs._
import org.scalajs.dom.raw._
import scalatags.JsDom.all._
import scalatags.JsDom.{TypedTag, tags}

object Selector {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  def options[T](contents: Seq[T],
                 defaultIndex: Int = 0,
                 key: ModifierSeq = emptyMod,
                 naming: T => String,
                 onclose: () => Unit = () => {},
                 onclickExtra: () ⇒ Unit = () ⇒ {},
                 decorations: Map[T, ModifierSeq] = Map(),
                 fixedTitle: Option[String] = None) = new Options(contents, defaultIndex, key, naming, onclose, onclickExtra, decorations, fixedTitle)

  def dropdown[T <: HTMLElement](content: TypedTag[T],
                                 buttonText: String,
                                 buttonIcon: ModifierSeq,
                                 buttonModifierSeq: ModifierSeq,
                                 allModifierSeq: ModifierSeq,
                                 dropdownModifierSeq: ModifierSeq,
                                 onclose: () => Unit) = {
    lazy val trigger: TypedTag[_ <: HTMLElement] =
      bs.button(buttonText, buttonModifierSeq +++ dropdownToggle, buttonIcon)(
        span(caret, sheet.marginLeft(4))
      )

    lazy val dd: Dropdown[T] = new Dropdown(
      content,
      trigger,
      allModifierSeq,
      dropdownModifierSeq,
      onclose)

    dd
  }

  def dropdown[T <: HTMLElement](content: TypedTag[T],
                                 trigger: TypedTag[_],
                                 allModifierSeq: ModifierSeq,
                                 dropdownModifierSeq: ModifierSeq,
                                 onclose: () => Unit): Dropdown[T] = new Dropdown(
                                                                          content,
                                                                          trigger,
                                                                          allModifierSeq,
                                                                          dropdownModifierSeq,
                                                                          onclose
                                                                          )


  class Dropdown[T <: HTMLElement](content: TypedTag[T],
                                   trigger: TypedTag[_],
                                   allModifierSeq: ModifierSeq,
                                   dropdownModifierSeq: ModifierSeq,
                                   onclose: () => Unit) {

    val open = Var(false)

    def toggle = open() = !open.now

    val render = div(allModifierSeq)(
      Rx {
        bs.buttonGroup(ms(open(), "open", ""))(
          trigger(data("toggle") := "dropdown", aria.haspopup := true, role := "button", aria.expanded := open(), tabindex := 0)(onclick := { () => toggle }),
          div(dropdownMenu +++ dropdownModifierSeq +++ (padding := 10))(content)
        )
      }
    ).render


    def close[T <: HTMLElement]: Unit = {
      onclose()
      toggle
    }
  }

  class Options[T](private val _contents: Seq[T],
                   defaultIndex: Int = 0,
                   key: ModifierSeq = emptyMod,
                   naming: T => String,
                   onclose: () => Unit,
                   onclickExtra: () ⇒ Unit,
                   decorations: Map[T, ModifierSeq],
                   fixedTitle: Option[String]) {

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

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
      contents() = cts
      content() = cts.headOption
      onset()
    }

    def emptyContents = {
      contents() = Seq()
      content() = None
    }

    def set(t: T) = content() = Some(t)

    def get: Option[T] = content.now

    def getOrElse(t: T) = get.getOrElse(t)

    def isContentsEmpty = contents.now.isEmpty

    lazy val selector: TypedTag[HTMLElement] = div(
      Rx {
        buttonGroup(
          toClass(
            if (opened()) "open"
            else ""
          ))(
          bs.button(fixedTitle.getOrElse(content().map {
            naming
          }.getOrElse("") + " "), key +++ dropdownToggle,
            content().map { ct =>
              decorations.getOrElse(ct, emptyMod)
            }.getOrElse(emptyMod), () => {
              opened() = !opened.now
              onclickExtra()
            })(
            data("toggle") := "dropdown", aria.haspopup := true, role := "button", aria.expanded := {
              if (opened()) true else false
            }, tabindex := 0)(
            span(caret, sheet.marginLeft(4))),
          ul(dropdownMenu, role := "menu")(
            for {
              c <- contents.now
            } yield {
              li(
                a(href := "#")(
                  span(
                    span(decorations.getOrElse(c, emptyMod).toSeq),
                    span(s" ${naming(c)}")
                  )),
                onclick := { () =>
                  content() = Some(c)
                  opened() = !opened.now
                  onclose()
                  false
                }
              )
            }
          )
        )
      })
  }

}

