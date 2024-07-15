package demo

import scaladget.highlightjs.HighlightJS
import scaladget.bootstrapnative.bsn._
import com.raquo.laminar.api.L._
import org.scalajs

import scala.scalajs.js.annotation._

/*
 * Copyright (C) 24/03/16 // mathieu.leclaire@openmole.org
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
 */

object App {

  def main(args: Array[String]): Unit = {
    scaladget.highlightjs.scalamode
    HighlightJS.initHighlightingOnLoad()

    val demo = SVGStarDemo.elementDemo

    val content = div(
      h3(demo.title),
      div(containerFluid,
        div(row, marginLeft := "15", marginTop := "25",
          div(colBS(demo.codeWidth), pre(code(cls := "scala", demo.cleanCode))),
          div(colBS(12 - demo.codeWidth), demo.element)
        )
      )
    )

    documentEvents(_.onDomContentLoaded).foreach { _ =>
      render(scalajs.dom.document.body, content)
    }(unsafeWindowOwner)

  }
}

