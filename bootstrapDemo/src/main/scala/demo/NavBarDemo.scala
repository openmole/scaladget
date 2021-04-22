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


import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._


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
      form(
        formInline,
        inputTag("").amend(placeholder := "Name", width := "100"),
        button("Search", btn_success_outline, onClick --> { _ =>
          println("Five open")
        })
      ),
      alignRight = true
    )

    //Create the nav bar
    navBar(
      Seq(navbar_light, backgroundColor := "#23ed12", bg_light),
      oneItem,
      twoItem,
      threeItem,
    ).withBrand("img/iscpif.png", width := "80", () => println("Brand")).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Nav bar"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
