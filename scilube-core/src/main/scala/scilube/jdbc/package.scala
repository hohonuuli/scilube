package scilube

import java.sql.{ResultSet, Timestamp}
import java.util.Date
import collection.mutable.ArrayBuffer

/**
 *
 * @author Brian Schlining
 * @since 2013-03-06
 */
package object jdbc {

  implicit class ResultSetRowMapper(resultSet: ResultSet) {

    /**
     * Convert a result set to a sequence of objects. Maps each row in the result set to the provided
     * type.
     *
     * @param rowMapper Maps each row of a result set to an object. Do not call resultSet.next in this
     *      function!! The rowMapper should handle errors internally and always return something
     * @tparam A The type to be returned
     * @return A sequence of objects
     */
    def map[A](rowMapper: ResultSet => A): Seq[A] = {
      // -- Read the results.
      val results = new ArrayBuffer[A]
      while (resultSet.next()) {
        results += rowMapper(resultSet)
      }
      results.toSeq
    }

  }

  /**
   * Timestamps do not have a default ordering so we have to create one so that 
   * they can be sorted
   */
  implicit val timestampOrdering = new Ordering[Timestamp] {
    def compare(x: Timestamp, y: Timestamp): Int = x.getTime.compareTo(y.getTime)
  }

  implicit def timestampAsDate(t: Timestamp): Date = new Date(t.getTime)
  implicit def dateAsTimestamp(d: Date): Timestamp = new Timestamp(d.getTime)

}
