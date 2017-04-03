package demo

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.JSApp

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

@JSExport("demo.FlowchartDemo")
object FlowChartDemo extends JSApp {

  @JSExport()
  def main(): Unit = {
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
}