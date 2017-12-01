package demo

import org.scalajs.dom

import scala.scalajs.js.annotation._
import scalatags.JsDom.all._
import scaladget.tools.stylesheet._

/*
 * Copyright (C) 03/04/17 // mathieu.leclaire@openmole.org
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

object FlowChartDemo {

  @JSExportTopLevel("flowchartDemo")
  def flowchartDemo(): Unit = {
    val nodes = Seq(
      Graph.task("one", 400, 600),
      Graph.task("two", 1000, 600),
      Graph.task("three", 400, 100),
      Graph.task("four", 1000, 100),
      Graph.task("five", 105, 60)
    )
    val edges = Seq(
      Graph.edge(nodes(0), nodes(1)),
      Graph.edge(nodes(0), nodes(2)),
      Graph.edge(nodes(3), nodes(1)),
      Graph.edge(nodes(3), nodes(2)))
    val window = new Window(nodes, edges)
  }

  val notesCSS: ModifierSeq = Seq(
    width := 350,
    height := "auto",
    fixedPosition,
    floatRight,
    right := 50,
    fontWeight := "bold",
    backgroundColor := "#ffdd55",
    padding := 20,
    top := 10,
    borderRadius := "5px"
  )

  val notes = div(
    notesCSS,
    "The demo provides with a small SVG graph editor based on the d3 library ",
    a(href := "https://bl.ocks.org/cjrd/6863459", target := "_blank")("http://bl.ocks.org/cjrd/6863459 "),
    "but with no D3.js at all.")(
    div(paddingTop := 10, "It's fully based on ",
      a(href := "https://github.com/lihaoyi/scalatags", target := "_blank")("scalatags "),
      ", ",
      a(href := "https://github.com/scala-js/scala-js-dom", target := "_blank")("scala-js-dom "),
      " and ",
      a(href := "https://github.com/openmole/scaladget", target := "_blank")("scaladget. "),
      "The full code can be found ",
      a(href := "https://github.com/openmole/scaladget/blob/master/demo/src/main/scala/fr/iscpif/demo/FlowChart.scala", target := "_blank")("here. "),
      "Try to:",
      ul(
        li("drag the nodes to move them"),
        li("shift-click on the graph to create a node"),
        li("shift-click on a node and then drag to another node to connect them with a directed edge"),
        li("click on any node or edge and press delete to remove them")
      )
    )
  )

  dom.document.body.appendChild(notes.render)
}