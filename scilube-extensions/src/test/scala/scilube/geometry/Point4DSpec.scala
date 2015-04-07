package scilube.geometry

import java.sql.Timestamp
import java.util.Date

import org.scalatest.{Matchers, FlatSpec}

/**
 *
 *
 * @author Brian Schlining
 * @since 2015-04-07T13:32:00
 */
class Point4DSpec extends FlatSpec with Matchers {

  "A Point4D" should "be created from factor" in {
    val p = Point4D(10, 11, 12, new Date)
    val t = Point4D(10, 11, 12, new Timestamp(new Date().getTime))
  }

}
