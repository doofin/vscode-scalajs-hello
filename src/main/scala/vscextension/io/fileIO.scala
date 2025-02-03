package vscextension.io
import typings.node.fsMod

/** IO utilities for input/output operations, like creating files/folders
  *
  * This object contains utilities for IO operations.
  */
object fileIO {
  def createFile() = {
    fsMod.mkdirSync("test")
    fsMod.writeFileSync("test/test.txt", "Hello, World!")
  }
}
