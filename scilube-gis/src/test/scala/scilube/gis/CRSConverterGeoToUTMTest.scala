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
class CRSConverterGeoToUTMTest extends FunSpec with Matchers {

  describe("Geographic (WGS84) to UTM Zone 10N coordinate reference system conversion") {

    it("should calculate the position correctly") {
      val (easting, northing) = CRSConverterGeoToUTM(-123, 42)
      easting should be (500000.0 +- 0.001)
      northing should be (4649776.22482 +- 0.001)
    }

  }

}
