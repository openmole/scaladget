package fr.iscpif.demo


import fr.iscpif.scaladget.api.Selector
import fr.iscpif.scaladget.stylesheet.all
import org.scalajs.dom.Element
import org.scalajs.dom.raw.HTMLDivElement

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

import fr.iscpif.scaladget.api.{BootstrapTags => bs}
import fr.iscpif.scaladget.stylesheet.{all => sheet}
import scalatags.JsDom.all._
import sheet._
import bs._

object SelectDemo {

  val sc = sourcecode.Text {
    import fr.iscpif.scaladget.api.Selector._
    import fr.iscpif.scaladget.tools.JsRxTags._
    import rx._

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
        btn_success,
        (m: MyElement) => m.name,
        () => selected() = optionDropDown.content.now.get,
        decorations = Map(first -> glyph_fire, second -> glyph_settings, third -> glyph_flash)
      )

    lazy val fixedTitleOptions: Options[MyElement] = elements.options(
      key = btn_danger,
      naming = (m: MyElement) => m.name,
      onclose = () => println(fixedTitleOptions.content.now.get),
      fixedTitle = Some("Actions")
    )

    val loginInput = bs.input("")(placeholder := "Login")
    val passInput = bs.input("")(placeholder := "Login", `type` := "password")
    val build: Var[Option[Dropdown[HTMLDivElement]]] = Var(None)

    val formDropDown = bs.vForm(width := 200)(
        loginInput.render.withLabel("Login"),
        passInput.render.withLabel("Pass"),
        bs.button("OK", btn_primary, () => {
          build.now.foreach{_.close}
        }).render
      ).dropdown("Form", btn_primary, allModifierSeq = sheet.marginLeft(10), onclose = ()=> println("OK"))

    val formDropDown2 = bs.vForm(width := 200)(
      loginInput.render.withLabel("Login")
    ).dropdown(buttonIcon = glyph_settings, buttonModifierSeq = btn_default)

    val formDropDown3 = bs.vForm(width := 200)(
      loginInput.render.withLabel("Login")
    ).dropdownWithTrigger(bs.glyphSpan(glyph_refresh))

    div(
      hForm()(
        bs.button("build", ()=> build() = Some(formDropDown)).render,
        formDropDown2.render,
        formDropDown3.render,
        optionDropDown.selector.render,
        fixedTitleOptions.selector.render,
        vForm(width := 200)(loginInput.render,
          bs.button("OK", btn_primary, () => {
          build.now.foreach{_.close}
        }).render).dropdownWithTrigger(label("Drop", sheet.paddingTop(10), label_warning)).render,
        Rx{
          build().map{_.render}.getOrElse(div("rien").render)
        }
      ),
      Rx {
        div(s"Selected: ${selected()}")
      }
    ).render

  }


  val elementDemo = new ElementDemo {
    def title: String = "Dropdown"

    def code: String = sc.source

    def element: Element = sc.value
  }

}
