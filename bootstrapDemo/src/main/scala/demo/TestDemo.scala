package demo

/*
 * Copyright (C) 05/01/17 // mathieu.leclaire@openmole.org
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

object TestDemo extends Demo {
  val sc = sourcecode.Text {


    case class AA(id: String,
                  title: HtmlElement,
                  active: Boolean = false)


    case class AAs(initialAAs: Seq[AA]) {

      val elements = Var(initialAAs)

      def setActive(id: String) = {
//        val ind = elements.now().zipWithIndex.find{case (e,i)=> e.id == id}.map(_._2)
//
//        ind.foreach {i=>
//          elements.update(e=> e.updated(i, e(i).copy(active = true)))
//        }

        elements.update(es=> es.map{e=> e.copy(active = e.id == id)})
      }

      def remove(id: String) = {
        elements.update(e=> e.filterNot(_.id == id))

        if(elements.now().length > 0 && elements.now().map{_.active}.forall(_ == false))
          elements.update{e=>
            e.head.copy(active = true) +: e.tail
          }
      }

      def renderTab(id: String, initialTab: AA, tabStream: Signal[AA]) = {

        li(
          initialTab.title,
          backgroundColor <-- tabStream.map { t => if (t.active) "red" else "white" },
          color <-- tabStream.map { t => if (t.active) "white" else "black" },
          cursor.pointer,
          onClick --> (_=> setActive(id)),
          span("X", cursor.pointer, onClick--> (_=> remove(id)))
        )
      }


      def render = {
        ul(children <-- elements.signal.split(_.id)(renderTab))
      }
    }

    AAs(Seq(AA("One", div("One"), true), AA("Two", div("Two")), AA("Three", div("Thre")), AA("Four", div("Four")), AA("Five", div("Five")))).render

  }


  val elementDemo = new ElementDemo {
    def title: String = "Tab"

    def code: String = sc.source

    def element: HtmlElement = sc.value
  }
}
