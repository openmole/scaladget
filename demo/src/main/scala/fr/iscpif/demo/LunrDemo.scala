package demo

import fr.iscpif.scaladget.mapping.lunr.{IIndexSearchResult, Index, Importedjs}
import fr.iscpif.scaladget.tools.JsRxTags._
import rx._
import scala.scalajs.js.Dynamic.{literal ⇒ lit}


import scala.scalajs.js.{JSON, JSApp}
import scala.scalajs.js.annotation.JSExport
import scalatags.JsDom.tags
import scalatags.JsDom.all._

/*
 * Copyright (C) 06/07/16 // mathieu.leclaire@openmole.org
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

@JSExport("LunrDemo")
object LunrDemo extends JSApp {

  @JSExport
  def main(): Unit = {

    val results: Var[Seq[IIndexSearchResult]] = Var(Seq())

    val index = Importedjs.lunr((i: Index) => {
      i.field("title", lit("boost" → 10).value)
      i.field("body", lit("boost" → 1).value)
      i.ref("id")
      i.add(lit(
        "title" -> "Scala-js",
        "body" -> "Arguably the most efficient way to learn new technology is to try it out yourself. Here we have collected a set of tutorials from simple to complete web app, to get you started right away!",
        "id" -> "www.google.com"
      ))
      i.add(lit(
        "title" -> "New York",
        "body" -> "New York set a major league record by winning five consecutive championships from 1949 to 1953, and appeared in the World Series nine times during the next 11 years. Despite management disputes, the team reached the World Series four times between 1976 and 1981, claiming the championship in 1977 and 1978.",
        "id" -> "openmole.org"
      ))
    })

    val result = index.search("new")

    results() = result.toSeq

    index.documentStore.get("openmole.org").toArray().foreach {
      println
    }

    org.scalajs.dom.document.body.appendChild(tags.div(
      color := "white",
      Rx {
        for {r <- results()} yield {
          tags.div(
            tags.span(
              tags.a(r.ref, cursor := "pointer", href := r.ref),
              r.score
            )
          )
        }
      }

    ).render)
  }
}
