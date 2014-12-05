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
package fr.iscpif.scaladget.mapping

import scala.scalajs.js

trait ModalOptions extends js.Object {
  var backdrop: Boolean = ???
  var keyboard: Boolean = ???
  var show: Boolean = ???
  var remote: String = ???
}

trait ModalOptionsBackdropString extends js.Object {
  var backdrop: String = ???
  var keyboard: Boolean = ???
  var show: Boolean = ???
  var remote: String = ???
}

trait ScrollSpyOptions extends js.Object {
  var offset: Double = ???
}

trait TooltipOptions extends js.Object {
  var animation: Boolean = ???
  var html: Boolean = ???
  var placement: js.Any = ???
  var selector: String = ???
  var title: js.Any = ???
  var trigger: String = ???
  var delay: js.Any = ???
  var container: js.Any = ???
}

trait PopoverOptions extends js.Object {
  var animation: Boolean = ???
  var html: Boolean = ???
  var placement: js.Any = ???
  var selector: String = ???
  var trigger: String = ???
  var title: js.Any = ???
  var content: js.Any = ???
  var delay: js.Any = ???
  var container: js.Any = ???
}

trait CollapseOptions extends js.Object {
  var parent: js.Any = ???
  var toggle: Boolean = ???
}

trait CarouselOptions extends js.Object {
  var interval: Double = ???
  var pause: String = ???
}

trait TypeaheadOptions extends js.Object {
  var source: js.Any = ???
  var items: Double = ???
  var minLength: Double = ???
  var matcher: js.Function1[js.Any, Boolean] = ???
  var sorter: js.Function1[js.Array[js.Any], js.Array[js.Any]] = ???
  var updater: js.Function1[js.Any, Any] = ???
  var highlighter: js.Function1[js.Any, String] = ???
}

trait AffixOptions extends js.Object {
  var offset: js.Any = ???
}

trait BootstrapStatic extends js.Object {
  def modal(): BootstrapStatic = ???
  def modal(options: ModalOptions = ???): BootstrapStatic = ???
  def modal(command: String): BootstrapStatic = ???
  def dropdown(): BootstrapStatic = ???
  def dropdown(command: String): BootstrapStatic = ???
  def scrollspy(command: String): BootstrapStatic = ???
  def scrollspy(options: ScrollSpyOptions = ???): BootstrapStatic = ???
  def tab(): BootstrapStatic = ???
  def tab(command: String): BootstrapStatic = ???
  def tooltip(options: TooltipOptions = ???): BootstrapStatic = ???
  def tooltip(command: String): BootstrapStatic = ???
  def popover(options: PopoverOptions = ???): BootstrapStatic = ???
  def popover(command: String): BootstrapStatic = ???
  def alert(): BootstrapStatic = ???
  def alert(command: String): BootstrapStatic = ???
  def button(): BootstrapStatic = ???
  def button(command: String): BootstrapStatic = ???
  def collapse(options: CollapseOptions = ???): BootstrapStatic = ???
  def collapse(command: String): BootstrapStatic = ???
  def carousel(options: CarouselOptions = ???): BootstrapStatic = ???
  def carousel(command: String): BootstrapStatic = ???
  def typeahead(options: TypeaheadOptions = ???): BootstrapStatic = ???
  def affix(options: AffixOptions = ???): BootstrapStatic = ???
}

