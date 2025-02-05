package vscextension.io

import scala.scalajs.concurrent.JSExecutionContext.Implicits.queue
import scala.scalajs.js
import scala.util.{Failure, Success}

import vscextension.facade.NodeFetch.*
import vscextension.facade.vscodeUtils.*

/** network utilities for network operations, like http requests
  *
  * see https://github.com/node-fetch/node-fetch
  */
object network {

  def httpGet = {
    val url = "https://github.com/"

    val authToken = "123"
    val requestOptions = new RequestOptions(
      method = "GET",
      headers = js.Dynamic.literal(
        "Content-Type" -> "application/json",
        "Authorization" -> s"Bearer $authToken"
      )
    )

    NodeFetch(url, requestOptions).toFuture onComplete {
      case Success(res) =>
        println(s"ok => ${res.ok}")
        println(s"status => ${res.status}")
        println(s"statusText => ${res.statusText}")

        val body = res.text().toFuture foreach { body =>
          // the body of the response
          showMessageAndLog(body.toString)
        }
      case Failure(e) =>
    }
  }

  def httpGet2 = {
    // use the fetch module generated by scalablytyped
    // fetch.Request(null)
  }
}
