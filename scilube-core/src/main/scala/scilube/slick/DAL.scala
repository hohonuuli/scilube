package scilube.slick


import slick.lifted.DDL

/**
 * Slick Data Access layer
 *
 * @author Brian Schlining
 * @since 2013-03-15
 */
trait DAL { this: DatabaseProfile =>

  import scala.slick.driver.BasicDriver.simple._

  def ddl: DDL

  def create(implicit session: Session) { ddl.create }

  def drop(implicit session: Session) { ddl.drop}

}
