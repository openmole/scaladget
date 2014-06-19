/*
 * Copyright (C) 12/06/14 Mathieu Leclaire
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
package fr.iscpif.scaladget

import d3.d3._
import d3mapping.Selection

object DomUtil {
  implicit def selectionToDomUtil(s: Selection): DomUtil = new DomUtil(s)

  def body: Selection = select("body")

  def title(t: String): Selection = select("head").append("title").html(t)
}

class DomUtil(val selection: Selection) {

  private def append(value: String) = selection.append(value)

  def div: Selection = append("div")

  def form: Selection = append("form")

  def input: Selection = append("input")

  def button: Selection = append("button")

  def ul: Selection = append("ul")

  def h1: Selection = append("h1")

  def h2: Selection = append("h2")

  def h3: Selection = append("h3")

  def h4: Selection = append("h4")

  def a: Selection = append("a")

  private def attr(key: String, value: String) = selection.attr(key, value)

  def clazz(value: String): Selection = attr("class", value)

  def role(value: String): Selection = attr("role", value)

  def tyype(value: String): Selection = attr("type", value)

  def id(value: String): Selection = attr("id", value)

  def placeholder(value: String): Selection = attr("placeholder", value)

  def value(value: String): Selection = attr("value", value)

  def href(value: String): Selection = attr("href", value)

  def datatoggle(value: String): Selection = attr("data-toggle", value)

  def dataparent(value: String): Selection = attr("data-parent", value)

  private def style(key: String, value: String) = selection.style(key, value)

  def color(value: String) = style("color", value)

  def backgroundColor(value: String) = style("background-color", value)

  def center: Selection = attr("align","center")
}