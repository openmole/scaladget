package scaladget.api

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

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}
import scaladget.tools.JsRxTags._
import scalatags.JsDom.tags
import scalatags.JsDom.all._
import sheet._
import bs._
import scaladget.api.SelectableButtons.SelectionButtonType
import rx._


case class SelectableButton(text: String, defaultActive: Boolean, modifierSeq: ModifierSeq, onclick: () => Unit) {
  val active = Var(defaultActive)
}

object SelectableButtons {

  trait SelectionButtonType {
    def cssStyle: String
    def onselection: (SelectableButton, Seq[SelectableButton])=> Unit
  }

  object RadioSelection extends SelectionButtonType {
    def cssStyle = "radio"
    def onselection=  (sb: SelectableButton, all: Seq[SelectableButton])=> {
      all.foreach{_.active() = false}
      sb.active() = true
    }
  }

  object CheckBoxSelection extends SelectionButtonType {
    def cssStyle = "checkbox"
    def onselection=  (sb: SelectableButton, all: Seq[SelectableButton])=> {
      sb.active() = !sb.active.now
    }
  }

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

  def bar(modifierSeq: ModifierSeq, buttons: Seq[SelectableButton], buttonType: SelectionButtonType): Modifier =
    buttonGroup(modifierSeq)(data("toggle") := "buttons")(
      for {
        button <- buttons
      } yield {
        tags.label(btn +++ button.modifierSeq +++ rxIf(button.active, toClass("active"), emptyMod))(
          tags.input(`type` := buttonType.cssStyle, autocomplete := "off",
            if(button.active.now) checked := "checked" else emptyMod,
            onclick := { () =>
              buttonType.onselection(button, buttons)
              button.onclick()
            }),
          button.text
        )
      }
    )
}

class SelectableButtons(modifierSeq: ModifierSeq, buttonType: SelectionButtonType, buttons: Seq[SelectableButton]) {

  val render = SelectableButtons.bar(modifierSeq, buttons, buttonType)


  def active = buttons.filter {
    _.active.now
  }

  def activeIndex: Int = buttons.zipWithIndex.filter{case (b, index)=> b.active.now}.head._2

}