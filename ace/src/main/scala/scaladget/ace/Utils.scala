package scaladget.ace

import com.raquo.laminar.api.L._

import scala.scalajs.js
import org.scalajs.dom

object Utils {

  def rangeFor(startRow: Int, startCol: Int, endRow: Int, endCol: Int) =
    js.Dynamic.newInstance(ace.require("ace/range").Range)(startRow, startCol, endRow, endCol).asInstanceOf[scaladget.ace.Range]

  def getBreakPointElements(editorDiv: HtmlElement) = {
   editorDiv.ref.getElementsByClassName("ace_breakpoint").map {e=>
      e-> e.innerText
    }}
  }
