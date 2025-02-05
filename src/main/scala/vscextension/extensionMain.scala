package vscextension

import typings.vscode.mod as vscode

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel
import facade.vscodeUtils.*

import typings.vscode.mod.TextEditor
import _root_.vscextension.facade.vscodeUtils
import typings.std.stdStrings.s
import typings.std.stdStrings.show

object extensionMain {

  /** The main entry for the extension, called when activated first time.
    */
  @JSExportTopLevel("activate") // Exports the function to javascript so that VSCode can load it
  def activate(context: vscode.ExtensionContext): Unit = {
    showMessageAndLog("congrats, your scala.js vscode extension is loaded")

    commands.registerAllCommands(context)

    // show the current language of the document
    vscode.window.activeTextEditor.toOption match {
      case None =>
        showMessageAndLog("no active editor")
      case Some(editor) =>
        showMessageAndLog("current language: " + editor.document.languageId)
    }

    val projectRoot = vscode.workspace.rootPath.getOrElse("")
    showMessageAndLog("project root: " + projectRoot)

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
    val url = "https://github.com/"
    io.network.httpGet(url)
    io.network.httpGetTyped(url)

    // file operations
    io.fileIO.createFile(projectRoot)
    // load configuration
    val cfg = io.config.loadConfig(projectRoot + "/.vscode/settings.json")
    vscodeUtils.showMessageAndLog(s"config loaded: $cfg")

  }

}
