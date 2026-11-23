package vscextension

import scala.scalajs.js

import typings.vscode.Thenable
import typings.vscode.mod as vscode

object IntegrationTestMain {
  def main(args: Array[String]): Unit = {
    val suite: js.Function0[Unit] = () => {
      val test: js.Function0[Thenable[Unit]] = { () =>
        val extension =
          vscode.extensions.getExtension[js.Any]("test.vscode-scalajs-hello")
        assert(extension.isDefined, "Scala.js extension was not found")

        extension.get
          .activate()
          .`then`((_: js.Any) =>
            vscode.commands
              .getCommands()
              .`then`((commands: js.Array[String]) => {
                assert(
                  commands.contains("extension.helloWorld"),
                  "Hello World command was not registered"
                )
              })
          )
      }

      js.Dynamic.global.test("activates and registers Hello World", test)
      ()
    }

    js.Dynamic.global.suite("Scala.js extension integration", suite)
  }
}
