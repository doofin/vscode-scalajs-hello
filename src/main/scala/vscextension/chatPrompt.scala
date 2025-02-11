package vscextension
import typings.vscodePromptTsx.mod.*
import typings.vscodePromptTsx.distBasePromptElementsMod.ChatMessageProps

/** Show a chat prompt UI in the editor, for AI chatbot like features.
  *
  * https://code.visualstudio.com/api/extension-guides/prompt-tsx
  */
object chatPrompt {

  def showChatPrompt(): Unit = {
    // val chatPrompt = new ChatPrompt()
    // chatPrompt.show()
    new UserMessage(ChatMessageProps().setName("user"))
  }
}
