package demo

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

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

    val onoff: Var[Option[Int]] = Var(None)
    val onoffBottom: Var[Boolean] = Var(false)

    def onoffUpdate(currentId: Option[Int], id: Int) = {
      if (Some(id) == currentId) None
      else Some(id)
    }

    def expandAction(i: Option[Int], butId: Int) = {
      i match {
        case Some(ii: Int) =>
          if (ii == butId) true
          else false
        case None => false
      }
    }

    div(
      button(" Trigger !", btn_primary, marginBottom := "10", glyph_settings).expandOnclick(
        div(child.text <-- onoff.signal.map(oo => "My text in detail " + oo)).amend(width := "400", height := "200")
      ),
      button("Set Var", btn_danger, onClick --> { _ => onoff.update(i => onoffUpdate(i, 1)) }),
      onoff.signal.map(oo => expandAction(oo, 1)).expand(div("Yes", backgroundColor := "orange", height := "150")),
      button("Set Var", btn_danger, onClick --> { _ => onoff.update(i => onoffUpdate(i, 2)) }),
      onoff.signal.map(oo => expandAction(oo, 2)).expand(div("Yes", backgroundColor := "orange", height := "150")),
      button("Set Var", btn_danger, onClick --> { _ => onoffBottom.update(!_) }),
      div("Yes", backgroundColor := "orange", cls <-- onoffBottom.signal.map{oo=> if (oo) "collapse-bottom" else "collapse-bottom close"})
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Collapse"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
