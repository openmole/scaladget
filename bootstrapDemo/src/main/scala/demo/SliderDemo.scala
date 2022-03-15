package demo

/*
 * Copyright (C) 23/08/16 // mathieu.leclaire@openmole.org
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

import com.raquo.laminar.api.L._
import scaladget.nouislider._
import scaladget.nouislider.NoUISliderImplicits._
import scala.scalajs.js

object SliderDemo {
  val sc = sourcecode.Text {

    val elStyle = margin := "60 0 60 0"
    val el1 = div(elStyle)
    val el2 = div(elStyle)
    val el3 = div(elStyle, height := "150", idAttr := "s3")

    noUiSlider.create(
      el1.ref, Options
        .range(Range.min(1300).max(3250))
        .start(1450)
        .step(100)
        .connect(Options.Lower)
        .tooltips(true)
    )

    noUiSlider.create(
      el2.ref, Options
        .range(Range.min(1300.45).max(3250.5))
        .start(js.Array(1450.0, 1800.0))
        .connect(js.Array(true, false, true))
        .tooltips(js.Array(true, true))
    )

    noUiSlider.create(
      el3.ref, Options
        .range(Range.min(0).max(10))
        .start(5)
        .connect(Options.Lower)
        .orientation(Options.Vertical)
        .tooltips(true)
    )

    el2.noUiSlider.on(event.ChangeEvent, (value, handle) => println("val : " + value + " on " + handle ))

    el2.ref.noUiSlider.set(js.Array(2000.0, 2500.0))

    println("2 " + el2.noUiSlider.get())


    div(el1, el2, el3)
  }

  val elementDemo = new ElementDemo {
    def title: String = "Slider"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
