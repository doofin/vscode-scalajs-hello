package vscextension

import munit.FunSuite

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*

import Mocha.*

/** The main entry for integration test, via Mocha framework. Munit tests are also supported but
  * require additional setup
  */
object IntegrationTestMain {
  def main(args: Array[String]): Unit = {
    mochaTest1.test1()

    // integration tests for Munit. put your Munit test suites here
    val munitSuites =
      Seq(new munitTest1)
    munitSuites.foreach(execMunitTests)
  }

  /** Executes the given Munit test suite within a Mocha suite.
    *
    * @param munitSuite
    */
  def execMunitTests(munitSuite: FunSuite): Unit = {
    suite("Munit extension integration") { () =>
      munitSuite.munitTests().foreach { t =>
        test(t.name) { () =>
          t.body().map(_ => ()).toJSPromise
        }
      }
    }
  }

}
