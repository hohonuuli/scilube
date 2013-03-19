package scilube

import scala.slick.lifted.{MappedTypeMapper}
import java.sql.{Date, Timestamp}

/**
 *
 * @author Brian Schlining
 * @since 2013-03-15
 */
package object slick {

  implicit val dateTypeMapper = MappedTypeMapper.base[Date, Timestamp](
    { t => new Timestamp(t.getTime) }, { u => new Date(u.getTime) })


  implicit val booleanTypeMapper = MappedTypeMapper.base[Boolean, Int](
    {t => if (t) 1 else 0}, { u => if (u == 0) false else true})

}
