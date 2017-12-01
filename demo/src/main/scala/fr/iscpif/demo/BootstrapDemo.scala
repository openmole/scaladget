package demo

import scaladget.bootstrapnative.{JSDependency}
import scala.scalajs.js.annotation._
import scaladget.bootstrapnative.all._
import scaladget.tools.stylesheet._
import scalatags.JsDom.all._
import org.scalajs.dom

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
 */

@JSExportTopLevel(name = "sjs.demo.BootstrapDemo") @JSExportAll
object BootstrapDemo {

  def runn(): Unit = {

    def imports =
      """
      import scaladget.stylesheet.{all => sheet}
      import scaladget.api.{BootstrapTags => bs}
      import scalatags.JsDom.all._
      import sheet._
      import bs._
      """.stripMargin

    JSDependency.withJS(JSDependency.BOOTSTRAP_NATIVE) {
      div(div(marginLeft := 15, marginTop := 25)(
        h3("Build"),
        div(row)(
          div(colMD(12))("Details on construction on ", a(href := "https://github.com/openmole/scaladget", target := "_blank")("the Scaladet Github page"))
        ),
        h3("Imports"),
        div(row)(
          div(colMD(8))(pre(code(toClass("scala"))(imports))),
          div(colMD(4), padding := 20)("This imports have to be done before using the following examples. Specific imports will be also sometimes specified.")
        )
      ),
        for {
          demo <- Seq(
          //  SliderDemo.elementDemo,
            ButtonDemo.elementDemo,
            LabelDemo.elementDemo,
            FormDemo.elementDemo,
            SelectDemo.elementDemo,
            ModalDialogDemo.elementDemo,
            TabDemo.elementDemo,
            TableDemo.elementDemo,
            NavBarDemo.elementDemo,
            TooltipDemo.elementDemo,
            PopoverDemo.elementDemo,
            CollapseDemo.elementDemo,
            AlertDemo.elementDemo
          )
        } yield {
          div(marginLeft := 15, marginTop := 25)(
            h3(demo.title),
            div(row)(
              div(colMD(demo.codeWidth))(pre(code(toClass("scala"))(demo.cleanCode))),
              div(colMD(12 - demo.codeWidth))(demo.element)
            )
          )
        }
      ).render
    }
    dom.document.body.appendChild(scalatags.JsDom.tags.script("hljs.initHighlighting();").render)
  }
}
