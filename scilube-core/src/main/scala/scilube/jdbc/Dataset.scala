package scilube.jdbc
/**
 *
 * @author Brian Schlining
 * @since 2012-02-15
 */

trait Dataset {

    def columnNames: Array[String]

    def data(columnName: String): Array[Any]

    def rowCount: Int
    def columnCount: Int
}
