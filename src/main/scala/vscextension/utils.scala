package vscextension

import scala.concurrent.Future

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js

// import scala.scalajs.js.Thenable.Implicits._

object utils {

  /** creates a new js promise
    *
    * you can implicitly convert a `Promise` to a `Future`, and therefore you can directly use the methods of `Future`
    * on `Promise`s with:
    *
    * import scala.scalajs.js.Thenable.Implicits._
    */
  def newJsPromise[T](x: T): js.Promise[T] = {
    js.Promise.resolve(x)
  }

}
