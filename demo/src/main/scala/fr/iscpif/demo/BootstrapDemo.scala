package demo

import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all => sheet}

import scala.scalajs.js.JSApp
import scala.scalajs.js.annotation.JSExport
import org.scalajs.dom

import scalatags.JsDom.tags
import scalatags.JsDom.all._
import sheet._
import bs._
import fr.iscpif.demo.{ModalDialogDemo, NavBarDemo, SelectDemo}

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
    for {
      demo <- Seq(ModalDialogDemo.elementDemo, SelectDemo.elementDemo, NavBarDemo.elementDemo)
    } yield {
      dom.document.body.appendChild(
        div( sheet.marginLeft(15), sheet.marginTop(25))(
          h3(demo.title),
          div(row)(
            div(colMD(6))(pre(code(toClass("scala"))(demo.cleanCode))),
            div(colMD(6))(demo.element)
          )
        )
      )
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
