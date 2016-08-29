package fr.iscpif.scaladget.api

import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all => sheet}

import scalatags.JsDom.all._
import scalatags.JsDom.tags
import sheet._
import rx._
import fr.iscpif.scaladget.api.Alert.ExtraButton
import rx.Rx

/*
 * Copyright (C) 29/08/16 // mathieu.leclaire@openmole.org
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

object Alert {

  case class ExtraButton(content: String, buttonStyle: ModifierSeq = btn_default, action: () => Unit = () => {}) {
    def render = bs.button(content, buttonStyle, action)
  }

}

class Alert(alertStyle: AlertStyle,
            title: String,
            content: String,
            triggerCondition: Rx.Dynamic[() => Boolean],
            cancelAction: ()=> Unit
           )(otherButtons: ExtraButton*) {

  val render = Rx {
      if (triggerCondition().apply) {
        tags.div(alertStyle +++ {if(otherButtons.isEmpty) emptyMod else sheet.paddingBottom(50)})(
          bs.closeButton("", cancelAction),
          h4(title),
          p(content),
          p(
            bs.buttonGroup(sheet.floatLeft)(
              for {
                b <- otherButtons
              } yield {
                b.render
              }
            )
          )
        )
      }
      else tags.div
    }
}