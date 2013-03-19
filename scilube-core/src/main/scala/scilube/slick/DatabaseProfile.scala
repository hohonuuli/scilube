package scilube.slick

import scala.slick.driver.ExtendedProfile
import scala.slick.session.Database

/**
 *
 * @author Brian Schlining
 * @since 2013-03-15
 */
trait DatabaseProfile {

  def driver: ExtendedProfile
  def database: Database

}
