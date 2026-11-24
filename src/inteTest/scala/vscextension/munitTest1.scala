package vscextension

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.scalajs.js

import typings.vscode.Thenable
import typings.vscode.mod as vscode
import munit.FunSuite

// implicit conversion from Thenable to Future
import scala.scalajs.js.Thenable.Implicits._

class munitTest1 extends FunSuite {
  test("munit test!") {
    val extension =
      vscode.extensions.getExtension[js.Any]("test.vscode-scalajs-hello")

    assert(extension.isDefined, "Scala.js extension was not found")

    (extension.get
      .activate())
      .toFuture
      .flatMap(_ => (vscode.commands.getCommands()).toFuture)
      .map { commands =>
        println("Test started for munit test!")
        assert(
          commands.contains("extension.helloWorld"),
          "Hello World command was not registered"
        )
      }
  }

}
