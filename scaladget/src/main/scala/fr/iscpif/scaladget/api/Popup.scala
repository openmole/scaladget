package fr.iscpif.scaladget.api


import fr.iscpif.scaladget.stylesheet.all
import fr.iscpif.scaladget.tools.JsRxTags._
import org.scalajs.dom._
import scalatags.JsDom.TypedTag
import scalatags.JsDom.all._
import all._
import rx._


/*
 * Copyright (C) 02/05/16 // mathieu.leclaire@openmole.org
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

object Popup {

  sealed trait PopupPosition

  //object Left extends PopupPosition

  object Right extends PopupPosition

  //object Top extends PopupPosition

  object Bottom extends PopupPosition

  sealed trait PopupType

  object HoverPopup extends PopupType

  object ClickPopup extends PopupType

  object DialogPopup extends PopupType

  lazy val noArrow: ModifierSeq = Seq()
  lazy val whiteBottomArrow = arrow("white", Bottom)
  lazy val whiteRightArrow = arrow("white", Right)
  lazy val blackBottomArrow = arrow("#000", Bottom)
  lazy val blackRightArrow = arrow("#000", Right)
  lazy val greyBottomArrow = arrow("#333", Bottom)
  lazy val greyRightArrow = arrow("#333", Right)

  lazy val dialogStyle: ModifierSeq = ms("ooo")
  /*Seq(
      backgroundColor := "white",
      absolutePosition,
      top := -250,
      left := 0,
      width := "70%",
      height := 250,
      padding := 20,
      transition := "top 300ms cubic-bezier(0.17, 0.04, 0.03, 0.94)",
      boxSizing := "border-box"
    )*/

  def arrow(color: String, position: PopupPosition): ModifierSeq = {
    val transparent = "5px solid transparent"
    val solid = s"5px solid $color"
    Seq(
      width := 0,
      zIndex := 1002,
      height := 0) ++ {
      position match {
        case Bottom => Seq(
          borderLeft := transparent,
          borderRight := transparent,
          borderBottom := solid)
        case Right => Seq(
          borderTop := transparent,
          borderBottom := transparent,
          borderRight := solid)
      }
    }
  }

}

import Popup._

class Popup(val triggerElement: org.scalajs.dom.raw.HTMLElement,
            innerDiv: TypedTag[org.scalajs.dom.raw.HTMLElement],
            popupType: PopupType,
            direction: PopupPosition,
            popupStyle: ModifierSeq,
            arrowStyle: ModifierSeq,
            onclose: () => Unit = () => {},
            condition: () => Boolean = () => true) {

  val popupVisible = Var(false)

  Obs(popupVisible) {
    if (!popupVisible()) onclose()
  }

  val zPosition = zIndex := 1002

  lazy val popupPosition: ModifierSeq = {
    direction match {
      case Bottom => Seq(
        left := triggerElement.offsetLeft,
        top := triggerElement.offsetTop + triggerElement.offsetHeight + 5
      )

      case Right => Seq(
        left := triggerElement.offsetLeft + triggerElement.offsetWidth + 5,
        top := triggerElement.offsetTop
      )
    }
  } +++ zPosition +++ absolutePosition

  lazy val arrowPosition: ModifierSeq = {
    direction match {
      case Bottom =>
        Seq(
          left := (triggerElement.offsetWidth / 2 - 1 + triggerElement.offsetLeft).toInt,
          top := triggerElement.offsetTop + triggerElement.offsetHeight
        )
      case Right => Seq(
        left := (triggerElement.offsetLeft + triggerElement.offsetWidth).toInt,
        top := (triggerElement.offsetHeight / 2).toInt
      )
    }
  } +++ zPosition +++ absolutePosition


  triggerElement.style.setProperty("cursor", "pointer")
  popupType match {
    case HoverPopup =>
      triggerElement.onmouseover = (m: MouseEvent) => popupVisible() = true
      triggerElement.onmouseleave = (m: MouseEvent) => popupVisible() = false
    case _ =>
      triggerElement.onclick = (m: MouseEvent)=> popupVisible() = !popupVisible()
  }

  val popup = div(relativePosition)(
    triggerElement,
    Rx {
      if (popupVisible() && condition()) div(
        div(arrowStyle +++ arrowPosition),
        innerDiv(popupStyle +++ popupPosition)
      ).render
      else span(display := "none").render
    }).render

  def close = popupVisible() = false

}
