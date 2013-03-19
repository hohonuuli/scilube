package scilube.slick

/**
 *
 * @tparam A The type of the primaryKey
 * @tparam B The Type of the entity
 * @author Brian Schlining
 * @since 2013-03-15
 *
 */
trait Entity[A, B <: Entity[A, B]] {

  def primaryKey: A

}
