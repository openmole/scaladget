package scaladget.lunr

/*
 * Copyright (C) 06/07/16 // mathieu.leclaire@openmole.org
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */


import scala.scalajs.js
import scala.scalajs.js.annotation._

@js.native
@JSGlobal
class EventEmitter extends js.Object {
  def addListener(eventName: String, handler: js.Function): Unit = js.native

  def addListener(eventName: String, eventName2: String, handler: js.Function): Unit = js.native

  def addListener(eventName: String, eventName2: String, eventName3: String, handler: js.Function): Unit = js.native

  def addListener(eventName: String, eventName2: String, eventName3: String, eventName4: String, handler: js.Function): Unit = js.native

  def addListener(eventName: String, eventName2: String, eventName3: String, eventName4: String, eventName5: String, handler: js.Function): Unit = js.native

  def removeListener(eventName: String, handler: js.Function): Unit = js.native

  def emit(eventName: String, args: js.Any*): Unit = js.native

  def hasHandler(eventName: String): Boolean = js.native
}

@js.native
trait IPipelineFunction extends js.Object {
  def apply(token: String): String = js.native

  def apply(token: String, tokenIndex: Double): String = js.native

  def apply(token: String, tokenIndex: Double, tokens: js.Array[String]): String = js.native
}

@js.native
@JSGlobal
class Pipeline extends js.Object {
  var registeredFunctions: js.Dictionary[js.Function] = js.native

  def registerFunction(fn: IPipelineFunction, label: String): Unit = js.native

  def warnIfFunctionNotRegistered(fn: IPipelineFunction): Unit = js.native

  def add(functions: IPipelineFunction*): Unit = js.native

  def after(existingFn: IPipelineFunction, newFn: IPipelineFunction): Unit = js.native

  def before(existingFn: IPipelineFunction, newFn: IPipelineFunction): Unit = js.native

  def remove(fn: IPipelineFunction): Unit = js.native

  def run(tokens: js.Array[String]): js.Array[String] = js.native

  def reset(): Unit = js.native

  def toJSON(): js.Dynamic = js.native
}

@js.native
@JSGlobal
object Pipeline extends js.Object {
  def load(serialised: js.Any): Pipeline = js.native
}

@js.native
@JSGlobal
class Vector extends js.Object {
  var list: Node = js.native

  def magnitude(): Double = js.native

  def dot(otherVector: Vector): Double = js.native

  def similarity(otherVector: Vector): Double = js.native
}

@js.native
@JSGlobal
class Node protected() extends js.Object {
  def this(idx: Double, `val`: Double, next: Node) = this()

  var idx: Double = js.native
  var `val`: Double = js.native
  var next: Node = js.native
}

@js.native
@JSGlobal
class SortedSet[T] extends js.Object {
  var elements: js.Array[T] = js.native
  var length: Double = js.native

  def add(values: T*): Unit = js.native

  def toArray(): js.Array[T] = js.native

  def map(fn: js.Function, ctx: js.Any): js.Array[T] = js.native

  def forEach(fn: js.Function, ctx: js.Any): js.Dynamic = js.native

  def indexOf(elem: T, start: Double = ???, end: Double = ???): Double = js.native

  def locationFor(elem: T, start: Double = ???, end: Double = ???): Double = js.native

  def intersect(otherSet: SortedSet[T]): SortedSet[T] = js.native

  def union(otherSet: SortedSet[T]): SortedSet[T] = js.native

  //def clone(): SortedSet[T] = js.native

  def toJSON(): js.Dynamic = js.native
}

@js.native
@JSGlobal
object SortedSet extends js.Object {
  def load[T](serialisedData: js.Array[T]): SortedSet[T] = js.native
}

@js.native
trait IIndexField extends js.Object {
  var name: String = js.native
  var boost: Double = js.native
}

@js.native
trait IIndexSearchResult extends js.Object {
  var ref: String = js.native
  var score: Double = js.native
}

@js.native
@JSGlobal
class Index extends js.Object {
  var eventEmitter: EventEmitter = js.native
  var documentStore: Store[String] = js.native
  var tokenStore: TokenStore = js.native
  var corpusTokens: SortedSet[String] = js.native
  var pipeline: Pipeline = js.native
  var _fields: js.Array[IIndexField] = js.native
  var _ref: String = js.native
  var _idfCache: js.Dictionary[String] = js.native

  def on(eventName: String, handler: js.Function): Unit = js.native

  def on(eventName: String, eventName2: String, handler: js.Function): Unit = js.native

  def on(eventName: String, eventName2: String, eventName3: String, handler: js.Function): Unit = js.native

  def on(eventName: String, eventName2: String, eventName3: String, eventName4: String, handler: js.Function): Unit = js.native

  def on(eventName: String, eventName2: String, eventName3: String, eventName4: String, eventName5: String, handler: js.Function): Unit = js.native

  def off(eventName: String, handler: js.Function): Unit = js.native

  def field(fieldName: String, options: js.Any = ???): Index = js.native

  def ref(refName: String): Index = js.native

  def add(doc: js.Any, emitEvent: Boolean = ???): Unit = js.native

  def remove(doc: js.Any, emitEvent: Boolean = ???): Unit = js.native

  def update(doc: js.Any, emitEvent: Boolean = ???): Unit = js.native

  def idf(token: String): String = js.native

  def search(query: String): js.Array[IIndexSearchResult] = js.native

  def documentVector(documentRef: String): Vector = js.native

  def toJSON(): js.Dynamic = js.native

  def use(plugin: js.Function, args: js.Any*): Unit = js.native
}

@js.native
@JSGlobal
object Index extends js.Object {
  def load(serialisedData: js.Any): Index = js.native
}

@js.native
@JSGlobal
class Store[T] extends js.Object {
  var store: js.Dictionary[SortedSet[T]] = js.native
  var length: Double = js.native

  def set(id: String, tokens: SortedSet[T]): Unit = js.native

  def get(id: String): SortedSet[T] = js.native

  def has(id: String): Boolean = js.native

  def remove(id: String): Unit = js.native

  def toJSON(): js.Dynamic = js.native
}

@js.native
@JSGlobal
object Store extends js.Object {
  def load[T](serialisedData: js.Any): Store[T] = js.native
}

@js.native
trait ITokenDocument extends js.Object {
  var ref: Double = js.native
  var tf: Double = js.native
}

@js.native
@JSGlobal
class TokenStore extends js.Object {
  var root: js.Dictionary[TokenStore] = js.native
  var docs: js.Dictionary[ITokenDocument] = js.native
  var length: Double = js.native

  def add(token: String, doc: ITokenDocument, root: TokenStore = ???): Unit = js.native

  def has(token: String): Boolean = js.native

  def getNode(token: String): TokenStore = js.native

  def get(token: String, root: TokenStore): js.Dictionary[ITokenDocument] = js.native

  def count(token: String, root: TokenStore): Double = js.native

  def remove(token: String, ref: String): Unit = js.native

  def expand(token: String, memo: js.Array[String] = ???): js.Array[String] = js.native

  def toJSON(): js.Dynamic = js.native
}

@js.native
@JSGlobal
object TokenStore extends js.Object {
  def load(serialisedData: js.Any): TokenStore = js.native
}

@JSGlobal
@js.native
object Lunr extends js.Object {
  var version: String = js.native

  def tokenizer(token: String): String = js.native

  def stemmer(token: String): String = js.native

  def stopWordFilter(token: String): String = js.native

  def trimmer(token: String): String = js.native
}

@JSGlobal
@js.native
object StopWordFilter extends js.Object {
  var stopWords: SortedSet[String] = js.native
}


@JSGlobalScope
@js.native
object Importedjs extends js.Object {
  def lunr(config: js.Function): Index = js.native
}
