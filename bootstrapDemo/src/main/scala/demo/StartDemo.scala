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
        //  FormDemo.elementDemo.element
        Tabs.tabs(
          for {
            demo <- Seq(
              ExecutionDemo.elementDemo,
              //              LabelDemo.elementDemo,
              //              ModalDialogDemo.elementDemo,
              //              CollapseDemo.elementDemo,
              //              FormDemo.elementDemo,
              //              SelectDemo.elementDemo,
              //              PopoverDemo.elementDemo,
              //              TabDemo.elementDemo,
              //              TooltipDemo.elementDemo,
              //              NavBarDemo.elementDemo,
              //              ToastDemo.elementDemo,
              TableDemo.elementDemo,
              ButtonDemo.elementDemo,
              //              SliderDemo.elementDemo,
              //              AceDemo.elementDemo,
              //              PlayGround.elementDemo,
            )
          } yield {
            Tab(demo, span(demo.title),
              div(
                h3(demo.title),
                div(containerFluid,
                  div(row, marginLeft := "15", marginTop := "25",
                    demo.element
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

