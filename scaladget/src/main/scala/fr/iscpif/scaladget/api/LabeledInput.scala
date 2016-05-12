package fr.iscpif.scaladget.api

/*
 * Copyright (C) 12/05/16 // mathieu.leclaire@openmole.org
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

import fr.iscpif.scaladget.stylesheet.all
import fr.iscpif.scaladget.api.{ BootstrapTags ⇒ bs }
import scalatags.JsDom.all._
import all._


class LabeledInput(title: String, default: String, pHolder: String, inputWidth: Int, labelStyle: ModifierSeq) {


  val lab = label(pHolder)(labelStyle, `for` := pHolder).render

  val inp = bs.input(default)(
    id := pHolder,
    formControl,
    placeholder := pHolder,
    width := inputWidth
  ).render


  def render = div(formGroup)(
    lab,
    inp)

  def value = inp.value

}