/*
 * Copyright (C) 28/07/14 mathieu.leclaire@openmole.org
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
package fr.iscpif.scaladget.jsplumb

import scala.scalajs.js
import js.Dynamic.{literal => lit}

trait WorkflowSettings {
  def defaults: js.Dynamic
  val connectPaintStyle: js.Dynamic = lit()
  val connectHoverStyle: js.Dynamic = lit()
  val endPointHoverStyle: js.Dynamic = lit()
  val sourcePoint: js.Dynamic = lit()
  val targetPoint: js.Dynamic = lit()
}
