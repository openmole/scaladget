package scaladget.bootstrapnative

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

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._



case class SelectableButton(text: String, defaultActive: Boolean, modifier: Modifier[HtmlElement], setters: HESetters) {
  val active = Var(defaultActive)
}
//
object SelectableButtons {

  trait SelectionButtonType {
    def cssStyle: String
    def onselection: (SelectableButton, Seq[SelectableButton])=> Unit
  }

  object RadioSelection extends SelectionButtonType {
    def cssStyle = "radio"
    def onselection=  (sb: SelectableButton, all: Seq[SelectableButton])=> {
      all.foreach { _.active.set(false)}
      sb.active.set(true)
    }
  }

  object CheckBoxSelection extends SelectionButtonType {
    def cssStyle = "checkbox"
    def onselection=  (sb: SelectableButton, all: Seq[SelectableButton])=> {
      sb.active.set(!sb.active.now)
    }
  }

  def bar(buttons: Seq[SelectableButton], buttonType: SelectionButtonType, setters: Setter[HtmlElement]*): HtmlElement =
    buttonGroup.amend(
      dataAttr("toggle") := "buttons",
      setters,
      for {
        button <- buttons
      } yield {
          label(btn, button.setters,  cls <-- button.active.signal.map{a=> if(a) "active" else ""},
            input(`type` := buttonType.cssStyle, autoComplete := "off",
              checked <-- button.active.signal,
             // if (button.active()) checked := "checked" else emptyMod,
              onClick --> { _ =>
                buttonType.onselection(button, buttons)
               // button.modifier
              }),
            button.text
          )
      }
    )
}


import SelectableButtons._
class SelectableButtons(heSetters: HESetters, buttonType: SelectionButtonType, buttons: Seq[SelectableButton]) {

  val render = SelectableButtons.bar(buttons, buttonType, heSetters)


  def active = buttons.filter {
    _.active.now
  }

  def activeIndex: Int = buttons.zipWithIndex.filter{case (b, index)=> b.active.now}.head._2

}