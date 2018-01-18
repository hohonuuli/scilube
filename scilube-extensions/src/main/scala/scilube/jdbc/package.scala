package scilube

import java.sql.{Connection, PreparedStatement, ResultSet, Timestamp}
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
    def compare(x: Timestamp, y: Timestamp): Int =
      x.getTime.compareTo(y.getTime)
  }

  implicit def timestampAsDate(t: Timestamp): Date = new Date(t.getTime)
  implicit def dateAsTimestamp(d: Date): Timestamp = new Timestamp(d.getTime)

  //  implicit class RichPreparedStatement(val ps: PreparedStatement) {
  //
  //    private[this] val typeMap = Map(
  //      PreparedStatement.ARRAY -> ((i: Int, x: Any): Unit => ps.setArray(i, x)),
  //      PreparedStatement.BIGINT -> ((i: Int, x: Any) => {}),
  //      PreparedStatement.BINARY -> (i: Int, x: Any) => {},
  //      PreparedStatement.BIT -> (i: Int, x: Any) => ps.setBoolean(i, x.asInstanceOf[Boolean])
  //      PreparedStatement.BLOB -> (i: Int, x: Any) => ps.setBlob(i, x.asInstanceOf[java.sql.Blob]),
  //      PreparedStatement.BOOLEAN -> (i: Int, x: Any) => ps.setBoolean(i, x.asInstanceOf[Boolean]),
  //      PreparedStatement.CHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.CLOB -> (i: Int, x: Any) => ps.setClob(i, x.asInstanceOf[java.sql.Clob]),
  //      PreparedStatement.DATALINK -> (i: Int, x: Any) => {},
  //      PreparedStatement.DATE -> (i: Int, x: Any) => ps.setDate(i, x.asInstanceOf[java.sql.Date]),
  //      PreparedStatement.DECIMAL -> (i: Int, x: Any) => ps.setDouble(i, x.asInstanceOf[Double]),
  //      PreparedStatement.DISTINCT -> (i: Int, x: Any) => {},
  //      PreparedStatement.DOUBLE -> (i: Int, x: Any) => ps.setDouble(i, x.asInstanceOf[Double]),
  //      PreparedStatement.FLOAT -> (i: Int, x: Any) => ps.setFloat(i, x.asInstanceOf[Float]),
  //      PreparedStatement.INTEGER -> (i: Int, x: Any) => ps.setInteger(i, x.asInstanceOf[Integer]),
  //      PreparedStatement.JAVA_OBJECT -> (i: Int, x: Any) => ps.setObject(i, x),
  //      PreparedStatement.LONGNVARCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.LONGVARBINARY -> (i: Int, x: Any) => {},
  //      PreparedStatement.LONGNVARCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.LONGVARCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.NCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.NCLOB -> (i: Int, x: Any) => ps.setNClob(i, x.asInstanceOf[java.sql.NClob]),
  //      PreparedStatement.NULL -> (i: Int, x: Any) => {},
  //      PreparedStatement.NUMERIC -> (i: Int, x: Any)  => ps.setDouble(i, x.asInstanceOf[Double]),
  //      PreparedStatement.NVARCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]),
  //      PreparedStatement.OTHER -> (i: Int, x: Any) => ps.setObject(i, x),
  //      PreparedStatement.REAL -> (i: Int, x: Any)  => ps.setDouble(i, x.asInstanceOf[Double]),
  //      PreparedStatement.REF -> (i: Int, x: Any) => ps.setRef(i, x.asInstanceOf[java.sql.Ref]),
  //      PreparedStatement.ROWID -> (i: Int, x: Any) => ps.setRowId(i, x.asInstanceOf[java.sql.RowId]),
  //      PreparedStatement.SMALLINT -> (i: Int, x: Any) => ps.setInteger(i, x.asInstanceOf[Integer]),
  //      PreparedStatement.SQLXML -> (i: Int, x: Any) => ps.setSQLXML(i, x.asInstanceOf[java.sql.SQLXML]),
  //      PreparedStatement.STRUCT -> (i: Int, x: Any) => {},
  //      PreparedStatement.TIME -> (i: Int, x: Any) => ps.setTime(i, x.asInstanceOf[java.sql.Time]),
  //      PreparedStatement.TIMESTAMP -> (i: Int, x: Any) => ps.setTimestamp(i, x.asInstanceOf[java.sql.Timestamp]),
  //      PreparedStatement.TINYINT -> (i: Int, x: Any) => ps.setInteger(i, x.asInstanceOf[Integer]),
  //      PreparedStatement.VARBINARY -> (i: Int, x: Any) => {},
  //      PreparedStatement.VARCHAR -> (i: Int, x: Any) => ps.setString(i, x.asInstanceOf[String]))
  //
  //    def use(p: Product) {
  //      val md = ps.getParameterMetaData
  //      require(p.productArity >= md.getParameterCount(),
  //        "The tuple does not have enough parameters to fill the preparedstatement")
  //
  //      for (i <- 1 to md.getParameterCount) {
  //        val fn = typeMap.getOrElse(md.getParameterType(i))
  //        fn(i, p.productElement(i - 1))
  //      }
  //
  //    }
  //
  //  }

}
