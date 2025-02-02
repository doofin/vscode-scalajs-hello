package vscextension

import scala.scalajs.js

import facade.lspFacade.LanguageClient

// https://github.com/microsoft/vscode-extension-samples/blob/main/lsp-sample/README.md
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
