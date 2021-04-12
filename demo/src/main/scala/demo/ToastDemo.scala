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
import com.raquo.laminar.api.L._

object ToastDemo {
  val sc = sourcecode.Text {

    val myToast = toast(ToastHeader("My header", backgroundColor = "#ffc107"), "My important message", delay = Some(2000))
    val mySecondToast = toast(ToastHeader("My second header", backgroundColor = "#dc3545"), "My important message")
    val myThirdToast =  toast(ToastHeader("My third header", backgroundColor = "#17a2b8"), "My important message")
    val toaster = toastStack(bottomRightPosition)

    def stackAndShow (t: Toast) = {
      toaster.stack(t)
      t.show
    }

    div(
      button(btn_warning_outline, "Toast with delay", onClick --> { _ => stackAndShow(myToast) }),
      button(btn_danger_outline, marginLeft := "15", "Toast again !", onClick --> { _ => stackAndShow(mySecondToast) }),
      button(btn_info_outline, marginLeft := "15", "Toast again !", onClick --> { _ => stackAndShow(myThirdToast) }),
      toaster.render
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Toast"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}