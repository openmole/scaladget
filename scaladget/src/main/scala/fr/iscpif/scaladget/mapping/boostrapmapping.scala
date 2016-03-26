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
package fr.iscpif.scaladget.mapping.bootstrap

import scala.scalajs.js
import org.querki.jquery._
import js.annotation._

@js.native
trait ModalOptions extends js.Object {
  var backdrop: Boolean = js.native
  var keyboard: Boolean = js.native
  var show: Boolean = js.native
  var remote: String = js.native
}

@js.native
trait ModalOptionsBackdropString extends js.Object {
  var backdrop: String = js.native
  var keyboard: Boolean = js.native
  var show: Boolean = js.native
  var remote: String = js.native
}

@js.native
trait ScrollSpyOptions extends js.Object {
  var offset: Double = js.native
}

@js.native
trait TooltipOptions extends js.Object {
  var animation: Boolean = js.native
  var html: Boolean = js.native
  var placement: js.Any = js.native
  var selector: String = js.native
  var title: js.Any = js.native
  var trigger: String = js.native
  var delay: js.Any = js.native
  var container: js.Any = js.native
}

@js.native
trait PopoverOptions extends js.Object {
  var animation: Boolean = js.native
  var html: Boolean = js.native
  var placement: js.Any = js.native
  var selector: String = js.native
  var trigger: String = js.native
  var title: js.Any = js.native
  var content: js.Any = js.native
  var delay: js.Any = js.native
  var container: js.Any = js.native
}

@js.native
trait CollapseOptions extends js.Object {
  var parent: js.Any = js.native
  var toggle: Boolean = js.native
}

@js.native
trait CarouselOptions extends js.Object {
  var interval: Double = js.native
  var pause: String = js.native
}

@js.native
trait TypeaheadOptions extends js.Object {
  var source: js.Any = js.native
  var items: Double = js.native
  var minLength: Double = js.native
  var matcher: js.Function1[js.Any, Boolean] = js.native
  var sorter: js.Function1[js.Array[js.Any], js.Array[js.Any]] = js.native
  var updater: js.Function1[js.Any, Any] = js.native
  var highlighter: js.Function1[js.Any, String] = js.native
}

@js.native
trait AffixOptions extends js.Object {
  var offset: js.Any = js.native
}

@js.native
trait BootstrapStatic extends js.Object {
  def modal(): BootstrapStatic = js.native
  //def modal(options: ModalOptions = js.native): BootstrapStatic = js.native
  def modal(options: ModalOptionsBackdropString = js.native): BootstrapStatic = js.native
  def modal(command: String): BootstrapStatic = js.native
  def dropdown(): BootstrapStatic = js.native
  def dropdown(command: String): BootstrapStatic = js.native
  def scrollspy(command: String): BootstrapStatic = js.native
  def scrollspy(options: ScrollSpyOptions = js.native): BootstrapStatic = js.native
  def tab(): BootstrapStatic = js.native
  def tab(command: String): BootstrapStatic = js.native
  def tooltip(options: TooltipOptions = js.native): BootstrapStatic = js.native
  def tooltip(command: String): BootstrapStatic = js.native
  def popover(options: PopoverOptions = js.native): BootstrapStatic = js.native
  def popover(command: String): BootstrapStatic = js.native
  def popover(): BootstrapStatic = js.native
  def alert(): BootstrapStatic = js.native
  def alert(command: String): BootstrapStatic = js.native
  def button(): BootstrapStatic = js.native
  def button(command: String): BootstrapStatic = js.native
  def collapse(options: CollapseOptions = js.native): BootstrapStatic = js.native
  def collapse(command: String): BootstrapStatic = js.native
  def carousel(options: CarouselOptions = js.native): BootstrapStatic = js.native
  def carousel(command: String): BootstrapStatic = js.native
  def typeahead(options: TypeaheadOptions = js.native): BootstrapStatic = js.native
  def affix(options: AffixOptions = js.native): BootstrapStatic = js.native
}

