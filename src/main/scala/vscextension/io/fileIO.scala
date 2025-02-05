package vscextension.io

import typings.node.fsMod
import typings.node.fsMod.PathOrFileDescriptor
import typings.node.bufferMod.global.BufferEncoding

import vscextension.facade.vscodeUtils

/** IO utilities for input/output operations, like creating files/folders
  *
  * This object contains utilities for IO operations.
  */
object fileIO {
  def createFile() = {
    // create folder target/test and file target/test/helo
    vscodeUtils.consoleLog("creating file")
    val testFolder = "target/test"

    // not working,file already exists
    fsMod.mkdir(
      "target/test",
      { err =>
        if err != null then vscodeUtils.consoleLog("error creating folder: " + err)
        else vscodeUtils.consoleLog("folder created")
      }
    )
    fsMod.mkdirSync("target")
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
