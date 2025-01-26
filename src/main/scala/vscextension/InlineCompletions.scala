package vscextension
import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose


import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichIterableOnce
/**
  * InlineCompletions is a simple extension that demonstrates how to provide inline completions in the editor.
  * https://github.com/microsoft/vscode-extension-samples/tree/main/inline-completions 
  */
object InlineCompletions {

  def createCompletionProvider(): vscode.InlineCompletionItemProvider = {
    new vscode.InlineCompletionItemProvider {
      override def provideInlineCompletionItems(
          document: vscode.TextDocument,
          position: vscode.Position,
          context: vscode.InlineCompletionContext,
          token: vscode.CancellationToken
      ) ={
        val text = document.getText(new vscode.Range(position, position))
        val word = text.split(" ").last
        val items = List("foo", "bar", "baz")
          .filter(_.startsWith(word))
          .map { label =>
            new vscode.InlineCompletionItem(label, new vscode.Range(position, position))
          }

        items.toJSArray
      } 
      
    }
  }

  def registerInlineCompletions()={
    vscode.languages.registerInlineCompletionItemProvider("*",createCompletionProvider())
  }
}