package fr.iscpif.scaladget.jsplumb

import scala.scalajs.js
import js.annotation._


trait JSPlumb extends js.Object {
  def ready(callback: js.Function0[Unit]) = ???
  def getInstance(d: js.Array[Defaults]): JSPlumbInstance = ???
  def fire(title: js.String, instance: JSPlumbInstance)
  def Defaults: Defaults = ???
}

trait JSPlumbInstance extends js.Object {
  var Defaults: Defaults = ???
  def setRenderMode(renderMode: String): String = ???
  def bind(event: String, callback: js.Function1[js.Any, Unit]): Unit = ???
  def unbind(event: String = ???): Unit = ???
  def importDefaults(defaults: Defaults): Unit = ???
  def restoreDefaults(): Unit = ???
  def addClass(el: js.Any, clazz: String): Unit = ???
  def addEndpoint(ep: String): js.Dynamic = ???
  def removeClass(el: js.Any, clazz: String): Unit = ???
  def hasClass(el: js.Any, clazz: String): Unit = ???
  def draggable(el: String, options: DragOptions): JSPlumbInstance = ???
  def draggable(ids: js.Array[String], options: DragOptions): JSPlumbInstance = ???
  def connect(connection: ConnectParams, referenceParams: ConnectParams = ???): Connection = ???
  def makeSource(el: String, options: SourceOptions): Unit = ???
  def makeTarget(el: String, options: TargetOptions): Unit = ???
  def repaintEverything(): Unit = ???
  def detachEveryConnection(): Unit = ???
  def detachAllConnections(el: String): Unit = ???
  def removeAllEndpoints(el: String, recurse: Boolean = ???): JSPlumbInstance = ???
  def select(params: SelectParams): Connections = ???
  def getConnections(options: js.Any = ???, flat: js.Any = ???): js.Array[js.Any] = ???
  def deleteEndpoint(uuid: String, doNotRepaintAfterwards: Boolean): JSPlumbInstance = ???
  def deleteEndpoint(endpoint: Endpoint, doNotRepaintAfterwards: Boolean): JSPlumbInstance = ???
  def repaint(el: String): JSPlumbInstance = ???
  var SVG: String = ???
  var CANVAS: String = ???
  var VML: String = ???
}


trait Defaults extends js.Object {
  var Endpoint: js.Array[js.Any] = ???
  var PaintStyle: PaintStyle = ???
  var HoverPaintStyle: PaintStyle = ???
  var ConnectionsDetachable: Boolean = ???
  var ReattachConnections: Boolean = ???
  var ConnectionOverlays: js.Array[js.Array[js.Any]] = ???
  var Container: js.Any = ???
  var DragOptions: DragOptions = ???
}

trait PaintStyle extends js.Object {
  var strokeStyle: String = ???
  var lineWidth: Double = ???
}

trait ArrowOverlay extends js.Object {
  var location: Double = ???
  var id: String = ???
  var length: Double = ???
  var foldback: Double = ???
}

trait LabelOverlay extends js.Object {
  var label: String = ???
  var id: String = ???
  var location: Double = ???
  var cssClass: String = ???
}

trait Connections extends js.Object {
  def detach(): Unit = ???
  var length: Double = ???
}

trait ConnectParams extends js.Object {
  var source: js.Any = ???
  var target: js.Any = ???
  var detachable: Boolean = ???
  var deleteEndpointsOnDetach: Boolean = ???
  var endPoint: String = ???
  var anchor: String = ???
  var anchors: js.Array[js.Any] = ???
  var label: String = ???
}

trait DragOptions extends js.Object {
  var containment: String = ???
}

trait SourceOptions extends js.Object {
  var parent: String = ???
  var endpoint: String = ???
  var anchor: String = ???
  var connector: js.Array[js.Any] = ???
  var connectorStyle: PaintStyle = ???
}

trait TargetOptions extends js.Object {
  var isTarget: Boolean = ???
  var maxConnections: Double = ???
  var uniqueEndpoint: Boolean = ???
  var deleteEndpointsOnDetach: Boolean = ???
  var endpoint: String = ???
  var dropOptions: DropOptions = ???
  var anchor: js.Any = ???
}

trait DropOptions extends js.Object {
  var hoverClass: String = ???
}

trait SelectParams extends js.Object {
  var scope: String = ???
  var source: String = ???
  var target: String = ???
}

trait Connection extends js.Object {
  def setDetachable(detachable: Boolean): Unit = ???
  def setParameter[T](name: String, value: T): Unit = ???
  var endpoints: js.Array[Endpoint] = ???
  //def getOverlay(s: String): js.Dynamic = ???
}

trait Endpoint extends js.Object {
}

