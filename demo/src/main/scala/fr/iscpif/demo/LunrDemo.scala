package demo

import fr.iscpif.scaladget.mapping.lunr.{IIndexSearchResult, Importedjs, Index}
import fr.iscpif.scaladget.tools.JsRxTags._
import org.scalajs.dom
import rx._

import scala.scalajs.js
import scala.scalajs.js.Dynamic.{literal => lit}
import scala.scalajs.js.{JSApp, JSON}
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

/*@JSExport("LunrDemo")
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

  case class Page(url: String, title: String, body: String)

}*/


@JSExport
object LunrDemo {
  val searchInput = input(placeholder:="Search").render
  val lunrIndex: Var[Option[Index]] = Var(None)
  val results: Var[Seq[IIndexSearchResult]] = Var(Seq())
  @JSExport
  def loadIndex(indexArray:js.Array[js.Any]): Unit = {
    val index = Importedjs.lunr((i: Index) => {
      i.field("title", lit("boost" → 10).value)
      i.field("body", lit("boost" → 1).value)
      i.ref("url")
      indexArray.foreach(p => {
        i.add(p)
      })
    })
    lunrIndex() = Some(index)
    val resultList = tags.div(
      color := "white",
      Rx {
        for {r <- results()} yield {
          tags.div(
            tags.span(
              tags.a(r.ref, cursor := "pointer", href := r.ref, target:="_blank"),
              r.score
            )
          )
        }
      }
    ).render
    dom.document.body.appendChild(
      tags.div(
      form(`type`:="submit", searchInput, onsubmit:={()=>
      search()
      false
      }), resultList).render)
  }

  @JSExport
  def search(): Unit = {
    println("search")
    results() = lunrIndex.now.map { i =>
      i.search(searchInput.value).toSeq
    }.getOrElse(Seq())
  }
}
