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

//import scaladget.bootstrapnative.ToggleButton
import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._

object ButtonDemo {
  val sc = sourcecode.Text {

    import scaladget.bootstrapnative.{SelectableButton, SelectableButtons}
    val clicked = Var("None")

    val buttonStyle = Seq(
      marginRight := "5",
      marginTop := "5"
    )


    val active: Var[Seq[SelectableButton]] = Var(Seq())
    def clickAction(tag: String) = onClick --> { _ => clicked.set(tag) }
    def checkAction = onClick --> { _ => active.set(checkBoxes.active) }
    def radioAction = onClick --> { _=> active.set(theRadios.active) }

    lazy val checkBoxes: SelectableButtons = checkboxes(
      selectableButton("Piano", false, checkAction, btn_danger),
      selectableButton("Guitar", false, checkAction),
      selectableButton("Bass", true, checkAction)
    )

    lazy val theRadios: SelectableButtons = radios()(
      selectableButton("Male", false, radioAction),
      selectableButton("Female", true, radioAction)
    )

//    lazy val toggleButton: ToggleButton = toggle(true, "Yes", "No", () => {
//      println("Position " + toggleButton.position.now)
//    })

    div(
      h4("Buttons"),
      button("Default", clickAction("default"), buttonStyle, btn_default),
      button("Primary", clickAction("primary"), buttonStyle, btn_primary),
      button("Info", clickAction("info"), buttonStyle, btn_info),
      button("Success", clickAction("success"), buttonStyle, btn_success),
      button("Warning", clickAction("warning"), buttonStyle, btn_warning),
      button("Danger", clickAction("danger"), buttonStyle, btn_danger),
      buttonIcon("Default", buttonStyle, btn_default :+ glyph_fire, clickAction("fire")),
      buttonIcon("", buttonStyle, Seq(btn_danger, glyph_download), clickAction("download")),
      linkButton("GitHub", "https://github.com/openmole/scaladget", Seq(buttonStyle, btn_primary)),
      div(paddingTop := "15", child.text <-- clicked.signal.map { s => s"Clicked: ${s}" }),
      h4("Badges", paddingTop := "30"),
      button("Badge", clickAction("badge"), buttonStyle, btn_primary, badge("7", backgroundColor := "pink")),
      div("Badge", clickAction("badge"), badge("7", Seq(backgroundColor := "yellow", color := "#ccc"))),
      h4("Icon buttons", paddingTop := "30"),
      glyphSpan(Seq(glyph_refresh, buttonStyle), clickAction("refresh")),
      glyphSpan(Seq(glyph_flash, buttonStyle), clickAction("flash")),
      h4("Check boxes", paddingTop := "30"),
      checkBoxes.render,
      h4("Radio buttons", paddingTop := "30"),
      theRadios.render,
      //      Rx {
      //        div(paddingTop := 15, s"Active: ${
      //          active().map {
      //            _.text
      //          }.toSeq
      //        }")
      //      },
      h4("Toggle buttons", paddingTop := "30"),
    //  toggleButton.render
    )
  }

  val elementDemo = new ElementDemo {
    def title: String = "Button"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
