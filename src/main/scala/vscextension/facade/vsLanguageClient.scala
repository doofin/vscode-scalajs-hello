package vscextension.facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import typings.vscode.mod as vscode
import scala.annotation.unused

object vsLanguageClient {
  @js.native
  @JSImport("vscode-languageclient/node", "LanguageClient")
  class LanguageClient(
      @unused id: String,
      @unused name: String,
      @unused serverOptions: ServerOptions,
      @unused clientOptions: LanguageClientOptions
  ) extends js.Object {
    def start(): js.Promise[Unit] = js.native
    def stop(): js.Promise[Unit] = js.native
    def setTrace(value: Int): js.Promise[Unit] = js.native
    def sendRequest(method: String, params: js.Any): js.Promise[js.Dynamic] = js.native
    def onRequest(
        method: String,
        handler: js.Function1[js.Dynamic, js.Promise[Null]]
    ): vscode.Disposable = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "TransportKind")
  object TransportKind extends js.Object {
    val ipc: Int = js.native
  }

  @js.native
  @JSImport("vscode-languageclient/node", "Trace")
  object Trace extends js.Object {
    val Verbose: Int = js.native
  }

  @js.native
  trait NodeModule extends js.Object {
    val module: String = js.native
    val transport: Int = js.native
  }

  object NodeModule {
    def apply(module: String, transport: Int): NodeModule =
      js.Dynamic.literal(module = module, transport = transport).asInstanceOf[NodeModule]
  }

  @js.native
  trait ServerOptions extends js.Object {
    val run: js.Any = js.native
    val debug: js.Any = js.native
  }

  object ServerOptions {
    def apply(run: js.Any, debug: js.Any): ServerOptions =
      js.Dynamic.literal(run = run, debug = debug).asInstanceOf[ServerOptions]
  }

  object DocumentFilter {
    def apply(scheme: String, language: String): vscode.DocumentFilter = {
      js.Dynamic
        .literal(scheme = scheme, language = language)
        .asInstanceOf[vscode.DocumentFilter]
    }

  }

  @js.native
  trait LanguageClientOptions extends js.Object {
    val documentSelector: js.Array[vscode.DocumentFilter] = js.native
    val outputChannel: vscode.OutputChannel = js.native
    val traceOutputChannel: js.UndefOr[vscode.OutputChannel] = js.native
    val initializationOptions: js.UndefOr[js.Any] = js.native
  }

  object LanguageClientOptions {
    def apply(
        documentSelector: js.Array[vscode.DocumentFilter],
        outputChannel: vscode.OutputChannel,
        traceOutputChannel: js.UndefOr[vscode.OutputChannel] = js.undefined,
        initializationOptions: js.UndefOr[js.Any] = js.undefined
    ): LanguageClientOptions =
      js.Dynamic
        .literal(
          documentSelector = documentSelector,
          outputChannel = outputChannel,
          traceOutputChannel = traceOutputChannel,
          initializationOptions = initializationOptions
        )
        .asInstanceOf[LanguageClientOptions]
  }

}
