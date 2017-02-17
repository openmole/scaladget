package fr.iscpif.demo

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


import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.api.{BootstrapTags => bs}

import scalatags.JsDom.all._
import sheet._
import bs._
import org.scalajs.dom._

object PopoverDemo extends Demo {
  val sc = sourcecode.Text {

    import fr.iscpif.scaladget.api.Popup._
    import fr.iscpif.scaladget.tools.JsRxTags._
    import rx._

    val buttonStyle: ModifierSeq = Seq(
      btn_default,
      sheet.marginRight(5)
    )


    val newPop: Var[Option[Popover]] = Var(None)

    div(
      bs.button("Build new Popover", buttonStyle, ()=> { newPop() = Some(new Popover(bs.button("NEW Pop !", buttonStyle, ()=> {}),
        "Pop life", dismissible = true, title = Some("My title")))}),
      bs.button("Left", buttonStyle, () => {}).popover("Popover on hover on left", Left),
      bs.button("Title", buttonStyle, () => {}).popover("Popover on hover with Title", Top, title = Some("Pop title")),
      bs.button("Dismissable", buttonStyle, () => {}).popover("Dismissible Popover on hover with Title", Top, HoverPopup, Some("Pop title"), true),
      bs.button("Left (click)", buttonStyle, () => {}).popover("Popover on click on right", Left, ClickPopup),
      bs.input("")(width := 320, sheet.marginTop(10), placeholder := "Bottom (click)").popover("Tooltip on click on bottom", Bottom, ClickPopup),
      Rx {
        newPop().map{_.render}.getOrElse(div("Nothing").render)
      }
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Popover"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 9
  }
}