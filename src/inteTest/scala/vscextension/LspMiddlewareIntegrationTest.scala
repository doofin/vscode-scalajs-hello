package vscextension

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.Promise
import scala.scalajs.js
import scala.scalajs.js.Thenable.Implicits.*
import scala.scalajs.js.timers.setTimeout

import typings.node.{nodeColonfsMod, osMod, pathMod}
import typings.vscode.mod as vscode
import munit.FunSuite

class LspMiddlewareIntegrationTest extends FunSuite {
  test("language-client middleware filters internal document symbols") {
    val extension =
      vscode.extensions.getExtension[js.Any]("test.vscode-scalajs-hello")
    assert(extension.isDefined, "Scala.js extension was not found")

    val tempFile =
      pathMod.join(osMod.tmpdir(), s"scalajs-lsp-middleware-${js.Date.now().toLong}.txt")
    nodeColonfsMod.writeFileSync(tempFile, "sample text")
    val uri = vscode.Uri.file(tempFile)

    extension.get
      .activate()
      .toFuture
      .flatMap(_ => vscode.workspace.openTextDocument(uri).toFuture)
      .flatMap { _ =>
        eventually(
          documentSymbolNames(uri),
          names => names.contains("VisibleSymbol"),
          "visible document symbol"
        )
      }
      .map { names =>
        assert(names.contains("VisibleSymbol"))
        assert(!names.contains("_internalSymbol"))
      }
      .andThen { case _ => nodeColonfsMod.unlinkSync(tempFile) }
  }

  private def documentSymbolNames(uri: vscode.Uri): Future[Set[String]] =
    vscode.commands
      .executeCommand[js.Array[js.Dynamic]]("vscode.executeDocumentSymbolProvider", uri)
      .toFuture
      .map(_.iterator.map(_.selectDynamic("name").asInstanceOf[String]).toSet)

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
