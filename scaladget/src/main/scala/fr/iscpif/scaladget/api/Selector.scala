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
                 onclickExtra: () ⇒ Unit = () ⇒ {}) = new Options(contents, defaultIndex, key, naming, onclickExtra)

  def dropdown[T <: HTMLElement](content: TypedTag[T],
                                 buttonText: String,
                                 buttonModifierSeq: ModifierSeq = emptyMod,
                                 allModifierSeq: ModifierSeq = emptyMod) = new Dropdown(content, buttonText, buttonModifierSeq, allModifierSeq)


  class Dropdown[T <: HTMLElement](content: TypedTag[T],
                                   buttonText: String,
                                   modifierSeq: ModifierSeq,
                                   allModifierSeq: ModifierSeq) {

    val open = Var(false)

    val render = div(allModifierSeq)(
      Rx {
        bs.buttonGroup(ms(open(), "open", ""))(
          bs.button(buttonText, modifierSeq +++ dropdownToggle, () => open() = true)(
            data("toggle") := "dropdown", aria.haspopup := true, role := "button", aria.expanded := open(), tabindex := 0)(
            span(caret, sheet.marginLeft(4))),
          div(dropdownMenu +++ (padding := 10))(content)
        )
      }
    ).render


    def close[T <: HTMLElement]: Unit = {
      open() = false
    }
  }

  class Options[T](private val _contents: Seq[T],
                   defaultIndex: Int = 0,
                   key: ModifierSeq = emptyMod,
                   naming: T=> String,
                   onclose: () => Unit = () => {},
                   onclickExtra: () ⇒ Unit = () ⇒ {}) {

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

    def isContentsEmpty = contents.now.isEmpty

    lazy val selector: HTMLElement = div(
      Rx {
        buttonGroup(
          toClass(
            if (opened()) "open"
            else ""
          ))(
          bs.button(content().map{naming}.getOrElse("") + " ", key +++ dropdownToggle, () => {
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
              li(a(href := "#")(naming(c)), onclick := { () =>
                content() = Some(c)
                opened() = !opened.now
                onclose()
                false
              })
            }
          )
        )
      }
    ).render
  }

}

