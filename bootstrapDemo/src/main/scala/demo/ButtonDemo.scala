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

import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

object ButtonDemo {
  val sc = sourcecode.Text {

    val clicked = Var("None")

    val buttonStyle = Seq(
      marginRight := "5",
      marginTop := "5"
    )


    def clickAction(tag: String) = onClick --> { _ => clicked.set(tag) }

    val yes = ToggleState("Yes", btn_primary_string, ()=> println("YES"))
    val no = ToggleState("No", btn_secondary_string, ()=> println("NO"))
    val male = ToggleState("Male", btn_primary_string, ()=> println("MALE"))
    val female = ToggleState("Female", btn_primary_string, ()=> println("FEMALE"))
    val pizza = ToggleState("Pizza", btn_primary_string, ()=> println("PIZZA"))
    val couscous = ToggleState("Couscous", btn_primary_string, ()=> println("COUSCOUS"))

    lazy val exRadio = exclusiveRadio(Seq(male,female, ToggleState("Other", btn_primary_string, ()=> println("OTHER"))), btn_secondary_string, male)
    lazy val unique = toggle(yes, true, no, ()=> {println("toggled")})
    lazy val theRadio = radio(Seq(pizza, couscous, ToggleState("Falafel", btn_primary_string, ()=> println("FALAFEL"))), Seq(pizza, couscous), btn_secondary_string)

    div(
      h4("Buttons"),
      button("Primary", clickAction("primary"), span(glyph_edit, paddingLeft := "10"), buttonStyle, btn_primary),
      button("Default", clickAction("default"), buttonStyle, btn_secondary),
      button("Info", clickAction("info"), buttonStyle, btn_info),
      button("Default", clickAction("default"), buttonStyle, btn_secondary_outline),
      button("Primary", clickAction("primary"), buttonStyle, btn_primary_outline),
      button("Info", clickAction("info"), buttonStyle, btn_info_outline),
      button("Success", clickAction("success"), buttonStyle, btn_success),
      button("Warning", clickAction("warning"), buttonStyle, btn_warning),
      button("Danger", clickAction("danger"), buttonStyle, btn_danger),

      button(" Like", clickAction("danger"), buttonStyle, fontSize := "16", btn_danger, glyph_heart, clickAction("heart")),
      button("", clickAction("danger"), buttonStyle, fontSize := "20", btn_secondary, glyph_download, clickAction("download")),
      linkButton("GitHub", "https://github.com/openmole/scaladget", Seq(buttonStyle, btn_primary)),
      div(paddingTop := "15", child.text <-- clicked.signal.map { s => s"Clicked: ${s}" }),
      h4("Badges", paddingTop := "30"),
      button("Badge", clickAction("badge"), buttonStyle, btn_primary, badge("7", backgroundColor := "#31508c")),
      div("Badge", clickAction("badge"), badge("7", Seq(backgroundColor := "#31508c", color := "white"))),
      h4("Icon buttons", paddingTop := "30"),
      glyphSpan(Seq(glyph_refresh, buttonStyle, fontSize := "20"), clickAction("refresh")),
      glyphSpan(Seq(glyph_lightning, buttonStyle,  fontSize := "20"), clickAction("flash")),
      h4("Radio", paddingTop := "30"),
      unique.element,
      h4("Exclusive radio buttons", paddingTop := "30"),
      exRadio,
      h4("Toggle buttons", paddingTop := "30"),
      theRadio
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Button"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
