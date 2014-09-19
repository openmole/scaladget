/*
 * Copyright (C) 30/07/14 mathieu.leclaire@openmole.org
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

import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.annotation.JSExport
import fr.iscpif.scaladget.d3.chart._
import js.Dynamic.{literal => lit}
import rx._


@JSExport
object TestSvg {

  @JSExport
  def run() = {
    // val scene = new Scene
    // scene.addRect(200,150,200,200)
    val nodes = scala.Array(
      new Task("1",Var("one"),Var((400,600))),
      new Task("2",Var("two"),Var((1500,600))),
      new Task("3",Var("three"),Var((400,100))),
      new Task("4",Var("four"),Var((1500,100))),
      new Task("5",Var("five"),Var((105,60)))
    )
    val edges = scala.Array(new Edge(Var(nodes(0)),Var(nodes(1))),new Edge(Var(nodes(0)),Var(nodes(2))),new Edge(Var(nodes(3)),Var(nodes(1))))
    val window = new Window(nodes,edges)
  }
}