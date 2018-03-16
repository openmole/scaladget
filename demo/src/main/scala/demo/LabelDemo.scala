package demo

import org.scalajs.dom._

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
import org.scalajs.dom.Element

import scaladget.bootstrapnative.bsn._
import scaladget.tools._

import org.scalajs.dom.raw._
import scalatags.JsDom.all._

object LabelDemo {
  val sc = sourcecode.Text {
    import rx._

    val hovered = Var("None")
    val labelStyle: ModifierSeq = Seq(
      marginTop := 20
    )

    def overAction(tag: String) = onmouseover := { () =>  hovered() = tag }

    div(row)(
      label("Default", label_default, overAction("default")).size4(labelStyle),
      label("Primary", label_primary, overAction("primary")).size4(labelStyle),
      label("Info", label_info,  overAction("info")).size4(labelStyle),
      label("Success", label_success, overAction("success")).size4(labelStyle),
      label("Warning", label_warning, overAction("warning")).size5(labelStyle),
      label("Danger", label_danger, overAction("danger")).size6(labelStyle),
      Rx{
        div(paddingTop := 15, s"Hovered: ${hovered()}")
      }
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Label"

    def code: String = sc.source

    def element: Element = sc.value
  }
}