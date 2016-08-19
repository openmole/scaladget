package fr.iscpif.demo

import fr.iscpif.scaladget.api.BootstrapTags.ModalDialog
import fr.iscpif.scaladget.stylesheet.all._
import fr.iscpif.scaladget.api.{BootstrapTags => bs}

import org.scalajs.dom.Element
import org.scalajs.dom
import scalatags.JsDom.all._
import scalatags.JsDom.tags

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

object ModalDialogDemo extends Demo {

  val modalCode =
    """
  lazy val modalDialog: ModalDialog = bs.ModalDialog()

  modalDialog header bs.ModalDialog.headerDialogShell(div("Header"))
  modalDialog body bs.ModalDialog.bodyDialogShell(div("My body !"))
  modalDialog footer bs.ModalDialog.footerDialogShell(
    bs.buttonGroup()(
      tags.button(btn_info, "OK"),
      tags.button(btn_info, "Cancel")
    )
  )

  val modal = modalDialog.dialog
  val trigger = modalDialog.buttonTrigger("Modal !", btn_primary).render
    """

  lazy val modalDialog: ModalDialog = bs.ModalDialog()

  modalDialog header bs.ModalDialog.headerDialogShell(div("Header"))
  modalDialog body bs.ModalDialog.bodyDialogShell(div("My body !"))
  modalDialog footer bs.ModalDialog.footerDialogShell(
    bs.buttonGroup()(
      tags.button(btn_info, "OK"),
      tags.button(btn_info, "Cancel")
    )
  )

  val modal = modalDialog.dialog
  val modalElement = modalDialog.buttonTrigger("Modal !", btn_primary).render
  dom.document.body.appendChild(modal)


  val elementDemo = new ElementDemo {
    def code: String = modalCode

    def element: Element = modalElement
  }

}