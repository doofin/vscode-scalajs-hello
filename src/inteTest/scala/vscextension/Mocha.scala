package vscextension

import scala.scalajs.js
import scala.scalajs.js.Thenable

/** Provides Scala.js bindings for Mocha test framework using js.Dynamic.
  *
  * This allows defining test suites and tests without requiring @js.native and @JSGlobal
  * annotations, and it's more idiomatic for Scala with curried functions.
  */
object Mocha {

  /** Defines a test suite with the given name and body.
    *
    * @param name
    * @param body
    */
  def suite(name: String)(body: () => Unit): Unit =
    js.Dynamic.global.suite(name, body)

  /** run a single test. should be used within a suite.
    *
    * @param name
    * @param body
    */
  def test(name: String)(body: () => Thenable[Unit]): Unit =
    js.Dynamic.global.test(name, body)

}
