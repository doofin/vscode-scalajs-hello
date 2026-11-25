package vscextension

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import scala.scalajs.js.Thenable.Implicits.*
import scala.scalajs.js.timers.setTimeout

import typings.node.{nodeColonfsMod, osMod, pathMod}
import typings.vscode.mod as vscode
import munit.FunSuite

class LspIntegrationTest extends FunSuite {
  test("language server provides completions and uppercase diagnostics") {
    val extension =
      vscode.extensions.getExtension[js.Any]("test.vscode-scalajs-hello")
    assert(extension.isDefined, "Scala.js extension was not found")

    val tempFile =
      pathMod.join(osMod.tmpdir(), s"scalajs-lsp-${js.Date.now().toLong}.txt")
    nodeColonfsMod.writeFileSync(tempFile, "HELLO")
    val uri = vscode.Uri.file(tempFile)

    extension.get
      .activate()
      .toFuture
      .flatMap(_ => vscode.workspace.openTextDocument(uri).toFuture)
      .flatMap { _ =>
        eventually(
          completionLabels(uri),
          labels => labels.contains("TypeScript") && labels.contains("JavaScript"),
          "language server completion items"
        )
      }
      .flatMap { _ =>
        eventually(
          Future.successful(
            vscode.languages.getDiagnostics(uri).iterator.map(_.message).toSet
          ),
          messages => messages.contains("HELLO is all uppercase."),
          "uppercase-word diagnostic"
        )
      }
      .map { messages =>
        assert(messages.contains("HELLO is all uppercase."))
      }
      .andThen { case _ => nodeColonfsMod.unlinkSync(tempFile) }
  }

  private def completionLabels(uri: vscode.Uri): Future[Set[String]] =
    vscode.commands
      .executeCommand[js.Dynamic](
        "vscode.executeCompletionItemProvider",
        uri,
        new vscode.Position(0, 0)
      )
      .toFuture
      .map { result =>
        val items =
          if (js.Array.isArray(result))
            result.asInstanceOf[js.Array[js.Dynamic]]
          else result.selectDynamic("items").asInstanceOf[js.Array[js.Dynamic]]
        items.iterator.map(_.selectDynamic("label").asInstanceOf[String]).toSet
      }

  private def eventually[A](
      read: => Future[A],
      isReady: A => Boolean,
      description: String,
      attemptsRemaining: Int = 40
  ): Future[A] =
    read.flatMap { value =>
      if (isReady(value)) Future.successful(value)
      else if (attemptsRemaining == 0)
        Future.failed(new AssertionError(s"Timed out waiting for $description"))
      else {
        val delay = Promise[Unit]()
        setTimeout(50)(delay.success(()))
        delay.future.flatMap(_ =>
          eventually(read, isReady, description, attemptsRemaining - 1)
        )
      }
    }
}
