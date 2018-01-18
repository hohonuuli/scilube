package scilube.jdbc

import java.io.File
import java.io.FileInputStream
import java.util.Properties
import org.slf4j.LoggerFactory
import scala.util.{Failure, Success, Try}

trait DatabaseParams {
  def url: String
  def driver: String
  def username: String
  def password: String
}

case class DBParams(url: String, driver: String, username: String, password: String)
    extends DatabaseParams {

  Class.forName(driver) // Initialize driver.
}

/**
 * Utilities for DatabaseParams
 */
object DatabaseParams {

  /**
   * Load database params from a properties file that has the following:
   * {{{
   *  jdbc.url=[your jdbc url]
   *  jdbc.username=
   *  jdbc.password=
   *  jdbc.driver=
   * }}}
   * @param file The properties file
   */
  def fromProps(file: File): Option[DatabaseParams] =
    Try {
      val prop = new Properties()
      val input = new FileInputStream(file)
      prop.load(input)
      prop
    } match {
      case Success(p) =>
        Option(
            DBParams(
                p.getProperty("jdbc.url"),
                p.getProperty("jdbc.driver"),
                p.getProperty("jdbc.username"),
                p.getProperty("jdbc.password")
            ))
      case Failure(e) => {
        val log = LoggerFactory.getLogger(getClass)
        log.error(s"Unable to load database properties from ${file.getAbsolutePath}", e)
        None
      }
    }
}
