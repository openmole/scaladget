package scaladget.ace

import scala.scalajs.js

object Utils {

  def rangeFor(startRow: Int, startCol: Int, endRow: Int, endCol: Int) =
    js.Dynamic.newInstance(ace.require("ace/range").Range)(startRow, startCol, endRow, endCol).asInstanceOf[scaladget.ace.Range]

}
