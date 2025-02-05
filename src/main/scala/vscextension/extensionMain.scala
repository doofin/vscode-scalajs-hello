package vscextension

import typings.vscode.mod as vscode

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import facade.vscodeUtils.*
import scala.concurrent.ExecutionContext.Implicits.global

import typings.vscode.mod.TextEditor
import _root_.vscextension.facade.vscodeUtils

object extensionMain {

  /** The main entry for the extension, called when activated first time.
    */
  @JSExportTopLevel("activate") // Exports the function to javascript so that VSCode can load it
  def activate(context: vscode.ExtensionContext): Unit = {
    showMessageAndLog("congrats, your scala.js vscode extension is loaded")

    commands.registerAllCommands(context)

    // show the current languages
    vscode.window.activeTextEditor.toOption match {
      case None =>
      case Some(editor) =>
        showMessageAndLog("current language: " + editor.document.languageId)
    }

    // register inline completions like github copilot
    InlineCompletions.registerInlineCompletions()

    // language server client
    // lsp.startLsp()

    // webview
    // webview.showWebviewPanel()

    // quick pick palette, like command palette
    // quickPick.showQuickPick()

    // code actions like quick fixes
    CodeActions.registerCodeActions(context)

    // network requests
    io.network.httpGet

    // load configuration
    val projectRoot = vscode.workspace.rootPath.getOrElse("")
    val temp = "/home/dhp/work/vscode-scalajs-hello"
    val cfg = io.config.loadConfig(temp + "/.vscode/settings.json")
    vscodeUtils.showMessageAndLog(s"config loaded: $cfg")
  }

}
