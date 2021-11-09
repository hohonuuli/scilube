package org.mbari.scilube3

import java.net.URL
import scala.concurrent.ExecutionContext
import java.net.HttpURLConnection
import scala.concurrent.Future

object HttpUtil {

  def exists(url: URL)(implicit ec: ExecutionContext): Future[Boolean] = Future {
    val connection = url.openConnection.asInstanceOf[HttpURLConnection]
    connection.setRequestMethod("HEAD")
    connection.getResponseCode == 200
  }
}
