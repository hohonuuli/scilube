package scilube.view3d.canadiangrid

import scala.math._
import org.scalatest.junit.JUnitRunner
import org.junit.runner.RunWith
import org.scalatest.{FunSpec, FunSuite}
import org.scalatest.Matchers

/**
 *
 * @author Brian Schlining
 * @since 2012-12-06
 */
@RunWith(classOf[JUnitRunner])
class CameraTest extends FunSpec with Matchers {

  private[this] val percentError = 0.03

  private[this] val camera = new Camera(112, toRadians(33.62), toRadians(42), toRadians(39))

  describe("A Camera") {

    it("Should throw an IllegalArgumentException with invalid angles") {
      intercept[IllegalArgumentException] {
        new Camera(10, 0, 0.1, 0.1)
      }
      intercept[IllegalArgumentException] {
        new Camera(10, Pi + 0.1, 0.1, 0.1)
      }
      intercept[IllegalArgumentException] {
        new Camera(10, 0.1, 0.1, 0)
      }
      intercept[IllegalArgumentException] {
        new Camera(10, 0.1, 0.1, Pi / 2 + 0.1)
      }
    }

    it("Should correctly calculate planeDistance") {
      val expected = 138.3085
      val range = expected * percentError
      camera.planeDistance should be (expected +- range)
    }

    it("Should correctly calculate lensDistance") {
      val expected = 177.9698
      val range = expected * percentError
      camera.lensDistance should be (expected +- range)
    }

    it("Should correctly calculate nearViewEdgeDistance") {
      val expected = 76.1151
      val range = expected * percentError
      camera.nearViewEdgeDistance should be (expected +- range)
    }

    it("Should correctly calculate farFarEdgeDistance") {
      val expected = 274.4476
      val range = expected * percentError
      camera.farViewEdgeDistance should be (expected +- range)
    }

    it("Should correctly calculate nearViewEgdeDistance") {
      val expected = 76.1151
      val range = expected * percentError
      camera.nearViewEdgeDistance should be (expected +- range)
    }

    it("Should correctly calculate viewWidth") {
      val expected = 136.6324
      val range = expected * percentError
      camera.viewWidth should be (expected +- range)
    }

    it("Should correctly calculate viewHeight") {
      val expected = 198.3325
      val range = expected * percentError
      camera.viewHeight should be (expected +- range)
    }

  }

}
