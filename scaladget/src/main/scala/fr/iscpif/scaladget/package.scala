package fr.iscpif.scaladget
import org.querki.jquery._
import mapping.tooltipster._
import mapping.bootstrap.BootstrapStatic


import org.scalajs.dom.raw.HTMLDivElement


package object tooltipster {
    implicit def jq2Datepicker(jq:JQuery):Tooltipster = jq.asInstanceOf[Tooltipster]
    def toolTip(on: HTMLDivElement, options: TooltipsterOptionBuilder) = $(on).tooltipster(options)
}

package object bootstrap {
    implicit def jqueryToBootstrap(jq: JQuery): BootstrapStatic = jq.asInstanceOf[BootstrapStatic]
}
