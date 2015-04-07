package scilube.geometry

/**
 * 4-D point. The 3 spatial dimensions must all be the same type. The 4 dimension can be a different type.
 * Following 3D conventions the dimensions are x, y, z, and w
 * @tparam A The type of the 3 spatial dimensionssp
 * @tparam B the type of the 4-th dimension
 */
trait Point4D[A, B] extends Point3D[A] {
  def w: B

  override lazy val toString: String = x + "," + y + "," + z + "," + w

  override def hashCode(): Int = this.toString.hashCode

  override def equals(obj: Any): Boolean = obj match {
    case that: Point4D[_, _] => super.equals(that) && w.equals(that.w)
    case _ => false
  }
}

object Point4D {
  def apply[A](x: Double, y: Double, z: Double, w: A) = new DoublePoint4D[A](x, y, z, w)
}

class DoublePoint4D[A](x: Double, y: Double, z: Double, val w: A)
    extends DoublePoint3D(x, y, z) with Point4D[Double, A]

class IntPoint4D[B](x: Int, y: Int, z: Int, val w: B)
    extends IntPoint3D(x, y, z) with Point4D[Int, B]

