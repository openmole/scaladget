package scaladget

import rx.Ctx

package object tools extends Utils with Stylesheet with JsRxTags{

  override val ctx: Ctx.Owner = Ctx.Owner.safe()
}
