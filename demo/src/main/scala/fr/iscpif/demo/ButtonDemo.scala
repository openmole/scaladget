package fr.iscpif.demo

import fr.iscpif.scaladget.api.{ButtonCheckBox, CheckBoxes}
import org.scalajs.dom._

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

import fr.iscpif.scaladget.stylesheet.{all => sheet}
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.tools.JsRxTags._
import scalatags.JsDom.all._
import sheet._

object ButtonDemo {
  val sc = sourcecode.Text {

    import rx._

    val clicked = Var("None")

    val buttonStyle: ModifierSeq = Seq(
      sheet.marginAll(right = 5, top = 5)
    )

    def clickAction(tag: String) = clicked() = tag

    lazy val checkBoxes: CheckBoxes = bs.checkboxes()(
      bs.buttonCheckbox("Piano", onclick = checkAction),
      bs.buttonCheckbox("Guitar", onclick = checkAction),
      bs.buttonCheckbox("Bass", true, onclick = checkAction)
    )

    lazy val active: Var[Seq[ButtonCheckBox]] = Var(checkBoxes.active)
    def checkAction = () => active() = checkBoxes.active

    div(
      h4("Buttons"),
      bs.button("Default", buttonStyle +++ btn_default, () => clickAction("default")),
      bs.button("Primary", buttonStyle +++ btn_primary, () => clickAction("primary")),
      bs.button("Info", buttonStyle +++ btn_info, () => clickAction("info")),
      bs.button("Success", buttonStyle +++ btn_success, () => clickAction("success")),
      bs.button("Warning", buttonStyle +++ btn_warning, () => clickAction("warning")),
      bs.button("Danger", buttonStyle +++ btn_danger, () => clickAction("danger")),
      Rx {
        div(sheet.paddingAll(top = 15), s"Clicked: ${clicked()}")
      },
      h4("Badges", sheet.paddingTop(30)),
      bs.badge("Badge", "7", buttonStyle +++ btn_primary, () => clickAction("badge")),

      h4("Check boxes", sheet.paddingTop(30)),
      checkBoxes.render,
      Rx {
        div(sheet.paddingAll(top = 15), s"Active: ${
          active().map {
            _.text
          }.toSeq
        }")
      }


    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Button"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
