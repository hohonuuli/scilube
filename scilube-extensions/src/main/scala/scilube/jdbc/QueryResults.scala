package scilube.jdbc

import scala.collection.mutable.{ArrayBuffer}
import java.util.Date
import java.sql.{ResultSet, Time, Timestamp, Types}

/**
 * Utility class that reads a ResultSet into a series of Arrays, one array for each column. Types will
 * need to be cast after reading. Here's some cast suggestions:
 *
 * {{{
 * switch sqlType =
 *    case Types.BIGINT => Long
 *    case Types.BIT => Boolean
 *    case Types.BOOLEAN => Boolean
 *    case Types.CHAR => String
 *    case Types.DATE => Date
 *    case Types.DECIMAL => BigDecimal
 *    case Types.DOUBLE => Double
 *    case Types.FLOAT => Float
 *    case Types.INTEGER => Int
 *    case Types.NCHAR => String
 *    case Types.NUMERIC => BigDecimal
 *    case Types.REAL => Float
 *    case Types.SMALLINT => Short
 *    case Types.TIME => Time
 *    case Types.TIMESTAMP => Timestamp
 *    case Types.TINYINT => Short
 *    case Types.VARCHAR => String
 *    case _ => Object
 * }}}
 * @author Brian Schlining
 * @since 2012-02-15
 *
 * @param resultSet The [[java.sql.ResultSet]] to process
 */
class QueryResults(resultSet: ResultSet) extends Dataset {

  private[this] val (columns, dataBuffers, _columnCount, _rowCount, _sqlColumnTypes) = {
    val metadata = resultSet.getMetaData
    val colCount = metadata.getColumnCount
    var rowCount = 0
    val columnLabels = Array.ofDim[String](colCount)
    val columnTypes = Array.ofDim[Int](colCount)
    val columnClassNames = Array.ofDim[String](colCount)
    val dataStores = Array.ofDim[ArrayBuffer[Any]](colCount)
    (0 until colCount).foreach { i =>
      columnLabels(i) = metadata.getColumnLabel(i + 1)
      columnClassNames(i) = metadata.getColumnClassName(i + 1).toLowerCase()
      columnTypes(i) = metadata.getColumnType(i + 1)
      dataStores(i) = new ArrayBuffer[Any]
    }

    // -- Read the results.
    while (resultSet.next()) {
      var i = 0
      while (i < colCount) { // In Scala, 'while' is much faster than 'for'
        columnClassNames(i) match {
          case "oracle.sql.timestamp" =>
            dataStores(i) += resultSet.getTimestamp(i + 1)
          case _ => dataStores(i) += resultSet.getObject(i + 1)
        }
        i += 1
      }
      rowCount += 1
    }

    val dataArrays = dataStores.map { ab =>
      val array = Array.ofDim[Any](rowCount)
      ab.copyToArray(array)
      array
    }
    (columnLabels, dataArrays, colCount, rowCount, columnTypes)
  }

  def columnNames: Array[String] = columns.toArray

  /**
   *
   * @param columnName The name of the column o interest (see __columneNames__)
   * @return An array of data for the column of interest. TODO should return an option if no match is found None
   */
  def data(columnName: String): Array[Any] =
    dataBuffers(columns.indexOf(columnName))

  def columnCount = _columnCount
  def rowCount = _rowCount

  /**
   * Get the SQL integer code of the type that the column contains
   * @param columnName
   * @return The SQL Type
   * @see [[java.sql.Types]]
   */
  def sqlType(columnName: String): Int =
    _sqlColumnTypes(columns.indexOf(columnName))

}
