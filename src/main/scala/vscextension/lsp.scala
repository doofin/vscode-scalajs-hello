package vscextension
import typings.vscodeLanguageclient.mod.BaseLanguageClient
import typings.vscodeLanguageclient.libCommonClientMod.LanguageClientOptions

import scala.scalajs.js
import scala.scalajs.js.JSConverters.*
import vscextension.facade.lspFacade.LanguageClient

// https://github.com/microsoft/vscode-extension-samples/blob/main/lsp-sample/README.md
object lsp {
  def start2() = {
    val client = new LanguageClient(
      "vscode-scalajs-hello", // id
      "VSCode Scala.js Hello", // name
      null,
      js.Dynamic.literal()
    )
  }

  def start(): Unit = {
    /*  client
	const clientOptions: LanguageClientOptions = {
		// Register the server for plain text documents
		documentSelector: [{ scheme: 'file', language: 'plaintext' }],
		synchronize: {
			// Notify the server about file changes to '.clientrc files contained in the workspace
			// fileEvents: workspace.createFileSystemWatcher('**/ // .clientrc')
    // }
    // } */
    val clientOptions = LanguageClientOptions()
      .setDocumentSelector(
        js.Array(
        )
      )
    //   .synchronize()

    val client = new BaseLanguageClient(
      "vscode-scalajs-hello", // id
      "VSCode Scala.js Hello", // name
      clientOptions
    )
    client.start()
  }
}
