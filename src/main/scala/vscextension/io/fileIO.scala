package vscextension.io
import typings.node.fsMod
import typings.node.fsMod.PathOrFileDescriptor
import typings.node.bufferMod.global.BufferEncoding

/** IO utilities for input/output operations, like creating files/folders
  *
  * This object contains utilities for IO operations.
  */
object fileIO {
  def createFile() = {
    val testFolder = "target/test"
    fsMod.mkdirSync(testFolder)
    fsMod.writeFileSync(s"$testFolder/helo", "Hello, World!")
  }

  def readFile(file: String): String = {
    fsMod.readFileSync(
      file.asInstanceOf[PathOrFileDescriptor], //
      BufferEncoding.utf8
    )
  }
}
