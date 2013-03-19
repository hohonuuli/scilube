package scilube.slick

/**
 * 
 * @author Brian Schlining
 * @since 2013-03-15
 */
trait EntityWithID[T <: EntityWithID[T]] extends Entity[Option[Long], T] {

  def id: Option[Long]
  def withId(id: Long): T

  def primaryKey = id
}
