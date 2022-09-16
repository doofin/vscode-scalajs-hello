import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.annotation.JSExportTopLevel

/**
 * This method is called when your extension is actived.
 * The extension is activated the first time one of its features is used (here we only have commands).
 */ 
@JSExportTopLevel("activate") // Exports the function to javascript so that VSCode can load it
def activate(context: vscode.ExtensionContext): Unit =

  println(
    """Congratulations, your extension "vscode-scalajs-hello" is now active!"""
  )

  /**
    * Example command. VSCode commands can take an argument of any type, hence the `Any` here.
    *
    * @param arg the argument (which we don't use, but it could be useful for other commands)
    */
  def showHello(arg: Any): Unit =
    val name = "Abdessalam"
    vscode.window.showInformationMessage(s"Hello World! How are you ${name}?")

  // Store all the commands here
  val commands = Seq(
    ("extension.helloWorld", showHello)
  )

  // Register the commands in VSCode
  for (name, fun) <- commands do
    context.subscriptions.push(
      vscode.commands.registerCommand(name, fun).asInstanceOf[Dispose] // to make the typechecker happy (VSCode has typescript facades nowadays)
    )
