package fr.iscpif.scaladget.api

/*
 * Copyright (C) 26/08/16 // mathieu.leclaire@openmole.org
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

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import sheet._
import bs._
import rx._

import scalatags.generic.Attr

case class ButtonCheckBox(text: String, defaultActive: Boolean, modifierSeq: ModifierSeq, onclick: () => Unit) {
  println("Init " + text + " " + defaultActive)
  val active = Var(defaultActive)
}

class CheckBoxes(modifierSeq: ModifierSeq)(checkBoxes: Seq[ButtonCheckBox]) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  def render: Modifier =
    buttonGroup(modifierSeq)(data("toggle") := "buttons")(
      for {
        cb <- checkBoxes
      } yield {
        tags.label(btn +++ cb.modifierSeq +++ rxIf(cb.active, toClass("active"), emptyMod))(
          tags.input(`type` := "checkbox", autocomplete := "off",
            if(cb.active.now) checked := "checked" else emptyMod,
            onclick := { () =>
              cb.active() = !cb.active.now
              cb.onclick()
            }),
          cb.text
        )
      }
    )

  def active = checkBoxes.filter {
    _.active.now
  }

}
