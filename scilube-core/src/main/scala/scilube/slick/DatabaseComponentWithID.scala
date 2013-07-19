package scilube.slick

/**
 * 
 * @author Brian Schlining
 * @since 2013-03-15
 */
trait DatabaseComponentWithID { this: DatabaseProfile =>

  import scala.slick.driver.BasicDriver.simple._

  /**
   * Default table columns and operations.
   */
  abstract class MapperWithID[T <: EntityWithID[T]](table: String) extends Table[T](None, table) {

    // -- table columns
    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    // -- helpers
    protected def autoInc = * returning id // FIXME: Can't map id into insert projection

    // -- operations on rows
    def delete(id: Long)(implicit session: Session): Boolean = this.filter(_.id === id).delete > 0

    private lazy val findAllQuery = for (entity <- this) yield entity

    def findAll(implicit session: Session): List[T] = findAllQuery.list

    private lazy val findByIdQuery = for {
      id <- Parameters[Long]
      e <- this if e.id === id
    } yield e

    def findById(id: Long)(implicit session: Session): Option[T] = findByIdQuery(id).firstOption

    def insert(entity: T)(implicit session: Session): T = {
      val id = autoInc.insert(entity)
      entity.withId(id)
    }

    def update(entity: T)(implicit session: Session): T = {
      entity.id.map { id =>
        this.filter(_.id === id).update(entity)
      }
      entity
    }
  }

}
