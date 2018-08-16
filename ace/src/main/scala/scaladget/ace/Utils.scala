package scaladget.ace

import scala.scalajs.js

object Utils {

  def rangeFor(startRow: Int, endRow: Int, startCol: Int, endCol: Int) =
    js.Dynamic.newInstance(ace.require("ace/range").Range)(startRow, endRow, startCol, endCol).asInstanceOf[scaladget.ace.Range]

}
