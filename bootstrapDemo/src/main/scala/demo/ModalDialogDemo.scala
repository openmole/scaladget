package demo

import scaladget.bootstrapnative.bsn

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

object ModalDialogDemo extends Demo {

  val sc = sourcecode.Text {

      lazy val dialog: bsn.ModalDialog = bsn.modalDialog (
      div("Header"),
      div("body"),
      div(bsn.btnGroup, dialogCloseTrigger, button("OK", bsn.btn_success)),
      () => println("Opened"),
      () => println("closed")
    )

    def dialogCloseTrigger = button(
      "Close",
      bsn.btn_secondary,
      onClick --> { _ =>
        dialog.hide
      }
    )

    def openAction = onClick --> { _ => dialog.show }

    val dialogTrigger = button(
      bsn.btn_primary,
      "Modal !",
      openAction
    )

    span(
      dialog.render,
      dialogTrigger,
      span(bsn.glyph_settings, paddingLeft := "5", cursor.pointer, openAction)
    )
  }


  val elementDemo = new ElementDemo {
    def title: String = "Modal"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }

}