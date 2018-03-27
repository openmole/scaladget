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

import org.scalajs.dom.raw.Element
import scalatags.JsDom.all._

object SVGStarDemo {

  val sc = sourcecode.Text {

    import scalatags.JsDom.svgTags
    import scalatags.JsDom.svgAttrs._
    import scaladget.svg._

    val scene = svgTags.g.render

    val star = path().m(150, 20).l(240, 240).l(30, 90).l(270, 90).l(60, 240).z
    scene.appendChild(star.render(fill := "red").render)
    scene.appendChild(star.render(fill := "yellow", transform := "scale(0.5) translate(150,130)").render)
    scene.appendChild(star.render(fill := "green", transform := "scale(0.25) translate(450,390)").render)

    svgTags.svg(
      width := 2500,
      height := 2500,
      scene
    ).render

  }

  val elementDemo = new ElementDemo {
    def title: String = "Path"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
