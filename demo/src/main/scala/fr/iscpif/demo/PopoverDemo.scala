package demo

/*
 * Copyright (C) 24/08/16 // mathieu.leclaire@openmole.org
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


import scaladget.stylesheet.{all => sheet}
import scaladget.api.{BootstrapTags => bs}

import scalatags.JsDom.all._
import sheet._
import bs._
import org.scalajs.dom._

object PopoverDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.api.Popup._

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      marginRight := 5
    )

    div(
      button("Left", buttonStyle).popover("Popover on hover on left", Left),
      button("Title", buttonStyle).popover("Popover on hover with Title", Top, title = Some("Pop title")),
      button("Dismissable", buttonStyle).popover("Dismissible Popover on hover with Title", Top, HoverPopup, Some("Pop title"), true),
      button("Left (click)", buttonStyle).popover("Popover on click on right", Left, ClickPopup),
      bs.input("")(width := 320, marginTop := 10, placeholder := "Bottom (click)").popover("Tooltip on click on bottom", Bottom, ClickPopup)
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Popover"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 9
  }
}