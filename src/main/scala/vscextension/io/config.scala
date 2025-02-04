package vscextension.io

/* load configuration from directory like .vscode/config.json
 *
 *
 */
object config {
  def loadConfig(dir: String): Unit = {
    println("loading config from " + dir)
    val strRead =
      fileIO.readFile(s"$dir/.vscode/config.json")
    // val json =
  }
}
