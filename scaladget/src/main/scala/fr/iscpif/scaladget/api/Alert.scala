package scaladget.api

import scaladget.api.{BootstrapTags => bs}
import scaladget.stylesheet.{all => sheet}

import scalatags.JsDom.all._
import scalatags.JsDom.tags
import sheet._
import rx._
import scaladget.api.Alert.ExtraButton
import scaladget.tools.JsRxTags._
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
            content: Seq[String],
            triggerCondition: Rx.Dynamic[Boolean],
            cancelAction: () => Unit
           )(otherButtons: ExtraButton*) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val closed = Var(false)

  val render = tags.div(
    Rx {
      if (triggerCondition() && !closed()) {
        tags.div(alertStyle +++ {
          if (otherButtons.isEmpty) emptyMod else sheet.paddingBottom(50)
        })(
          bs.closeButton("", ()=> {
            cancelAction()
            closed() = true
          }),
          h4(title),
          if( content.size == 1) content
          else {
          ul(Seq(sheet.marginLeft(-25), sheet.marginTop(20)))(
            for{
              c<- content
            } yield {li(c)})},
          p(sheet.marginTop(25))(
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
  )

}