package scilube.jdbc

import java.sql.{ DriverManager, Connection }
import scala.util.{ Failure, Success, Try }
import org.slf4j.LoggerFactory

class Database(url: String, username: String, password: String) {

  def this(params: DatabaseParams) =
    this(params.url, params.username, params.password)

  private[this] val log = LoggerFactory.getLogger(getClass)

  def connection: Connection = DriverManager.getConnection(url, username, password)

  def inTransaction[A](body: Connection => A): Option[A] = Try {
    val c = connection
    c.setAutoCommit(false)
    val a = body(c)
    c.commit()
    c.close()
    a
  } match {
    case Success(a) => Option(a)
    case Failure(e) => {
      log.info("Failed to execute transaction.", e)
      None
    }
  }

}