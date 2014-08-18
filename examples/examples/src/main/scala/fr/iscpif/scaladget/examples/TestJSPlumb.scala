/*
 * Copyright (C) 30/07/14 mathieu
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
package fr.iscpif.scaladget.examples

import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{literal => lit}
import rx._
import fr.iscpif.scaladget.jsplumb._
import org.scalajs.dom
import scalatags.JsDom.all._



@JSExport
object TestJSPlumb {

  case class Task(name: String)

  class MyWorkflowSettings extends WorkflowSettings {
    val defaults = lit(
      DragOptions = lit(cursor = "pointer", zIndex = 2000),
      ConnectionOverlays = js.Array(
        js.Array("Arrow", lit(location = 1)),
        js.Array("Label", lit(location = 0.1, id = "label", cssClass = "aLabel"))
      ),
      Container = "flowchart-demo"
    )

    override val connectPaintStyle = lit(
      lineWidth = 4,
      strokeStyle = "#61B7CF",
      joinstyle = "round",
      outlineColor = "white",
      outlineWidth = 2
    )

    override val connectHoverStyle = lit(
      lineWidth = 4,
      strokeStyle = "#216477",
      outlineWidth = 2,
      outlineColor = "white"
    )

    override val endPointHoverStyle = lit(
      fillStyle = "#216477",
      strokeStyle = "#216477"
    )

    override val sourcePoint = lit(
      endpoint = "Dot",
      paintStyle = lit(
        strokeStyle = "#7AB02C",
        fillStyle = "transparent",
        radius = 11,
        lineWidth = 5
      ),
      isSource = true,
      connector = js.Array(
        "Flowchart",
        lit(
          stub = js.Array(40, 60),
          gap = 10,
          cornerRadius = 5,
          alwaysRespectStubs = true
        )
      ),
      connectorStyle = connectPaintStyle,
      hoverPaintStyle = endPointHoverStyle,
      connectorHoverStyle = connectHoverStyle,
      dragOptions = lit(),
      overlays = js.Array(
        js.Array(
          "Label",
          lit(
            location = js.Array(0.5, 1.5),
            cssClass = "endpointSourceLabel"
          )
        )
      )
    )

    override val targetPoint = lit(
      endpoint = "Dot",
      paintStyle = lit(
        fillStyle = "#7AB02C",
        radius = 11
      ),
      hoverPaintStyle = endPointHoverStyle,
      maxConnections = -1,
      dropOptions = lit(
        hoverClass = "hover",
        activeClass = "active"
      ),
      isTarget = true,
      overlays = js.Array(
        js.Array(
          "Label",
          lit(
            location = js.Array(0.5, -0.5),
            cssClass = "endpointTargetLabel"
          )
        )
      )
    )


  }

  @JSExport
  def run() = {
    val flow = new FlowChart(new MyWorkflowSettings, Seq("Window2", "Window3"))
    //flow.addWindow("window1")
   // flow.addWindow("window4")
    //flow.addWindow("window5")
  }

/*
  @JSExport
  def addWindow(id: String, flow: FlowChart) = {
    val chartId = "flowchart" + toId

    dom.document.body.appendChild(
      div(id := "main",
        div(
          `class` := "demo flowchart-demo",
          id := "flowchart-demo",
          div(
            id := chartId,
            `class` := "window",
            strong("1")).render
        ).render
      ).render
    )


    flow.addPoint(id, flow)
  }*/
}
