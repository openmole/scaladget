package scaladget.bootstrapnative

import org.scalajs.dom.raw.HTMLElement
import com.raquo.laminar.api.L._

trait JSDependency{
  def path: String
}

  object JSDependency {

    lazy val BOOTSTRAP_NATIVE = new JSDependency{ def path = "js/bootstrap-native.min.js" }


    def withBootstrapNative[T <: HTMLElement](f: => T): Unit = withJS(BOOTSTRAP_NATIVE)(f)

    def withJS[T <: HTMLElement](js: JSDependency*)(f: => T): Unit = {
      org.scalajs.dom.document.body.appendChild(f)
      for {
        j <- js
      } yield {
        org.scalajs.dom.document.body.appendChild(
          script(`type` := "text/javascript", src := j.path).ref
        )
      }
    }
  }