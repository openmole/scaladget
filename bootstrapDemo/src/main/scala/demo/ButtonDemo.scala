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

    sealed trait Food
    object Couscous extends Food
    object Falafel extends Food
    object Pizza extends Food

    val yes = ToggleState("Yes","Yes", btn_primary_string, ()=> println("YES"))
    val no = ToggleState("No","No", btn_secondary_string, ()=> println("NO"))
    val yesOutline = ToggleState("Yes","Yes", btn_outline_primary_string, ()=> println("YES"))
    val noOutline = ToggleState("No","No", btn_outline_secondary_string, ()=> println("NO"))
    val male = ToggleState("Male","Male", btn_danger_string, ()=> println("MALE"))
    val female = ToggleState("Male","Female", btn_danger_string, ()=> println("FEMALE"))
    val pizza = ToggleState[Food](Pizza,"Pizza", btn_primary_string, ()=> println("PIZZA"))
    val couscous = ToggleState[Food](Couscous,"Couscous", btn_primary_string, ()=> println("COUSCOUS"))
    val falafel = ToggleState[Food](Falafel, "Falafel", btn_primary_string, ()=> println("FALAFEL"))

    lazy val exRadio = exclusiveRadio(Seq(male,female, ToggleState("Other", "Other", btn_danger_string, ()=> println("OTHER"))), btn_secondary_string, 0).element
    lazy val unique = toggle(yes, true, no, ()=> {println("toggled")})
    lazy val uniqueSquared = toggle(yesOutline, true, noOutline, ()=> {println("toggled")}, withCaret = false)
    lazy val theRadio = radio[Food](Seq(pizza, couscous, falafel), Seq(pizza, couscous), btn_secondary_string)
    val only2 = exclusiveRadios[Food](Seq(pizza, couscous, falafel), btn_secondary_string, Seq(0,2)).element
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
      uniqueSquared.element.amend(width := "50", height := "50", marginLeft := "20"),
      h4("2 exclusive radio buttons", paddingTop := "30"),
      only2,
      h4("Exclusive radio buttons", paddingTop := "30"),
      exRadio,
      h4("Toggle buttons", paddingTop := "30"),
      theRadio,
      h4("Check button"),
      checkbox(true)
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Button"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
