package vscextension

import typings.vscode.mod as vscode
import vscextension.facade.vscodeUtils.consoleLog

import scala.scalajs.js

import Mocha.*

/** The main entry for integration test, via Mocha framework. Munit tests are also supported but
  * require additional setup
  */
object mochaTest1 {
  def test1(): Unit = {
    suite("sjs integration suite2") { () =>
      test("example test 2") { () =>
        val extension =
          vscode.extensions.getExtension[js.Any]("test.vscode-scalajs-hello")

        extension.get
          .activate()
          .`then`((_: js.Any) =>
            vscode.commands
              .getCommands()
              .`then`((commands: js.Array[String]) => {
                consoleLog(
                  s"Hello World command registered: ${commands.contains("extension.helloWorld")}"
                )
                assert(
                  commands.contains("extension.helloWorld"),
                  "Hello World command was not registered"
                )
                println("Test completed for example test 2 with print statement")
              })
          )
          .asInstanceOf[js.Thenable[Unit]]
      }
    }

  }

}
