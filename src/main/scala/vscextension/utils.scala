package vscextension
import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichIterableOnce

object utils {
  def showMessage(message: String): Unit = {
    vscode.window.showInformationMessage(message)
  }
}
