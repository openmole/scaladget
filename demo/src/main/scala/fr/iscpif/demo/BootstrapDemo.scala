package demo

import fr.iscpif.scaladget.api.Select.SelectElement
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.tools.JsRxTags._
import fr.iscpif.scaladget.api.Popup._
import fr.iscpif.scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import scalatags.JsDom.{styles => sty}
import sheet._
import bs._
import fr.iscpif.demo.{ModalDialogDemo, SelectDemo}
import org.scalajs.dom.raw.Event
import rx._

/*
 * Copyright (C) 24/03/16 // mathieu.leclaire@openmole.org
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


@JSExport("Demo")
object BootstrapDemo extends JSApp {

  @JSExport()
  def main(): Unit = {
    Seq(ModalDialogDemo.elementDemo, SelectDemo.elementDemo).foreach { demo =>
      dom.document.body.appendChild(
        div(relativePosition)(
          div(colMD(8))(pre(code(toClass("scala"))(demo.cleanCode))),
          div(colMD(4))(demo.element)
        ))
    }
  }

  @JSExport()
  def loadBootstrap(): Unit = {
    dom.document.body.appendChild(tags.script(`type` := "text/javascript", src := "js/bootstrap-native.min.js"))
  }


  @JSExport()
  def highlight(): Unit = {
    dom.document.body.appendChild(tags.script("hljs.initHighlighting();"))
  }
}
