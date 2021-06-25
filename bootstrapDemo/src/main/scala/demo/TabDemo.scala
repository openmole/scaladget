package demo

/*
 * Copyright (C) 05/01/17 // mathieu.leclaire@openmole.org
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

object TabDemo extends Demo {
  val sc = sourcecode.Text {

    val div1 = div("Lorem ipsum dolor sit amet, " +
      "consectetur adipiscing elit, sed do eiusmod tempor " +
      "incididunt ut labore et dolore magna aliqua. " +
      "Ut enim ad minim veniam, quis nostrud exercitation ullamco " +
      "laboris nisi ut aliquip ex ea commodo consequat. Duis aute " +
      "irure dolor in reprehenderit in voluptate velit esse cillum dolore " +
      "eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, " +
      "sunt in culpa qui officia deserunt mollit anim id est laborum.", padding := "10")

    case class AA(i: Int)

    val rng = scala.util.Random

    lazy val trigger = button("Add tab", onClick --> { _ => {
      val aa = AA(rng.nextInt())
      theTabs.add(Tab(aa, span("An other"), div(s"Random number: ${aa.i}")))
    }
    }, btn_danger, marginBottom := "20")

    lazy val theTabs = Tabs.tabs(isClosable = true).
      add(Tab(AA(4), span("My first"), div1, onClicked = () => println("My first clicked"))).
      add(Tab(AA(5), span("My second"), inputTag("Hey !"), onRemoved = () => println("5 is dead"))).build


    val toaster = toastStack(bottomRightPosition, unstackOnClose = true)
    val warningToast = toast(ToastHeader("Warning", backgroundColor = "#eee"), "Your tab is empty", delay = Some(2000))

    lazy val tabsObserver = Observer[Seq[Tab[AA]]] {(aas: Seq[Tab[AA]]) =>
      if (aas.isEmpty) toaster.stackAndShow(warningToast)
    }


    div(
      trigger,
      theTabs.render,
      theTabs.tabs--> tabsObserver,
      toaster.render
    )


  }


  val elementDemo = new ElementDemo {
    def title: String = "Tab"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
