package fr.iscpif.scaladget.mapping
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

import org.scalajs.jquery.JQuery

object Utils {
  implicit def jq2Select2Static(jq:JQuery):Select2Static = jq.asInstanceOf[Select2Static]
  implicit def jq2BoostrapStatic(jq:JQuery):BootstrapStatic = jq.asInstanceOf[BootstrapStatic]
}