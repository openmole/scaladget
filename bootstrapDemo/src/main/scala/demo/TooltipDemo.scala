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

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

object TooltipDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapnative.Popup._

    div(
      button("Left", btn_secondary, marginRight := "5").tooltip("Tooltip on left", Left),
      label("Right", badge_primary, marginRight := "5").tooltip("Tooltip on right", Right),
      label("Top", badge_success, marginRight := "5").tooltip("Tooltip on top", Top),
      button("Bottom", btn_info_outline).tooltip("Tooltip on bottom", Bottom)
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Tooltip"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
