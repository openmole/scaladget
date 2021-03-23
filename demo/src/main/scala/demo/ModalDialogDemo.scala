package demo

import scaladget.bootstrapnative.bsn
import scaladget.{bootstrapnative, tools}
import scaladget.tools.Stylesheet

/*
 * Copyright (C) 19/08/16 // mathieu.leclaire@openmole.org
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

import com.raquo.laminar.api.L._
import com.github.uosis.laminar.webcomponents.material._

object ModalDialogDemo extends Demo {

  val sc = sourcecode.Text {

    println("00")



//    lazy val dialog: Dialog.El = Dialog(
//      _.heading := "Header",
//      _.slots.default(div("My body")),
//      _.slots.primaryAction(dialogCloseTrigger),
//      _.slots.secondaryAction(dialogCloseTrigger),
//      _.onClosed --> { _ => println("Closed")},
//      _.onOpened --> { _=>  println("Opened")}
//    )

    lazy val dialog: Dialog.El = bsn.dialog("Header", div("body"), dialogCloseTrigger, dialogCloseTrigger, ()=> println("Opened"), ()=> println("closed"))

    def dialogCloseTrigger = Button(
      _.label := "Close",
      _ => onClick --> { _ =>
        dialog.ref.close()
      },
      _.raised := true,
    )

    def openAction = onClick --> { _ => dialog.ref.show()}

    val dialogTrigger = Button(
      _.label := "Modal !",
      _ => openAction
    )

    println("001")
    // Append header, body, footer elements
    // Build the dialog and the modal dialog
    val oo = span(
      dialog,
      dialogTrigger,
      span(bsn.glyph_settings, paddingLeft := "5", Stylesheet.pointer, openAction)
    )
    println("002")
    oo
  }


  val elementDemo = new ElementDemo {
    def title: String = "Modal"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }

}