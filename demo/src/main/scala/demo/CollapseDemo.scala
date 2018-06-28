package demo

import org.scalajs.dom.Element
import scaladget.bootstrapnative.bsn._
import scaladget.tools._
import org.scalajs.dom.raw._
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._

/*
 * Copyright (C) 30/08/16 // mathieu.leclaire@openmole.org
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

object CollapseDemo extends Demo {


  val sc = sourcecode.Text {
    import rx._

    val onoff = Var(true)

    lazy val aDiv: HTMLDivElement = div(
      buttonIcon("Trigger !", btn_primary +++ (marginBottom := 10).toMS).expandOnclick(panel("My text in detail")(width := 400, height := 200)),
      button("Set Var", btn_danger, onclick := {()=> onoff() = !onoff.now}),
      onoff.expand(div("Yes", backgroundColor := "orange", height := 150)),

      button(btn_default, "Build", onclick := {() =>
        println("Build")
        aDiv.appendChild(
          onoff.expand(div("YAA", backgroundColor := "yellow", height := 300))
        )
      })
    ).render

    aDiv
  }

  val elementDemo = new ElementDemo {
    def title: String = "Collapse"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
