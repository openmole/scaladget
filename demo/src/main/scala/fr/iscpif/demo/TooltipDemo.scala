package fr.iscpif.demo

import org.scalajs.dom.Element
import fr.iscpif.scaladget.tools.JsRxTags._

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

import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import scalatags.JsDom.all._
import sheet._
import bs._
    import rx._

object TooltipDemo extends Demo {
  implicit val ctx: Ctx.Owner = Ctx.Owner.safe()
  val sc = sourcecode.Text {

    import fr.iscpif.scaladget.api.Popup._

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      sheet.marginRight(5)
    )

    val labelStyle: ModifierSeq = Seq(
      label_danger,
      sheet.marginRight(5)
    )

    val add = Var(false)
    div(
      bs.button("Left", buttonStyle, () => {}).tooltip("Tooltip on left", Left),
      label("Right", labelStyle).tooltip("Tooltip on right", Right),
      label("Top", labelStyle).tooltip("Tooltip on top", Top),
      bs.button("Bottom", buttonStyle, () => {
        add() = true
      }).tooltip("Tooltip on bottom", Bottom),
        Rx {
          if (add()) label("New", labelStyle).tooltip("Tooltip New", Right)
          else div()
        }
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Tooltip"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
