package fr.iscpif.demo

import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import bs._
import org.scalajs.dom.raw.Element
import sheet._
import scalatags.JsDom.all._

/*
 * Copyright (C) 30/08/16 // mathieu.leclaire@openmole.org
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

object CollapseDemo extends Demo {


  val sc = sourcecode.Text {

    bs.button("Trigger !", btn_primary).expandOnclick(bs.panel("My panel")(width := 400))
  }

  val elementDemo = new ElementDemo {
    def title: String = "Collapse"

    def code: String = sc.source

    def element: Element = sc.value.render
  }
}
