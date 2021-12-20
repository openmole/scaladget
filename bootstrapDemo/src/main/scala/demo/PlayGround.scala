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


object PlayGround {
  val sc = sourcecode.Text {

    val onoff = Var(-1)

    def fileTable(rows: Seq[(Seq[HtmlElement], Int)]) = {
      div(
        rows.map { case (r, id) ⇒
          div(display.flex, flexDirection.column, width:= "200px",
            div(display.flex, alignItems.center,
              r.zipWithIndex.map { case (e, c) ⇒
                e.amend(cls := s"col$c")
              }
            ),
            onoff.signal.map { i => i == id }.expand(div(s"Yes $id", backgroundColor := "orange", height := "50"))
          )
        }
      )
    }

    def dirBox = {
      div(cls := "dir",
        div(cls := "plus bi-plus")
      )
    }


    def gear(id: Int) = div(cls := "bi-gear-fill", cursor.pointer, onClick --> { _ =>
      onoff.set(
        if (id == onoff.now()) -1
        else id
      )
    })

    val dirs = Seq("one", "two", "trestrestrestrestrestrestrestrestrestreslong")


    fileTable(dirs.zipWithIndex.map { case (d, id) =>
      (Seq(div(dirBox), div(d), div("4.00KB"), gear(id)), id)
    })
  }

  val elementDemo = new ElementDemo {
    def title: String = "Playground"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}