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
package fr.iscpif.scaladget

import fr.iscpif.scaladget.d3mapping.{Selection, Base}
import scala.scalajs.js
import DomUtil._

package object d3 extends js.GlobalScope {
  val d3: Base = ???
}

package object widget {
  def form(parent: Selection, id: String) = {
    val root = parent.div.clazz("container").id(id)
      .div.clazz("row")
      .div.clazz("col-xs-12 col-md-8 col-md-6")
      .form.role("form")
      .style("font-size", "13")
      .div.clazz("well")
    new Form(root, root, id)
  }

  def accordion(parent: Selection, id: String) = {
    val root = parent.div.clazz("container").div.clazz("row").div.clazz("panel-group").id("accordion")
    new Accordion(root,root, id)
  }
}