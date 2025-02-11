package vscextension

import typings.vscode.mod as vscode
import typings.vscode.mod.TextEditor

import facade.vscodeUtils.*

object documentProps {

  /** Shows various properties of the current document and editor
    *
    * like the language of the document, the project root, etc.
    *
    * also the programing features like symbols, references, def
    *
    * https://code.visualstudio.com/api/references/commands
    *
    * https://code.visualstudio.com/api/extension-guides/command
    */
  def showProps = {
    vscode.window.activeTextEditor.toOption match {
      case None =>
        showMessageAndLog("no active editor")
      case Some(editor) =>
        showMessageAndLog("current language: " + editor.document.languageId)
    }

    val projectRoot = vscode.workspace.rootPath.getOrElse("")
    showMessageAndLog("project root: " + projectRoot)

  }
}
