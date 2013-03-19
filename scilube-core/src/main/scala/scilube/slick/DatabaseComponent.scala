package scilube.slick

/**
 * 
 * @author Brian Schlining
 * @since 2013-03-15
 */
trait DatabaseComponent { this: DatabaseProfile =>

  import scala.slick.driver.BasicDriver.simple._

  /**
   * Default table columns and operations.
   */
  abstract class Mapper[A, T <: Entity[A, T]](table: String) extends Table[T](None, table) {

    // -- operations on rows
    def delete(primaryKey: A)(implicit session: Session): Boolean

    private lazy val findAllQuery = for (entity <- this) yield entity

    def findAll(implicit session: Session): List[T] = findAllQuery.list

    def findByPrimaryKey(primaryKey: A)(implicit session: Session): Option[T]

  }

}

