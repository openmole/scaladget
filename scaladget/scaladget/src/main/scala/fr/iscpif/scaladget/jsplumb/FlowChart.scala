/*
 * Copyright (C) 28/07/14 mathieu
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

package fr.iscpif.scaladget.jsplumb

import org.scalajs.dom
import scalatags.JsDom.all._

import scala.scalajs.js
import js.Dynamic.{literal => lit}

class FlowChart(settings: WorkflowSettings, tasks: Seq[String]) {


  val mainDiv = div(id := "main").render

  val flowChartDiv = div(
    `class` := "demo flowchart-demo",
    id := "flowchart-demo"
  ).render

  val (firstWindow, firstWindowId) = {
    val id = tasks.head
    val fW = windowDiv(id, "flowchart" + id)
    flowChartDiv.appendChild(fW)
    (fW, id)
  }

  def windowDiv(ID: String, chartId: String) = div(
    id := chartId,
    `class` := "window",
    strong(ID)
  ).render

  val jsplumb = js.Dynamic.global.jsPlumb


  mainDiv.appendChild(flowChartDiv)
  dom.document.body.appendChild(mainDiv)

  jsplumb.ready { () =>

  val plumbInstance = jsplumb.getInstance(
    settings.defaults
  )

    plumbInstance.doWhileSuspended(() => {
      tasks.foreach {
        addPoint
      }
      plumbInstance.bind("connection", (conInfo: js.Dynamic, _: js.Dynamic) => {
        init(conInfo.connection)
      })

      plumbInstance.draggable(jsplumb.getSelector(".flowchart-demo .window"), lit(
        grid = js.Array(20, 20)
      ))

    })

    def init(connection: js.Dynamic) = {
      connection.getOverlay("label").setLabel(connection.sourceId.substring(15) + "-" + connection.targetId.substring(15))
      connection.bind("editCompleted", (o: js.Dynamic) => {
        println("connection edited. Path is now " + o.path)
      })
    }

    def addPoint(toId: String) = {
      val chartId = "flowchart" + toId
      if (toId != firstWindowId) {
        flowChartDiv.insertBefore(windowDiv(toId, chartId), firstWindow)
      }

      val sourceUUID = toId + "RightMiddle"
      plumbInstance.addEndpoint(chartId, settings.sourcePoint, lit(anchor = "RightMiddle", uuid = sourceUUID))

      val targetUUID = toId + "LeftMiddle"
      plumbInstance.addEndpoint(chartId, settings.targetPoint, lit(anchor = "LeftMiddle", uuid = targetUUID))
    }

    jsplumb.fire("workfow loaded", plumbInstance)
  }


}