package scaladget.bootstrapnative

import scaladget.bootstrapnative.bsn._
import scaladget.tools._

import scalatags.JsDom.all._
import scalatags.JsDom.tags
import rx._

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
    def render = button(content, onclick := action)(buttonStyle)
  }

}

class Alert(alertStyle: AlertStyle,
            title: String,
            content: Seq[String],
            triggerCondition: Rx.Dynamic[Boolean],
            cancelAction: () => Unit
           )(otherButtons: Alert.ExtraButton*) {

  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val closed = Var(false)

  val render = tags.div(
    Rx {
      if (triggerCondition() && !closed()) {
        tags.div(
          alertStyle +++ {
            if (otherButtons.isEmpty) emptyMod else (paddingBottom := 50).toMS
          })(
            closeButton("", () => {
              cancelAction()
              closed() = true
            }),
            h4(title),
            if (content.size == 1) content
            else {
              ul(
                for {
                  c <- content
                } yield {
                  li(c)
                })(marginLeft := -25, marginTop := 20)
            },
            p(marginTop := 25)(
              buttonGroup(floatLeft)(
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