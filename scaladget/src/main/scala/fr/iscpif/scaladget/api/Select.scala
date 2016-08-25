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
import sheet._
import rx._
import bs._
import org.scalajs.dom.raw._

import scalatags.JsDom.all._
import scalatags.JsDom.{TypedTag, tags}

object Select {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  case class SelectElement[T](value: T, mod: ModifierSeq = emptyMod)

  implicit def seqOfTtoSeqOfSelectElement[T](s: Seq[T]): Seq[SelectElement[T]] = s.map { e => SelectElement(e) }

  implicit def tToTElement[T](t: T): SelectElement[T] = SelectElement(t)

  implicit def optionSelectElementTToOptionT[T](opt: Option[SelectElement[T]]): Option[T] = opt.map {
    _.value
  }

  def apply[T](
                contents: Seq[SelectElement[T]],
                default: Option[T],
                naming: T => String,
                key: ModifierSeq = emptyMod,
                onclickExtra: () ⇒ Unit = () ⇒ {}
              ) = tags.div() //new Select(contents, default, naming, key, onclickExtra)

  def apply[T <: HTMLElement](content: TypedTag[T],
                              buttonText: String,
                              modifierSeq: ModifierSeq,
                              onclose: () => {}) = {
    bs.buttonGroup()(
      bs.button(buttonText, modifierSeq +++ dropdownToggle, () => {})(
        data("toggle") := "dropdown", aria.haspopup := true, role := "button", aria.expanded := false, tabindex := 0)(
        span(caret, sheet.marginLeft(4))
      ),
      div(dropdownMenu +++ (padding := 10))(content)
    )

  }
}
