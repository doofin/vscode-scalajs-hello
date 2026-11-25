package vscextension

import scala.scalajs.js

import typings.vscode.mod as vscode

import facade.vsLanguageClient.{
  DocumentFilter,
  LanguageClient,
  LanguageClientOptions,
  NodeModule,
  ServerOptions,
  Trace,
  TransportKind
}
import facade.vscodeUtils.*

/** LSP client example: connects over IPC to provide Plain Text completions and uppercase-word
  * diagnostics.
  */
object lsp {
  def start(context: vscode.ExtensionContext): Unit = {
    val serverPath =
      if (context.extensionMode == vscode.ExtensionMode.Development)
        "out/server/server.js"
      else "src/res/server.js"
    val serverModule = context.asAbsolutePath(serverPath)
    consoleLog(s"Server file: $serverModule")
    val outputChannel = vscode.window.createOutputChannel("Scala.js LSP")
    outputChannel.appendLine(
      s"Starting LSP client; server module: $serverModule; IPC transport: ${TransportKind.ipc}"
    )
    val serverOptions = ServerOptions(
      run = NodeModule(serverModule, TransportKind.ipc),
      debug = NodeModule(serverModule, TransportKind.ipc)
    )

    val clientOptions = LanguageClientOptions(
      documentSelector = js.Array(DocumentFilter("file", "plaintext")),
      outputChannel = outputChannel,
      traceOutputChannel = outputChannel
    )
    val client = new LanguageClient(
      "languageServerExample",
      "Language Server Example",
      serverOptions,
      clientOptions
    )

    context.pushDisposable(new vscode.Disposable(() => outputChannel.dispose()))
    context.pushDisposable(new vscode.Disposable(() => {
      client.stop()
      ()
    }))

    client
      .start()
      .`then`((_: Unit) => {
        outputChannel.appendLine("Language client started; enabling verbose LSP trace.")
        client
          .setTrace(Trace.Verbose)
          .`catch`((error: Any) => {
            outputChannel.appendLine(s"Failed to enable LSP trace: $error")
            ()
          })
        vscode.window.showInformationMessage("Language server started successfully.")
        ()
      })
      .`catch`((error: Any) => {
        outputChannel.appendLine(s"Language server failed to start: $error")
        vscode.window.showErrorMessage(s"Language server failed to start: $error")
        ()
      })
  }
}
