package vscextension.io

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
// import typings.node.childProcessMod.spawn

object process {
  @js.native
  @JSImport("child_process", JSImport.Namespace)
  private object ChildProcessModule extends js.Object {
    def spawn(command: String, args: js.Array[String], options: SpawnOptions): ChildProcess = js.native
  }

  @js.native
  trait ChildProcess extends js.Object {
    val stdin: WritableStream = js.native
    val stdout: ReadableStream = js.native
    val stderr: ReadableStream = js.native

    def kill(): Boolean = js.native
    def on(event: String, listener: js.Function1[js.Any, Unit]): ChildProcess = js.native
    def on(event: String, listener: js.Function2[js.Any, js.Any, Unit]): ChildProcess = js.native
  }

  @js.native
  trait ReadableStream extends js.Object {
    def on(event: String, listener: js.Function1[js.Any, Unit]): ReadableStream = js.native
  }

  @js.native
  trait WritableStream extends js.Object {
    def write(chunk: String): Boolean = js.native
  }

  @js.native
  trait SpawnOptions extends js.Object

  object SpawnOptions {
    def apply(cwd: String): SpawnOptions =
      js.Dynamic
        .literal(cwd = cwd, stdio = js.Array("pipe", "pipe", "pipe"))
        .asInstanceOf[SpawnOptions]
  }

  def spawn(command: String, args: js.Array[String], cwd: String): ChildProcess = {

    ChildProcessModule.spawn(command, args, SpawnOptions(cwd))
  }

}
