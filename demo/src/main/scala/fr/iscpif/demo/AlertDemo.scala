package demo

/*
 * Copyright (C) 29/08/16 // mathieu.leclaire@openmole.org
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
import scaladget.tools.JsRxTags._
import org.scalajs.dom.raw.Element

import scalatags.JsDom.all._
import sheet._
import scaladget.api.Alert.ExtraButton
import scaladget.api.{BootstrapTags => bs}
import bs._

object AlertDemo extends Demo {


  val sc = sourcecode.Text {

    import rx._

    implicit val ctx: Ctx.Owner = Ctx.Owner.safe()

    trait Alert
    object Info extends Alert
    object Success extends Alert
    object Warning extends Alert
    object Danger extends Alert

    val triggers: Var[Seq[Alert]] = Var(Seq())

    def add(alert: Alert) = () => triggers() = triggers.now :+ alert

    def remove(alert: Alert) = () => triggers() = triggers.now.filterNot(_ == alert)

    def contains(alert: Alert) = triggers.map { t => t.contains(alert) }

    val buttonStyle: ModifierSeq = Seq(
      marginRight := 5,
      marginTop := 5
    )


    div(
      h4("No collapser"),
      bs.button("Info", buttonStyle +++ btn_info, add(Info)),
      bs.button("Warning", buttonStyle +++ btn_warning, add(Warning)),
      h4("With collapser", paddingTop := 30),
      contains(Success).expand(bs.button("Success", buttonStyle +++ btn_success, add(Success)),
        bs.successAlert("Success !", "Operation completed !", todocancel = remove(Success))()),
      contains(Danger).expand(bs.button("Danger", buttonStyle +++ btn_danger, add(Danger)),
        bs.dangerAlerts("Danger !", Seq("Operation 1 failed ", "Operation 2 failed"), todocancel = remove(Danger))(
          ExtraButton("Run", btn_danger, action = remove(Danger)))),
      div(padding := 10)(
        bs.infoAlert("Info !", "Operation completed !", contains(Info), todocancel = remove(Info))(
          ExtraButton("Build", btn_info, action = remove(Info)),
          ExtraButton("Abort", btn_default, action = remove(Info))),
        bs.warningAlert("Warning !", "Operation failed !", contains(Warning), todocancel = remove(Warning))()
      )
    ).render
  }

  val elementDemo = new ElementDemo {
    def title: String = "Alert"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 9
  }
}
