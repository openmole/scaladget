package scaladget.svg

/*
 * Copyright (C) 26/03/16 // mathieu.leclaire@openmole.org
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

import org.scalajs.dom
import org.scalajs.dom.raw.Node

import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import scalatags.JsDom._
import scaladget.tools._

object path {
  implicit def pathToTypedTagPath(p: Path): TypedTag[dom.svg.Path] = p.render

  implicit def pathToNode(p: Path): Node = p().render

  def start(x: Int, y: Int): Path = Path("").m(x, y)

//  def apply(st: String = "", ms: ModifierSeq = emptyMod, precisionPattern: String = "") = new Path {
//    val svgString = st
//    val modifierSeq = ms
//    val precision = precisionPattern
//  }

  type PathOperator = String
  val M: PathOperator = "M"
  val L: PathOperator = "L"
  val H: PathOperator = "H"
  val V: PathOperator = "V"
  val C: PathOperator = "C"
  val Q: PathOperator = "Q"
  val S: PathOperator = "S"
  val T: PathOperator = "T"
  val A: PathOperator = "A"
  val Z: PathOperator = "Z"


  // presicion: Ex: 1.5f)
  case class Path(svgString: String = "", precisionPattern: String = "") {

    def render: TypedTag[dom.svg.Path] = svgTags.path(svgAttrs.d := svgString)

    def expand(value: Double) = {
      if (precisionPattern.isEmpty) value
      else precisionPattern.format(value)
    }

    private def append(s: String): Path = copy(svgString = svgString + s" $s")

    def m(x: Double, y: Double): Path = append(s"$M ${expand(x)} ${expand(y)}")

    def l(x: Double, y: Double): Path = append(s"$L ${expand(x)} ${expand(y)}")

    def h(y: Double): Path = append(s"$H ${expand(y)}")

    def v(x: Double): Path = append(s"$V ${expand(x)}")

    def c(x1: Double, y1: Double, x2: Double, y2: Double, x: Double, y: Double): Path = append(s"$C ${expand(x1)} ${expand(y)}1 ${expand(x1)} ${expand(y)}2 ${expand(x)} ${expand(y)}")

    def q(x1: Double, y1: Double, x: Double, y: Double): Path = append(s"$Q ${expand(x1)} ${expand(y1)} ${expand(x)} ${expand(y)}")

    def s(x2: Double, y2: Double, x: Double, y: Double): Path = append(s"$S ${expand(x2)} ${expand(y2)} ${expand(x)} ${expand(y)}")

    def t(x: Double, y: Double): Path = append(s"$T ${expand(x)} ${expand(y)}")

    def a(rx: Double, ry: Double, xAxisRotation: Double, largeArcFlag: Double, sweepFlag: Double, x: Double, y: Double) = append(s"$A ${expand(rx)} ${expand(ry)} ${expand(xAxisRotation)} $largeArcFlag $sweepFlag ${expand(x)} ${expand(y)}")

    def z = append("Z")
  }

}

