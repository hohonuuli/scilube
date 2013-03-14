package scilube.geometry

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2013-01-08
 * @tparam A The type of data that the points in the triangle represent. ex. Double would mean the
 *           triangle is made of Point3D[Double]
 * @tparam B The return type of the calculations. For example a Triangle3D[Int, Float] would have
 *           points defined by Point3D[Int] but area calculations on it would return Float values.
 */
trait Polygon2D[A, B, C <: Point2D[A]] {

  def points: Seq[C]

  def area: B

  def perimeter: B

  def intersects(that: Polygon2D[A, _, C]): Boolean

  def contains(point: C): Boolean

}

object Polygon2D {

  def apply[A <: Point2D[Double]](points: A*) = new DoublePolygon2D(points)

}

class DoublePolygon2D[P <: Point2D[Double]](val points: Seq[P]) extends Polygon2D[Double, Double, P] {
  require(points.size > 2, " A polygon needs 3 or more points, you gave " + points.size)

  lazy val area: Double = {
    val n = points.size
    val i1 = 0 until n
    val i2 = (1 until n) :+ 0
    var p1 = 0D
    var p2 = 0D
    for (i <- 0 until n) {
      p1 = p1 + points(i1(i)).x * points(i2(i)).y
      p2 = p2 + points(i2(i)).x * points(i1(i)).y
    }
    abs((p1 - p2) / 2D)
  }

  def perimeter: Double = throw new UnsupportedOperationException("Not Implemented yet")

  def intersects(that: Polygon2D[Double, _, P]): Boolean =
    throw new UnsupportedOperationException("Not Implemented yet")

  /**
   * @see http://stackoverflow.com/questions/217578/point-in-polygon-aka-hit-test
   * @param point
   * @return
   */
  def contains(point: P): Boolean = {
    throw new UnsupportedOperationException("Not Implemented yet")
  }
}
