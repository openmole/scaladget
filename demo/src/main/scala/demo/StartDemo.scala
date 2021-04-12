package demo


import scaladget.highlightjs.HighlightJS
import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import org.scalajs

import scala.scalajs.js.annotation._

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
              ToastDemo.elementDemo
            )

            //            //            TableDemo.elementDemo,
                        //            //            AlertDemo.elementDemo,
            //            //            PlayGroundDemo.elementDemo
            //          )
          } yield {
            //          println("TITLE " + demo.title)
            //          demo.element
            Tab(demo.title,
              div(
                h3(demo.title),
                div(containerFluid,
                  div(row, marginLeft := "15", marginTop := "25",
                 //   div(colBS(demo.codeWidth), pre(code(cls := "scala", demo.cleanCode))),
                    div(colBS(12 /*- demo.codeWidth*/), demo.element)
                  )
                )
              )
            )
            ////            ))
          }).build.render
      )

    }

    documentEvents.onDomContentLoaded.foreach { _ =>
      render(scalajs.dom.document.body, content)
    }(unsafeWindowOwner)
  }

  //    @JSExportTopLevel("flowchart")
  //    def flowchart(): Unit = {
  //      val nodes = Seq(
  //        Graph.task("one", 400, 600),
  //        Graph.task("two", 1000, 600),
  //        Graph.task("three", 400, 100),
  //        Graph.task("four", 1000, 100),
  //        Graph.task("five", 105, 60)
  //      )
  //      val edges = Seq(
  //        Graph.edge(nodes(0), nodes(1)),
  //        Graph.edge(nodes(0), nodes(2)),
  //        Graph.edge(nodes(3), nodes(1)),
  //        Graph.edge(nodes(3), nodes(2)))
  //      val window = new Window(nodes, edges)
  //
  //
  //      val notesCSS: ModifierSeq = Seq(
  //        width := 350,
  //        height := "auto",
  //        fixedPosition,
  //        floatRight,
  //        right := 50,
  //        fontWeight := "bold",
  //        backgroundColor := "#ffdd55",
  //        padding := 20,
  //        top := 10,
  //        borderRadius := "5px"
  //      )
  //
  //      val notes = div(
  //        notesCSS,
  //        "The demo provides with a small SVG graph editor based on the d3 library ",
  //        a(href := "https://bl.ocks.org/cjrd/6863459", target := "_blank")("http://bl.ocks.org/cjrd/6863459 "),
  //        "but with no D3.js at all.")(
  //        div(paddingTop := 10, "It's fully based on ",
  //          a(href := "https://github.com/lihaoyi/scalatags", target := "_blank")("scalatags "),
  //          ", ",
  //          a(href := "https://github.com/scala-js/scala-js-dom", target := "_blank")("scala-js-dom "),
  //          " and ",
  //          a(href := "https://github.com/openmole/scaladget", target := "_blank")("scaladget. "),
  //          "The full code can be found ",
  //          a(href := "https://github.com/openmole/scaladget/blob/master/demo/src/main/scala/fr/iscpif/demo/FlowChart.scala", target := "_blank")("here. "),
  //          "Try to:",
  //          ul(
  //            li("drag the nodes to move them"),
  //            li("shift-click on the graph to create a node"),
  //            li("shift-click on a node and then drag to another node to connect them with a directed edge"),
  //            li("click on any node or edge and press delete to remove them")
  //          )
  //        )
  //      )
  //
  //      dom.document.body.appendChild(notes.render)
}

//    @JSExportTopLevel("svg")
//    def svg(): Unit = {
//      val content = div(
//        for {
//          demo <- Seq(
//            SVGStarDemo.elementDemo
//          )
//        } yield {
//          div(marginLeft := 15, marginTop := 25)(
//            h3(demo.title),
//            div(row)(
//              div(colMD(demo.codeWidth))(pre(code(toClass("scala"))(demo.cleanCode))),
//              div(colMD(12 - demo.codeWidth))(demo.element)
//            )
//          )
//        }
//      )
//      dom.document.body.appendChild(content)
//
//    }

