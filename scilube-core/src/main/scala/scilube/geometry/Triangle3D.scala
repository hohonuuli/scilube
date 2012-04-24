package scilube.geometry

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2012-04-23
 * @tparam A The type of data that the points in the triangle represent. ex. Double would mean the
 *           triangle is made of Point3D[Double]
 * @tparam B The return type of the calculations. For example a Triangle3D[Int, Float] would have
 *           points defined by Point3D[Int] but area calculations on it would return Float values.
 */
trait Triangle3D[A, B] {

    /**
     *
     * @return The points (size will be exactly 3) that make up the coordinates of the triangle
     */
    def points: Seq[Point3D[A]]

    /**
     *
     * @return The area of the triangle
     */
    def area: B

    /**
     *
     * @return The perimeter of the triangle
     */
    def perimeter: B

}

object Triangle3D {
    def apply(points: Seq[Point3D[Double]]) = new DoubleTriangle3D(points)

    def apply(p0: Point3D[Double], p1: Point3D[Double], p2: Point3D[Double]) =
        new DoubleTriangle3D(Seq(p0, p1, p2))
}

/**
 *
 * @param points The points that define the 3 corners of the triangle in 3D space.
 */
class DoubleTriangle3D(val points: Seq[Point3D[Double]]) extends Triangle3D[Double, Double] {
    require(points.size == 3, "Triangle needs exactly 3 points, you gave " + points.size)

    lazy val (area, perimeter) = {
        val a = points(1).distance(points(2))
        val b = points(2).distance(points(3))
        val c = points(3).distance(points(1))
        val s = (a + b + c) / 2
        val _area = sqrt(s * (s - a) * (s - b) * (s - c))
        val _perimeter = a + b + c
        (_area, _perimeter)
    }

}
