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
  def form(parent: Selection) = {
    val root = parent.div.clazz("container")
      .div.clazz("row")
      .div.clazz("col-xs-12 col-md-8 col-md-6")
      .form.role("form")
      .style("font-size", "13")
      .div.clazz("well")
    new Form(root, root)
  }

  def accordion(selection: Selection, width: Int, components: Tuple3[String, String, Selection => Selection]*): Selection = {
    val root = selection.div.clazz("container").div.clazz("row"). /*div.clazz("col-md-1"+ {if(width<=12) width else 12}).*/ div.clazz("panel-group").id("accordion")
    components.foreach { case (id, name, f) =>
      val paneldefault = root.div.clazz("panel panel-default")
      paneldefault.div.clazz("panel-heading").h4.clazz("panel-title").a.datatoggle("collapse").dataparent("#accordion").href("#" + id).html(name)
      val bodypanel = paneldefault.div.id(id).clazz("panel-collapse collapse in")
      f(bodypanel)
    }
    root
  }

  def accordion(selection: Selection) = {
    val root = selection.div.clazz("container").div.clazz("row").div.clazz("panel-group").id("accordion")

  }
}