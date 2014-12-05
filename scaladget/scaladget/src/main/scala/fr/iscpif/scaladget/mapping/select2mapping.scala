package fr.iscpif.scaladget.mapping

import scala.scalajs.js
import js.annotation._
import org.scalajs.jquery.{JQuery, JQueryXHR, JQueryAjaxSettings, JQueryEventObject}
import org.scalajs.dom.Node



trait Select2QueryOptions extends js.Object {
  var term: String = ???
  var page: Double = ???
  var context: js.Any = ???
  var callback: js.Function1[js.Any, Unit] = ???
}

trait AjaxFunction extends js.Object {
  def apply(settings: JQueryAjaxSettings): JQueryXHR = ???
  def apply(url: String, settings: JQueryAjaxSettings = ???): JQueryXHR = ???
}

trait Select2AjaxOptions extends js.Object {
  var transport: AjaxFunction = ???
  var url: js.Any = ???
  var dataType: String = ???
  var quietMillis: Double = ???
  var data: js.Function3[String, Double, js.Any, Any] = ???
  var results: js.Function3[js.Any, Double, js.Any, Any] = ???
}

trait IdTextPair extends js.Object {
  var id: js.Any = ???
  var text: String = ???
}

trait Select2Options extends js.Object {
  var width: String = ???
  var dropdownAutoWidth: Boolean = ???
  var minimumInputLength: Double = ???
  var minimumResultsForSearch: Double = ???
  var maximumSelectionSize: Double = ???
  var placeholder: String = ???
  var separator: String = ???
  var allowClear: Boolean = ???
  var multiple: Boolean = ???
  var closeOnSelect: Boolean = ???
  var openOnEnter: Boolean = ???
  var id: js.Function1[js.Any, String] = ???
  var matcher: js.Function3[String, String, js.Any, Boolean] = ???
  var formatSelection: js.Function3[js.Any, JQuery, js.Function1[String, String], String] = ???
  var formatResult: js.Function4[js.Any, JQuery, js.Any, js.Function1[String, String], String] = ???
  var formatResultCssClass: js.Function1[js.Any, String] = ???
  var formatNoMatches: js.Function1[String, String] = ???
  var formatSearching: js.Function0[String] = ???
  var formatInputTooShort: js.Function2[String, Double, String] = ???
  var formatSelectionTooBig: js.Function1[Double, String] = ???
  var formatLoadMore: js.Function1[Double, String] = ???
  var createSearchChoice: js.Function2[String, js.Any, Any] = ???
  var initSelection: js.Function2[JQuery, js.Function1[js.Any, Unit], Unit] = ???
  var tokenizer: js.Function4[String, js.Array[js.Any], js.Function0[Unit], Select2Options, String] = ???
  var tokenSeparators: js.Array[String] = ???
  var query: js.Function1[Select2QueryOptions, Unit] = ???
  var ajax: Select2AjaxOptions = ???
  var data: js.Any = ???
  var tags: js.Any = ???
  var containerCss: js.Any = ???
  var containerCssClass: js.Any = ???
  var dropdownCss: js.Any = ???
  var dropdownCssClass: js.Any = ???
  var escapeMarkup: js.Function1[String, String] = ???
}

trait Select2JQueryEventObject extends JQueryEventObject {
  var `val`: js.Any = ???
  var added: js.Any = ???
  var removed: js.Any = ???
}

object Utils {
  implicit def jq2Select2Static(jq:JQuery):Select2Static = jq.asInstanceOf[Select2Static]
}

trait Select2Static extends js.Object {

  def select2(): Select2Static = ???
  def select2(it: IdTextPair): Select2Static = ???
  def select2(method: String): js.Dynamic = ???
  def select2(method: String, value: js.Any, trigger: Boolean = ???): js.Dynamic = ???
  def select2(options: Select2Options): Select2Static = ???
  def select2(options: js.Dynamic): Select2Static = ???
}