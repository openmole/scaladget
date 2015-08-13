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

import fr.iscpif.scaladget.mapping._
import fr.iscpif.scaladget.mapping.ace._
import fr.iscpif.scaladget.mapping.tooltipster._
import org.scalajs.jquery.JQuery
import scala.scalajs.js

package object d3 extends js.GlobalScope {
  val d3: Base = js.native
}

package object bootstrap extends js.GlobalScope {
  val bootstrap: BootstrapStatic = js.native
  val modalOptions: ModalOptions = js.native
}

package object ace extends js.GlobalScope {
  val ace: Ace = js.native
  val autocomplete: AutoComplete = js.native
  var range: Range = js.native
}

package object tooltipster {
  implicit def jq2Datepicker(jq:JQuery):Tooltipster = jq.asInstanceOf[Tooltipster]
}