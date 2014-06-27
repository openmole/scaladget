/*
 * Copyright (C) 27/06/14 mathieu
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

import fr.iscpif.scaladget.d3mapping.Selection
import DomUtil._

protected case class Accordion(root: Selection, selection: Selection, id: String) extends WComposer {
  implicit def selectionToForm(s: Selection): Accordion = this

  def tab(id: String, name: String, f: Selection=> Selection): Accordion = {
      val paneldefault = root.div.clazz("panel panel-default")
      paneldefault.div.clazz("panel-heading").h4.clazz("panel-title").a.datatoggle("collapse").dataparent("#accordion").href("#" + id).html(name)
      val bodypanel = paneldefault.div.id(id).clazz("panel-collapse collapse in")
      copy(selection = f(bodypanel))
  }
}
