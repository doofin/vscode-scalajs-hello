package vscextension

import scala.scalajs.js

import typings.vscode.mod as vscode

import facade.vscodeUtils.*

/* Syntax highlighting for TODO comments in Scala files

It highlights TODO comments in Scala source files within the VS Code editor. Example: `// TODO: Refactor this method`

To test,Click Plain Text in the bottom-right and select Scala, or save the file as .scala
 */
object SyntaxHighlighting {
  private val todoPattern = "\\bTODO\\b".r

  def todoOffsets(line: String): List[Int] =
    todoPattern.findAllMatchIn(line).map(_.start).toList

  def register(context: vscode.ExtensionContext): Unit = {
    val legend = new vscode.SemanticTokensLegend(
      js.Array("keyword"),
      js.Array[String]()
    )
    val provider = new vscode.DocumentSemanticTokensProvider {
      override def provideDocumentSemanticTokens(
          document: vscode.TextDocument,
          token: vscode.CancellationToken
      ): vscode.SemanticTokens = {
        val builder = new vscode.SemanticTokensBuilder(legend)
        if (!token.isCancellationRequested) {
          for (line <- 0 until document.lineCount.toInt) {
            todoOffsets(document.lineAt(line).text).foreach { start =>
              builder.push(line, start, 4, 0)
            }
          }
        }
        builder.build()
      }
    }

    val registration = vscode.languages.registerDocumentSemanticTokensProvider(
      selector = "scala",
      provider = provider,
      legend = legend
    )
    context.pushDisposable(registration)
  }
}
