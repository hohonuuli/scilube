package scilube.view3d.canadiangrid

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2012-12-07
 */
@RunWith(classOf[JUnitRunner])
class PixelTest extends FunSpec with Matchers {

  private[this] val camera = new Camera(112, toRadians(33.62), toRadians(42), toRadians(39))
  private[this] val corners = Pixel.imageCorners(camera, 800, 600)
  private[this] val percentError = 0.01

  describe("A Pixel") {

    it("Should correctly calculate alpha") {
      val expected = 0.2932
      val range = expected * percentError
      corners(0).alpha should be (expected +- range)
      corners(1).alpha should be (expected +- range)
      corners(2).alpha should be (-expected +- range)
      corners(3).alpha should be (-expected +- range)
    }

    it("Should correctly calculate beta") {
      val expected = 0.3665
      val range = expected * percentError
      corners(0).beta should be (-expected +- range)
      corners(1).beta should be (expected +- range)
      corners(2).beta should be (expected +- range)
      corners(3).beta should be (-expected +- range)
    }

    it("Should correctly calculate xDistance") {
      val range = 0.5
      val xTop = 113.52
      val xBottom = 51.98
      corners(0).xDistance should be (-xTop +- range)
      corners(1).xDistance should be (xTop +- range)
      corners(2).xDistance should be (xBottom +- range)
      corners(3).xDistance should be (-xBottom +- range)
    }

    it("Should correctly calculate yDistance") {
      val range = 0.5
      val yTop = 274.45
      val yBottom = 76.12
      corners(0).yDistance should be (yTop +- range)
      corners(1).yDistance should be (yTop +- range)
      corners(2).yDistance should be (yBottom +- range)
      corners(3).yDistance should be (yBottom +- range)
    }

  }

}
