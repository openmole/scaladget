package scaladget.api

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


import scalatags.JsDom
import scalatags.JsDom._
import scalatags.JsDom.all._
import org.scalajs.dom

package object svg {

  object path {
    implicit def pathToTypedTagPath(p: Path): TypedTag[dom.svg.Path] = p.render

      def start(x: Int, y: Int): Path = apply("").m(x, y)

      def apply(st: String) = new Path {
        val svgString = st
    }


    trait Path {
      def svgString: String

      def render: TypedTag[dom.svg.Path] = svgTags.path(svgAttrs.d := svgString)

      private def append(s: String): Path = path(svgString + s" $s")

      def m(x: Int, y: Int): Path = append(s"M $x $y")

      def l(x: Int, y: Int): Path = append(s"L $x $y")

      def h(y: Int): Path = append(s"H $y")

      def v(x: Int): Path = append(s"V $x")

      def c(x1: Int, y1: Int, x2: Int, y2: Int, x: Int, y: Int): Path = append(s"C $x1 $y1 $x2 $y2 $x $y")

      def q(x1: Int, y1: Int, x: Int, y: Int): Path = append(s"Q $x1 $y1 $x $y")

      def s(x2: Int, y2: Int, x: Int, y: Int): Path = append(s"S $x2 $y2 $x $y")

      def t(x: Int, y: Int): Path = append(s"T $x $y")

      def a(rx: Int, ry: Int, xAxisRotation: Int, largeArcFlag: Int, sweepFlag: Int, x: Double, y: Double) = append(s"A $rx $ry $xAxisRotation $largeArcFlag $sweepFlag $x $y")

      def z = append("Z")
    }

  }

}

