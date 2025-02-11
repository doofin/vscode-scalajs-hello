package vscextension
import typings.vscode.mod as vscode
import vscextension.facade.vscodeUtils.*

object statusBar {

  def createStatusBarItem(context: vscode.ExtensionContext) = {
    val statusBarItem =
      vscode.window.createStatusBarItem(vscode.StatusBarAlignment.Left)
    statusBarItem.text = "mStatusBar"
    statusBarItem.name = "mStatusBar"
    statusBarItem.command = "mStatusBar.command"
    statusBarItem.show()

    context.pushDisposable(statusBarItem.asInstanceOf[vscode.Disposable])
  }
}
