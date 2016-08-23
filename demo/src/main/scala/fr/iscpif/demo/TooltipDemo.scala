package fr.iscpif.demo

import org.scalajs.dom.Element

/*
 * Copyright (C) 23/08/16 // mathieu.leclaire@openmole.org
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

object TooltipDemo extends Demo {
  val sc = sourcecode.Text {
    import fr.iscpif.scaladget.stylesheet.{all => sheet}
    import fr.iscpif.scaladget.api.{BootstrapTags => bs}
    import fr.iscpif.scaladget.api.Popup._
    import scalatags.JsDom.all._
    import sheet._
    import bs._

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      sheet.marginRight(5)
    )

    div(
      bs.button("Left", buttonStyle, () => {}).tooltip("Tooltip on left", Left),
      bs.button("Right", buttonStyle, () => {}).tooltip("Tooltip on right", Right),
      bs.button("Top", buttonStyle, () => {}).tooltip("Tooltip on top", Top),
      bs.button("Bottom", buttonStyle, () => {}).tooltip("Tooltip on bottom", Bottom)
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Tooltip"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
