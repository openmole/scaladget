package demo

/*
 * Copyright (C) 12/12/18 // mathieu.leclaire@openmole.org
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


object SVGGridDemo {

  import scalatags.JsDom.svgTags
  import scalatags.JsDom.svgAttrs._
  import scala.scalajs.js.annotation.JSExportTopLevel
  import scalatags.JsDom.implicits._

  object Color {

    val coldColor = (255, 238, 170)
    val hotColor = (255, 100, 0)

    val baseR = (hotColor._1 - coldColor._1)
    val baseG = (hotColor._2 - coldColor._2)
    val baseB = (hotColor._3 - coldColor._3)

    def color(value: Double) = (
      (baseR * value + coldColor._1).toInt,
      (baseG * value + coldColor._2).toInt,
      (baseB * value + coldColor._3).toInt
    )
  }


  @JSExportTopLevel("grid")
  def grid(): Unit = {
    lazy val rng = scala.util.Random
    def randomDoubles(nb: Int = 100) = Seq.fill(nb)(rng.nextDouble)

    val gridSize = 1000
    val nbCellsByDimension = 50

    val cellDimension = gridSize.toDouble / nbCellsByDimension
    val values = (1 to nbCellsByDimension).foldLeft(Seq[Seq[Double]]())((elems, _) => elems :+ randomDoubles(nbCellsByDimension))

    val scene = svgTags.svg(
      width := gridSize,
      height := gridSize
    ).render

    for {
      col <- (0 to nbCellsByDimension - 1).toArray
      val colCoord = (col * cellDimension) + 1
      row <- (0 to nbCellsByDimension - 1).toArray
    } yield {
      scene.appendChild(
        svgTags.rect(x := ((row * cellDimension) + 1), y := colCoord, width := cellDimension, height := cellDimension,
          style := s"fill:rgb${Color.color(values(row)(col))};").render
      )
    }

    org.scalajs.dom.document.body.appendChild(scene)

  }
}
