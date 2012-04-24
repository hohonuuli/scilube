package scilube.geometry


import java.awt.geom.{Point2D => JPoint2D}
import scala.math._
import java.lang.String


/**
 * 4-D point. The 3 spatial dimensions must all be the same type. The 4 dimension can be a different type
 * @tparam A The type of the 3 spatial dimensionssp
 * @tparam B the type of the 4-th dimension
 */
trait Point4D[A, B] extends Point3D[A] {
    def t: B

    override lazy val toString: String = x + "," + y + "," + z + "," + t

    override def hashCode(): Int = this.toString.hashCode

    override def equals(obj: Any): Boolean = obj match {
        case that: Point4D[_, _] => super.equals(that) && t.equals(that.t)
        case _ => false
    }
}


class DoublePoint4D[A](x: Double, y: Double, z: Double, val t: A) extends DoublePoint3D(x, y, z) with Point4D[Double, A]

class IntPoint4D[B](x: Int, y: Int, z: Int, val t: B) extends IntPoint3D(x, y, z) with Point4D[Int, B]



