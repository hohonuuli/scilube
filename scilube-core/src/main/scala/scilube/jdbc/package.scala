package scilube

import java.sql.ResultSet
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
     * @param rowMapper Maps the result set to a single object. Do not call resultSet.next in this
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

}
