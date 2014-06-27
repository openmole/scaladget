/*
 * Copyright (C) 12/06/14 Mathieu Leclaire
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
package fr.iscpif.scaladget.examples

import scala.scalajs.js._
import annotation.JSExport
import fr.iscpif.scaladget.d3._
import fr.iscpif.scaladget.Form._
import fr.iscpif.scaladget.DomUtil._
import fr.iscpif.scaladget.widget._

@JSExport
object Form {

  @JSExport
  def run() {
    title("My first html page aieth Scala !")

    body
      .backgroundColor("#d4d4d4")
      .color("#000")
      .h1.html("A Scaladget form example").center

    form(body)
      .line.input("name", "Name", "", 6).input("email", "Email", "", 6)
      .line.input("firstname", "First Name", "")
      .group.button("save", "Save", State.PRIMARY).button("cancel", "Cancel")
  }
}


