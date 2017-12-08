package demo

/*
 * Copyright (C) 23/08/16 // mathieu.leclaire@openmole.org
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

import org.scalajs.dom.Element

import scaladget.bootstrapnative.bsn._
import scaladget.tools._

import scalatags.JsDom.all._

object TooltipDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapnative.Popup._
    import rx._
    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      marginRight := 5
    )

    val labelStyle: ModifierSeq = Seq(
      label_danger,
      marginRight := 5
    )

    val add = Var(false)
    div(
      button("Left", buttonStyle).tooltip("Tooltip on left", Left),
      label("Right", labelStyle).tooltip("Tooltip on right", Right),
      label("Top", labelStyle).tooltip("Tooltip on top", Top),
      button("Bottom", buttonStyle, onclick := { () => {
        add() = true
      }
      }).tooltip("Tooltip on bottom", Bottom),
      Rx {
        if (add()) label("New", labelStyle).tooltip("Tooltip New", Right)
        else div().render
      }
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Tooltip"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
