/*
 * Copyright (C) 18/06/14 Mathieu Leclaire
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

import fr.iscpif.scaladget.d3mapping.Selection

import scala.scalajs.js._
import annotation.JSExport
import fr.iscpif.scaladget.d3._
import fr.iscpif.scaladget.Form._
import fr.iscpif.scaladget.DomUtil._

@JSExport
object Accordion {

  @JSExport
  def run() {
    title("Accordion !")

    body
      .backgroundColor("#ff80b2")
      .color("#000")
      .h1.html("A Scaladget accordion example")

    accordion(body, 6,
      ("first", "First", (s: Selection) => {
      form(s).line.input("firstName", "First name", "", 6).input("name", "Name", "", 6)
    }.selection),
    ("second", "Second", (s: Selection) => {
      form(s).line.input("email", "Email", "", 6).selection
    }))
  }
}
