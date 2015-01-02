package scilube.gis

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.Matchers

/**
 *
 * @author Brian Schlining
 * @since 2013-05-20
 */
@RunWith(classOf[JUnitRunner])
class CRSConverterUTMToGeoTest extends FunSpec with Matchers {

  describe("Geographic (WGS84) to UTM Zone 10N coordinate reference system conversion") {

    it("should calculate the position correctly") {
      val (longitude, latitude) = CRSConverterUTMToGeo(500000.0, 4649776.22482)
      longitude should be (-123.0 +- 0.001)
      latitude should be (42.0 +- 0.001)
    }

  }

}
