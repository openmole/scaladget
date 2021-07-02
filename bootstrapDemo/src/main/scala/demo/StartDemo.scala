package demo


import scaladget.highlightjs.HighlightJS
import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import org.scalajs


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

object App {

  def main(args: Array[String]): Unit = {


    scaladget.highlightjs.scalamode
    HighlightJS.initHighlightingOnLoad()

    def imports =
      """
      import scaladget.bootstrapnative.bsn._
      import scaladget.tools._
      import scalatags.JsDom.all._
      """.stripMargin

    // val element = AceDemo.elementDemo
    // println("Element " + element)
    lazy val content = {
      div(containerFluid, marginLeft := "15", marginTop := "25",
        h3("Build"),
        div(row,
          div(colSM, "Details on construction on ", a(href := "https://github.com/openmole/scaladget", target := "_blank", "the Scaladet Github page"))
        ),
        h3("Imports"),
        div(row,
          div(colSM, pre(code(cls("scala"), imports))),
          div(colSM, padding := "20", "This imports have to be done before using the following examples. Specific imports will be also sometimes specified.")
        ),
        //  FormDemo.elementDemo.element
        Tabs.tabs(
          for {
            //          demo <- Seq(
            //            //            AceDiffDemo.elementDemo,
            //            //            SliderDemo.elementDemo,

            demo <- Seq(
              AceDemo.elementDemo,
              LabelDemo.elementDemo,
              ButtonDemo.elementDemo,
              ModalDialogDemo.elementDemo,
              CollapseDemo.elementDemo,
              FormDemo.elementDemo,
              SelectDemo.elementDemo,
              PopoverDemo.elementDemo,
              TabDemo.elementDemo,
              TooltipDemo.elementDemo,
              NavBarDemo.elementDemo,
              ToastDemo.elementDemo,
              TableDemo.elementDemo,
              TestDemo.elementDemo
            )
          } yield {
            Tab(demo, span(demo.title),
              div(
                h3(demo.title),
                div(containerFluid,
                  div(row, marginLeft := "15", marginTop := "25",
                    div(colBS(demo.codeWidth), pre(code(cls := "scala", demo.cleanCode))),
                    div(colBS(12 - demo.codeWidth), demo.element)
                  )
                )
              )
            )
          },
          tabStyle = navbar_pills).build.render
      )

    }

    documentEvents.onDomContentLoaded.foreach { _ =>
      render(scalajs.dom.document.body, content)
    }(unsafeWindowOwner)
  }
}

