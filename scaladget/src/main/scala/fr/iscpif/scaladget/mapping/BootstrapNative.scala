package bootstrapnative

/*
 * Copyright (C) 18/08/16 // mathieu.leclaire@openmole.org
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


import org.scalajs.dom.Element

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@JSImport("bootstrap.native", "Modal")
@js.native
class Modal(element: Element) extends js.Object {

  def open(): Unit = js.native
  def close(): Unit = js.native
}