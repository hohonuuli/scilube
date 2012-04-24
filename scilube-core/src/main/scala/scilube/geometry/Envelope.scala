package scilube.geometry


import java.awt.geom.Rectangle2D
import scala.math._

/**
 * Represents a rectangular area
 * @author Brian Schlining
 * @since 2011-05-15
 */
trait Envelope {

    def contains[B](p: Point2D[B])(implicit numeric: Numeric[B]): Boolean

    def width: Double

    def height: Double

    def minimum: Point2D[Double]

    def maximum: Point2D[Double]

    def toRectangle: Rectangle2D
}

/**
 * Reusable implementation of a contains method for envelopes. Based on implemntation of minimum
 * and maximum in Envelope.
 */
trait EnvelopeContains {
    self: Envelope =>

    def contains[B](p: Point2D[B])(implicit numeric: Numeric[B]): Boolean = {
        val minX = minimum.x
        val maxX = maximum.x
        val minY = minimum.y
        val maxY = maximum.y
        val x = numeric.toDouble(p.x)
        val y = numeric.toDouble(p.y)

        (x >= minX) && (x <= maxX) && (y >= minY) && (y <= maxY);
    }

}

/**
 * Reusable implementation of toRectangle method for envelopes
 */
trait EnvelopeToRectangle {
    self: Envelope =>

    def toRectangle: Rectangle2D = new Rectangle2D.Double(minimum.x, minimum.y, width, height);
}

/**
 * Our basic envelop implementation
 */
class SpatialEnvelope(val corner0: Point2D[Double], val corner1: Point2D[Double]) extends Envelope
        with EnvelopeContains with EnvelopeToRectangle {

    def this(x0: Double, y0: Double, x1: Double, y1: Double) = this(new DoublePoint2D(x0, y0), new DoublePoint2D(x1, y1))

    private val corners = {
        val minX = min(corner0.x, corner1.x)
        val maxX = max(corner0.x, corner1.x)
        val minY = min(corner0.y, corner1.y)
        val maxY = max(corner0.y, corner1.y)
        (new DoublePoint2D(minX, minY), new DoublePoint2D(maxX, maxY))
    }

    def maximum: Point2D[Double] = corners._2.asInstanceOf[Point2D[Double]]

    def minimum: Point2D[Double] = corners._1.asInstanceOf[Point2D[Double]]

    lazy val height: Double = maximum.x - minimum.x

    lazy val width: Double = maximum.y - minimum.y
}