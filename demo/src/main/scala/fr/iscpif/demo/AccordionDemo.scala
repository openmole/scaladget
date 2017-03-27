package fr.iscpif.demo

/*
 * Copyright (C) 30/09/16 // mathieu.leclaire@openmole.org
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
import org.scalajs.dom.raw.Element

import scalatags.JsDom.all._
import sheet._
import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import bs._


object AccordionDemo extends Demo {


  val sc = sourcecode.Text {

    val titleStyle: ModifierSeq = Seq(
      color := "#3086b5",
      fontWeight := "bold"
    )

    bs.accordion(margin := 10)(titleStyle)(
      accordionItem("First Title", div("This is my life")),
      accordionItem("Second Title", div("This is my second life")),
      accordionItem("Third Title", div("This is my third life"))
    ).render
  }


  val elementDemo = new ElementDemo {
    def title: String = "Alert"

    def code: String = sc.source

    def element: Element = sc.value

    override def codeWidth: Int = 7
  }
}
