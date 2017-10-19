package fr.iscpif.scaladget

import scala.scalajs.js
import scala.scalajs.js.JSConverters._
import js.{JSArrayOps, |}

package object tools {

  type Datum = Int | Double

  implicit def arrayOfIntToArrayOfDatum(a: Array[Datum]): js.Array[Datum] = a.toJSArray
}