package scilube.imglib2.awt

import scala.collection.mutable.{ListBuffer}
import scala.math._
import java.awt.geom.{AffineTransform, Ellipse2D}
import java.awt.Shape
import scilube.geometry._


/**
 * A container for pixels that represent a particular region of an image. This is a ShortRegion
 * as it represents Short (16 bit) (actually [[scala.Int]]), pixels
 *
 * @param label the label identifier for the region
 * @param points A mutable collection of points that back this short region
 * @author Brian Schlining
 * @since 2012-03-07
 */
class ShortRegion protected(val label: Int, val points: ListBuffer[Point3D[Int]]) {

    /**
     * Adds a pixel to this region
     * @param x x-coordinate of pixel
     * @param y y-coordinate of pixel
     * @param z z-value of pixel
     */
    def addPixel(x: Int, y: Int, z: Int) {addPixel(Point3D(x, y, z))}

    /**
     * Add a pixel to this regions
     * @param point point location of pixel. Z-value is pixel intensity
     */
    def addPixel(point: Point3D[Int]) {points += point}

    /**
     * Calculates a central moment.
     * @param p
     * @param q
     * @return
     */
    def centralMoment(p: Int, q: Int) = {
        val m00 = moment(0, 0) // region area
        val xCtr = moment(1, 0) / m00
        val yCtr = moment(0, 1) / m00
        points.map(i => pow(i.x.toDouble - xCtr, p) * pow(i.y.toDouble - yCtr, q)).sum
    }

    /**
     * Calculates a normalized central moment
     * @param p
     * @param q
     * @return
     */
    def normalizedCentralMoment(p: Int,  q: Int) = {
        val m00 = moment(p, q)
        val norm = pow(m00, (p + q + 2) / 2D)
        centralMoment(p, q) / norm
    }

    /**
     * Calculate the centroid using moments.
     *
     * @return A point that contains the coordinates of the centroid.
     */
    def centroid(): LabeledDoublePoint2D = {
        val m00 = moment(0, 0)
        val m10 = moment(1, 0)
        val m01 = moment(0, 1)
        val x = round(m10 / m00)
        val y = round(m01 / m00)
        Point2D(x, y, label)
    }

    /**
     * Calculates a rectangular envelope that contains all pixels
     * @return An Envelope
     * TODO replace Envelope with scilube's envelope
     */
    def envelope(): Envelope = {
        assert(points.size > 2, "At least 3 points are required to calculate an envelope")
        val left = points.minBy(_.x).x
        val right = points.maxBy(_.x).x
        val top = points.maxBy(_.y).y
        val bottom = points.minBy(_.y).y
        new SpatialEnvelope(left, top, right, bottom)
    }

    /**
     * Calculate the centroid. This method caches the result until a pixel is added, then the
     * result is recalculated.
     *
     * @return A point that contains the coordinates of the centroid.
     */
    def centroidAlt(): LabeledDoublePoint2D = {
        val x = points.map(_.x.toDouble).sum / points.size
        val y = points.map(_.y.toDouble).sum / points.size
        Point2D(x, y, label)
    }

    /**
     * Calculates a moment of the region
     * @param p
     * @param q
     * @return
     */
    def moment(p: Int, q: Int) = points.map(i => pow(i.x.toDouble, p) * pow(i.y.toDouble, q)).sum

    /**
     * Calculate the eccentricity of a region.
     *
     * @return Tuple where 1 = eccentricity, 2 = length of major axis, 3 = length of minor axis
     */
    def eccentricity() = {
        val m20 = centralMoment(2, 0)
        val m02 = centralMoment(0, 2)
        val m11 = centralMoment(1, 1)
        val a1 = m20 + m02 + sqrt(pow(m20 - m02, 2) + 4 * m11 * m11)
        val a2 = m20 + m02 - sqrt(pow(m20 - m02, 2) + 4 * m11 * m11)
        val e = a1 / a2
        val ra = sqrt(2 * a1 / points.size)
        val rb = sqrt(2 * a2 / points.size)
        (e, ra, rb)
    }

    /**
     * Generate a ellipse shape that visually represents the eccentricity of a region.
     *
     * @return
     */
    def ellipse(): Shape = {
        val c = centroid()
        val (e, ra, rb) = eccentricity()
        val x = c.x - ra
        val y = c.y - rb
        val angle = orientation() + Pi / 2
        val rawEllipse = new Ellipse2D.Double(x, y, 2 * ra, 2 * rb)
        val transform = AffineTransform.getRotateInstance(angle, c.x, c.y)
        transform.createTransformedShape(rawEllipse)
    }

    /**
     * @return The orientation angle of the ellipse in radians.
     */
    def orientation() = 1D / 2D * atan(2 * centralMoment(1, 1) / (centralMoment(2, 0) - centralMoment(0, 2)))


}


object ShortRegion {
    /**
     * Factory method.
     *
     * @param label Label for the ShortRegion
     * @return A ShortRegion with the provided label and an empty internal
     *      list of points.
     */
    def apply(label: Int) = new ShortRegion(label, new ListBuffer[Point3D[Int]])

    /**
     * Factory method
     *
     * @param label Label for the ShortRegion
     * @param points Points to be copied into the ShortRegions internal list.
     * @return A ShortRegion with the provided label and pixels defined by the points collection
     */
    def apply(label: Int, points: Iterable[Point3D[Int]]) = {
        val buf = new ListBuffer[Point3D[Int]]
        buf ++= points
        new ShortRegion(label, buf)
    }
}
