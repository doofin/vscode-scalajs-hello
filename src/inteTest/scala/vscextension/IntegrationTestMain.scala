package vscextension

import scala.concurrent.ExecutionContext.Implicits.global
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import scala.scalajs.js

import typings.vscode.Thenable
import typings.vscode.mod as vscode
import vscextension.facade.vscodeUtils.consoleLog
import munit.FunSuite
import Mocha.*

/** The main entry for integration test, via Mocha framework. Munit tests are also supported but
  * require additional setup
  */
object IntegrationTestMain {
  def main(args: Array[String]): Unit = {
    mochaTest1.test1()

    // integration tests for Munit
    execMunitTests(new munitTest1)

  }

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
