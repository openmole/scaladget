package fr.iscpif.scaladget
import org.querki.jquery._
import mapping.bootstrap.BootstrapStatic



package object bootstrap {
  implicit def jqueryToBootstrap(jq: JQuery): BootstrapStatic = jq.asInstanceOf[BootstrapStatic]
}