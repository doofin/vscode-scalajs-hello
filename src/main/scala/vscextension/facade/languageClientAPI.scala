package vscextension.facade

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

import typings.vscode.mod as vscode

object languageClientAPI {
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
    val run: NodeModule = js.native
    val debug: NodeModule = js.native
  }

  object ServerOptions {
    def apply(run: NodeModule, debug: NodeModule): ServerOptions =
      js.Dynamic.literal(run = run, debug = debug).asInstanceOf[ServerOptions]
  }

  object DocumentFilter {
    def plainTextFile: vscode.DocumentFilter =
      js.Dynamic.literal(scheme = "file", language = "plaintext")
        .asInstanceOf[vscode.DocumentFilter]
  }

  @js.native
  trait LanguageClientOptions extends js.Object {
    val documentSelector: js.Array[vscode.DocumentFilter] = js.native
    val outputChannel: vscode.OutputChannel = js.native
    val traceOutputChannel: vscode.OutputChannel = js.native
  }

  object LanguageClientOptions {
    def apply(
        documentSelector: js.Array[vscode.DocumentFilter],
        outputChannel: vscode.OutputChannel,
        traceOutputChannel: vscode.OutputChannel
    ): LanguageClientOptions =
      js.Dynamic
        .literal(
          documentSelector = documentSelector,
          outputChannel = outputChannel,
          traceOutputChannel = traceOutputChannel
        )
        .asInstanceOf[LanguageClientOptions]
  }

  @js.native
  @JSImport("vscode-languageclient/node", "LanguageClient")
  class LanguageClient(
      id: String,
      name: String,
      serverOptions: ServerOptions,
      clientOptions: LanguageClientOptions
  ) extends js.Object {
    def start(): js.Promise[Unit] = js.native
    def stop(): js.Promise[Unit] = js.native
    def setTrace(value: Int): js.Promise[Unit] = js.native
  }
}
