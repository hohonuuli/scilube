package scilube

import java.text.{DateFormat, SimpleDateFormat}
import java.util.{Date, TimeZone}

/**
 *
 * @author Brian Schlining
 * @since 2013-09-11
 */
package object time {

  private[this] val dateFormat = iso8601DateFormat

  /**
   * Convert an iso8601 string to a date object. Examples strings include:
   * {{{
   *   "2013-09-11T14:11:25Z"
   *   "2013-09-11T14:11:25-00"
   *   "2013-09-11T14:11:25-08"
   *   "2013-09-11T14:11:25-0800"
   *   "2013-09-11T14:11:25-08:00"
   * }}}
   * @param s
   * @return
   */
  def toDate(s: String): Date = dateFormat.parse(s)

  /**
   * Create a new instance of a [java.text.DateFormat] for 
   * ISO8601 convetion
   */
  def iso8601DateFormat: DateFormat = {
    val df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssX")
    df.setTimeZone(TimeZone.getTimeZone("UTC"))
    df
  }

}
