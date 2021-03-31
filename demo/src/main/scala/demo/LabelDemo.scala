package demo

import org.scalajs.dom._

/*
 * Copyright (C) 24/08/16 // mathieu.leclaire@openmole.org
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
import com.raquo.laminar.api.L._

object LabelDemo {
  val sc = sourcecode.Text {

    val hovered = Var("None")
    val spanStyle: HESetters = Seq(
      marginTop := "20"
    )

    def overAction(tag: String) = onMouseOver --> { _ => hovered.set(tag) }

    div(
      span("Light", badge_light, overAction("light")).size4(spanStyle),
      span("Primary", badge_primary, overAction("primary")).size4(spanStyle),
      span("Info", badge_info, overAction("info")).size4(spanStyle),
      span("Success", badge_success, overAction("success")).size4(spanStyle),
      span("Warning", badge_warning, overAction("warning")).size5(spanStyle),
      span("Danger", badge_danger, overAction("danger")).size6(spanStyle),
      div(paddingTop := "15", child.text <-- hovered.signal.map { s => s"Hovered: $s" })

    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Label"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}