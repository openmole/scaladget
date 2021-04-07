package demo


import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._


/*
 * Copyright (C) 22/08/16 // mathieu.leclaire@openmole.org
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

object SelectDemo {

  val sc = sourcecode.Text {
    import scaladget.bootstrapnative.Selector._

    // Define a toy case class containing at least a name attribute
    case class MyElement(name: String)

    // Define the option sequence
    val first = MyElement("First element")
    val second = MyElement("Second Element")
    val third = MyElement("Third Element")
    val elements = Seq(first, second, third)


    val selected: Var[MyElement] = Var(elements(1))

    lazy val optionDropDown: Options[MyElement] =
      elements.options(
        1,
        Seq(btn_success, width := "160"),
        (m: MyElement) => m.name,
        onclose = () => selected.set(optionDropDown.content.now.get),
        decorations = Map(first -> glyph_archive, second -> glyph_settings, third -> glyph_filter)
      )

    lazy val fixedTitleOptions: Options[MyElement] = elements.options(
      key = btn_danger,
      naming = (m: MyElement) => m.name,
      onclose = () => println(fixedTitleOptions.content.now.get),
      fixedTitle = Some("Actions")
    )


    hForm(padding := "5",
      optionDropDown.selector,
      fixedTitleOptions.selector,
      div(
        padding := "8",
        child.text <-- selected.signal.map(s => s"Selected:${s.name}")
      )
    )

  }


  val elementDemo = new ElementDemo {
    def title: String = "Dropdown"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }

}
