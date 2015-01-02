package scilube.geometry

import scala.math._

/**
 * 3D point.
 */
trait Point3D[A] extends Point2D[A] {
  def z: A

  def distance(that: Point3D[A])(implicit numeric: Numeric[A]): Double = {
    val x1 = numeric.toDouble(that.x)
    val y1 = numeric.toDouble(that.y)
    val z1 = numeric.toDouble(that.z)
    val x0 = numeric.toDouble(x)
    val y0 = numeric.toDouble(y)
    val z0 = numeric.toDouble(z)
    val dx = pow(x1 - x0, 2)
    val dy = pow(y1 - y0, 2)
    val dz = pow(z1 - z0, 2)
    sqrt(dx + dy + dz)
  }

  def direction(point: Point3D[A])(implicit numeric: Numeric[A]): Double = {
    throw new UnsupportedOperationException("Not implemented yet")
  }

  override lazy val toString: String = x + "," + y + "," + z

  override def hashCode(): Int = this.toString.hashCode

  override def equals(obj: Any): Boolean = obj match {
    case that: Point3D[_] => super.equals(that) && z.equals(that.z)
    case _ => false
  }
}

class DoublePoint3D(x: Double, y: Double, val z: Double) extends DoublePoint2D(x, y) with Point3D[Double]

class IntPoint3D(x: Int, y: Int, val z: Int) extends IntPoint2D(x, y) with Point3D[Int]

object Point3D {
  def apply(x: Int, y: Int, z: Int) = new IntPoint3D(x, y, z)

  def apply(x: Double, y: Double, z: Double) = new DoublePoint3D(x, y, z)
}

