package scaladget

import rx.Ctx
import scalatags.JsDom.all.s

package object tools extends Utils with Stylesheet with JsRxTags{

  override val ctx: Ctx.Owner = Ctx.Owner.safe()

  type ID = String

  def uuID: ID = java.util.UUID.randomUUID.toString

  implicit class ShortID(id: ID) {
    def short: String = id.split('-').head

    def short(prefix: String): String = s"$prefix$short"
  }
}
