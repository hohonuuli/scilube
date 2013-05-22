package scilube.gis.simple

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers
import scilube.Matlib._

/**
 *
 * @author Brian Schlining
 * @since 2013-05-21
 */
@RunWith(classOf[JUnitRunner])
class ASCGridReaderTest extends FunSpec with ShouldMatchers {

  val url = getClass.getResource("/mockup.asc")
  val tolerance = 0.00000001

  describe("An ASCII Grid Reader") {

    it ("should read in a grid without an error") {
      val grid = ASCGridReader.read(url)
    }

    it ("should read in the x axes correctly") {
      val grid = ASCGridReader.read(url)
      grid.x should have length 9
      grid.x should be (linspace(400000.0000000, 400000.0000000 + 500.0 * 8, 9).toIndexedSeq)
    }

    it ("should read in the y axes correctly") {
      val grid = ASCGridReader.read(url)
      grid.y should have length 12
      grid.y should be (linspace(3000000.0000000, 3000000.0000000 + 500.0 * 11, 12).toIndexedSeq.reverse)
    }

    it ("should read the nodata values correctly") {
      val grid = ASCGridReader.read(url)
      assert(grid.z(8, 0).isNaN)
    }

    it ("should read the z values correctly") {
      val grid = ASCGridReader.read(url)
      grid.z(0, 0) should be (110.0 plusOrMinus tolerance)
      grid.z(1, 1) should be (101.0 plusOrMinus tolerance)
      grid.z(2, 8) should be (32.0 plusOrMinus tolerance)
    }
  }


}
