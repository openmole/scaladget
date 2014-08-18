/*
 * Copyright (C) 09/07/14 mathieu
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
package fr.iscpif.scaladget.examples

import org.scalajs.dom
import scalatags.JsDom._
import all._
import rx._
import fr.iscpif.scaladget.tools.JsRxTags._
import scala.scalajs.js.annotation.JSExport
import scala.Some

case class Person(firstName: Var[String], name: Var[String])

@JSExport
object TestScalaTags {


  val persons = Var(
    Seq(
      Person(Var("Joe"), Var("Dalton")),
      Person(Var("Jack"), Var("Dalton"))
    )
  )

  val nbPerson = Rx {
    persons().length
  }

  val firstNameInput = input(
    id := "new-todo",
    placeholder := "First Name",
    autofocus := true
  ).render

  val nameInput = input(
    id := "new-todo",
    placeholder := "Name",
    autofocus := true
  ).render

  val emailInput = input(
    id := "new-todo",
    placeholder := "Email",
    autofocus := true
  ).render

  val submitButton = button("Save")(
    `class` := "btn btn-primary btn-lg",
    // `type` := "button",
    cursor := "pointer",
    onclick := { () =>
      persons() = Person(Var(firstNameInput.value), Var(nameInput.value)) +: persons()
      firstNameInput.value = ""
      nameInput.value = ""
      false
    }
  )

  val simpleButton = button("Simple")(
    `class` := "btn btn-default btn-lg"
    //`type` := "button"
  )

  @JSExport
  def run() {
    dom.document.body.appendChild(form(
      div(id := "green")(firstNameInput, nameInput),
      div(id := "green")(emailInput, label("une label")),
      div(`class` := "btn-group")(submitButton, simpleButton),
      Rx {
        ul(id := "todo-list")(
          for (person <- persons()) yield {
            li(input(
              `class` := "toggle",
              `type` := "checkbox",
              cursor := "pointer"
            ),
              label(person.firstName() + " " + person.name()),
              button(
                `class` := "destroy",
                cursor := "pointer",
                onclick := { () => persons() = persons().filter(_ != person)}
              )
            )
          }
        )
      }
    ).render)

    dom.document.body.appendChild(
      footer(id := "info")(
        p(Rx {
          nbPerson() + " persons"
        })
      ).render
    )
  }
}
