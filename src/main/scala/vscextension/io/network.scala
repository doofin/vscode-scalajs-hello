package vscextension.io

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.util.{Failure, Success}

import vscextension.facade.NodeFetch.*
import vscextension.facade.vscodeUtils.*
import typings.vscodeLanguageclient.libCommonClientMod.SuspendMode.on

/** network utilities for network operations, like http requests
 * 
 * see https://github.com/node-fetch/node-fetch
  */
object network {

  def httpGet = {
    NodeFetch("https://github.com/").toFuture onComplete {
      case Success(res) =>
        println(s"ok => ${res.ok}")
        println(s"status => ${res.status}")
        println(s"statusText => ${res.statusText}")

        val body = res.text().toFuture foreach { body =>
          showMessageAndLog(body.toString)
        }
      case Failure(e) =>
    }
  }
}
