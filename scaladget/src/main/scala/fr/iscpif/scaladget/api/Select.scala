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

import Popup._
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all ⇒ sheet}
import fr.iscpif.scaladget.tools.JsRxTags._
import sheet._
import rx._
import org.scalajs.dom.raw._
import scalatags.JsDom.all._
import scalatags.JsDom.{tags ⇒ tags}

object Select {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  case class SelectElement[T](value: T, mod: ModifierSeq = emptyMod)

  implicit def seqOfTtoSeqOfSelectElement[T](s: Seq[T]): Seq[SelectElement[T]] = s.map { e => SelectElement(e) }

  implicit def tToTElement[T](t: T): SelectElement[T] = SelectElement(t)

  implicit def optionSelectElementTToOptionT[T](opt: Option[SelectElement[T]]): Option[T] = opt.map{_.value}

  def apply[T](
                contents: Seq[SelectElement[T]],
                default: Option[T],
                naming: T => String,
                key: ModifierSeq = emptyMod,
                onclickExtra: () ⇒ Unit = () ⇒ {}
              ) = new Select(contents, default, naming, key, onclickExtra)

}

import fr.iscpif.scaladget.api.Select._

class Select[T](
                 private val _contents: Seq[SelectElement[T]],
                 default: Option[T] = None,
                 naming: T => String,
                 key: ModifierSeq = emptyMod,
                 onclickExtra: () ⇒ Unit = () ⇒ {}
               ) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  val contents = Var(_contents)
  val autoID = java.util.UUID.randomUUID.toString

  val content: Var[Option[SelectElement[T]]] = Var(_contents.size match {
    case 0 ⇒ None
    case _ ⇒ default match {
      case None ⇒ _contents.headOption
      case _ ⇒
        val defaultSelectElement: SelectElement[T] = default match {
          case Some(t) => t
          case _ => _contents.head
        }
        if (_contents.exists(_ == defaultSelectElement)) Some(defaultSelectElement)
        else _contents.headOption
    }
  })

  val hasFilter = Var(false)
  val filtered: Var[Seq[SelectElement[T]]] = Var(_contents)
  filtered() = _contents.take(100)

  lazy val inputFilter: HTMLInputElement = bs.input("")(selectFilter, placeholder := "Filter", oninput := { () ⇒
    filtered() = _contents.filter { f =>
      naming(f.value).toUpperCase.contains(inputFilter.value.toUpperCase)
    }
  }).render

  def resetFilter = {
    filtered() = contents.now.take(100)
    content() = None
  }

  def setContents(cts: Seq[SelectElement[T]], onset: () ⇒ Unit = () ⇒ {}) = {
    contents() = cts
    content() = cts.headOption
    filtered() = cts.take(100)
    inputFilter.value = ""
    onset()
  }

  def emptyContents = {
    contents() = Seq()
    content() = None
  }

  def isContentsEmpty = contents.now.isEmpty

  lazy val selector = {

    lazy val popupButton: Popup = new Popup(
      span(key +++ pointer, `type` := "button")(
        Rx {
          content().map { c ⇒
            bs.glyphSpan(c.mod)
          }
        },
        Rx {
          content().map { c => naming(c.value) }.getOrElse("") + " "
        },
        span(caret)
      ).render,
      div(
        Rx {
          if (hasFilter())
            div(
              tags.form(inputFilter)(`type` := "submit", onsubmit := { () ⇒
                content() = filtered().headOption
                false
              })
            )
          else tags.div
        },
        Rx {
          tags.div(sheet.marginLeft(0) +++ (listStyleType := "none"))(
            if (filtered().size < 100) {
              for (c ← filtered()) yield {
                tags.div(pointer, onclick := { () ⇒
                  content() = contents().filter {
                    _.value == c.value
                  }.headOption.map {
                    _.value
                  }
                  onclickExtra()
                  popupButton.close
                })(naming(c.value))
              }
            }
            else tags.li("To many results, filter more !")
          )
        }
      ),
      ClickPopup,
      Bottom,
      whitePopupWithBorder,
      noArrow
    )

    popupButton.popup

  }

  lazy val selectorWithFilter = {
    // hasFilter() = if(contents().size > 9) true else false
    hasFilter() = true
    selector
  }
}
