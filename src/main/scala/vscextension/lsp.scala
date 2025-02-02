package vscextension

import scala.scalajs.js

import facade.lspFacade.LanguageClient

/** Language Server Protocol (LSP) client
  *
  * This is a simple example of how to start a language server protocol client in a vscode extension.
  *
  * The language server protocol is a protocol used between an editor and a language server to provide language features
  * like auto completions, go to definition, etc.
  *
  * https://github.com/microsoft/vscode-extension-samples/blob/main/lsp-sample/README.md
  */
object lsp {
  def startLsp() = {
    val client = new LanguageClient(
      "vscode-scalajs-hello", // id
      "VSCode Scala.js Hello", // name
      null,
      js.Dynamic.literal()
    )
    client.start()
  }

}
