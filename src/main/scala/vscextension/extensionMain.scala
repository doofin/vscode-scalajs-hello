package vscextension

import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import facade.vscodeUtils.*

object extensionMain {

  /** The main entry for the extension, called when activated first time.
    */
  @JSExportTopLevel("activate") // Exports the function to javascript so that VSCode can load it
  def activate(context: vscode.ExtensionContext): Unit = {
    consoleLog("hi,your vscode-scalajs-hello loaded!")

    commands.registerAllCommands(context)

    // register inline completions like github copilot
    InlineCompletions.registerInlineCompletions()

    // language server client
    // lsp.startLsp()

    // webview
    // webview.showWebviewPanel()

    // quick pick palette, like command palette
    // quickPick.showQuickPick()

    // code actions
    CodeActions.registerCodeActions(context)
  }

}
