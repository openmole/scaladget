package demo

/*
 * Copyright (C) 11/10/17 // mathieu.leclaire@openmole.org
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

import com.karasiq.highlightjs.HighlightJS
import org.scalajs.dom.Element

import scala.scalajs.js
import scalatags.JsDom.all._
import scaladget.tools.JsRxTags._
import rx._


object SliderDemo extends Demo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapslider._

    val sliderValue = Var("None")
    val myDiv = input(paddingTop := 100).render
    org.scalajs.dom.document.body.appendChild(myDiv)

    val options = SliderOptions
      .max(100)
      .min(0.0)
      .value(js.Array[scala.Double](14.0, 92.0))
      .tooltip(SliderOptions.ALWAYS)

    val slider = new Slider(myDiv, options._result)
    slider.on(Slider.STOP_SLIDER, () => {
      sliderValue() = slider.getValue.toString
    })

    // Trick for the demo. Otherwise, you only need to add myDiv input wherever you want in the dom
    val o = div(
      org.scalajs.dom.document.getElementsByClassName("slider slider-horizontal")(0),
      Rx {
        sliderValue()
      }
    ).render

    HighlightJS.initHighlightingOnLoad()

    o
  }


  val elementDemo = new ElementDemo {
    def title: String = "Slider"

    def code: String = sc.source

    def element: Element = sc.value
  }
}
