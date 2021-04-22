package demo

/*
 * Copyright (C) 22/08/16 // mathieu.leclaire@openmole.org
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

import scaladget.svg.path.Path
import com.raquo.laminar.api.L.svg
import com.raquo.laminar.api.L._
import org.scalajs


object SVGStarDemo {

  val sc = sourcecode.Text {

    val star = Path(precisionPattern = "%1.2f").m(150.2235, 20).l(240.22201, 240).l(30.3666, 90).l(270, 90).l(60, 240).z

    val scene = svg.g(
      star.render.amend(svg.fill := "red"),
      star.render.amend(svg.fill := "yellow", svg.transform := "scale(0.5) translate(150,130)"),
      star.render.amend(svg.fill := "green", svg.transform := "scale(0.25) translate(450,390)")
    )

    svg.svg(
      svg.width := "2500",
      svg.height := "2500",
      scene
    )

  }

  val elementDemo = new ElementDemo {
    def title: String = "Path"

    def code: String = sc.source

    def element: SvgElement = sc.value
  }
}
