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

import org.scalajs.dom.Element

import scaladget.bootstrapnative.{BootstrapTags => bs}
import bs._
import scaladget.bootstrapnative.all
import scalatags.JsDom.all._
import scaladget.tools.stylesheet._

object NavBarDemo {
  val sc = sourcecode.Text {

    // Create nav items
    val oneItem = stringNavItem("One", () ⇒
      println("One open")
    )

    val twoItem = stringNavItem("Two", () ⇒
      println("Two open"), true
    )

    val threeItem = navItem(
      bs.input("")(placeholder := "Name", width := 100).render, () ⇒
        println("Three open")
    )

    val fourItem = navItem(
      div(all.glyph_fire +++ (color := "#337ab7"), lineHeight := "35px").render, () ⇒
        println("Four open")
    )

    val fiveItem = navItem(
      buttonGroup()(
      button("OK", onclick := {()=> {println("Five open")}}, all.btn_primary),
      button("Cancel", onclick := {()=> {println("Five cancel")}}, all.btn_default)
      ).render
    )

    //Create the nav bar
    bs.navBar(
      all.navbar_staticTop,
      oneItem,
      twoItem,
      threeItem,
      fourItem.right,
      fiveItem.right
    ).withBrand("img/iscpif.png", Seq(width := 80), ()=> println("Brand")).render.render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Nav bar"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
