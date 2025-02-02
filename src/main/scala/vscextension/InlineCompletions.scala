package vscextension
import typings.vscode.mod as vscode
import typings.vscode.anon.Dispose

import scala.collection.immutable
import scala.scalajs.js
import scala.scalajs.js.JSConverters.JSRichIterableOnce

import facade.vscodeUtils.*

/** demonstrates how to provide inline completions in the editor. like the github copilot
  * https://github.com/microsoft/vscode-extension-samples/tree/main/inline-completions
  * https://github.com/continuedev/continue/blob/main/core/autocomplete/CompletionProvider.ts
  */
object InlineCompletions {

  def createCompletionProvider(): vscode.InlineCompletionItemProvider = {
    new vscode.InlineCompletionItemProvider {
      override def provideInlineCompletionItems(
          document: vscode.TextDocument, // the current document
          position: vscode.Position, // the position of the cursor
          context: vscode.InlineCompletionContext,
          token: vscode.CancellationToken // to cancel the completion
      ) = {

        val offset = 0
        // get the line before the current line
        val lineBefore =
          if !(position.line - offset < 0)
          then document.lineAt(position.line - offset).text
          else ""

        // the whole line before the cursor
        showMessage(s"line before cursor: $lineBefore")

        // get the word before the cursor
        // val word = document.getText(new vscode.Range(position.`with`(0), position))
        // utils.showMessage(s"word: $word")

        // always return a list of items, only first item will be displayed
        val items = List("foo", "bar", "baz")
          // .filter(_.startsWith(word))
          .map { str =>
            new vscode.InlineCompletionItem(
              insertText = str, // text to insert
              range = new vscode.Range(position, position)
            )
          }

        items.toJSArray

      }

    }
  }

  def registerInlineCompletions() = {
    vscode.languages.registerInlineCompletionItemProvider(selector = "*", provider = createCompletionProvider())
  }
}
