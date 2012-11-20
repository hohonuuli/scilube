package scilube.view3d

import scala.math._
import matlube.{MatrixFactory, Matrix}


/**
 *
 * @author Brian Schlining
 * @since 2012-08-14
 */

trait PerspectiveTransforms[A <: Matrix[A]] {


    def factory: MatrixFactory[A]

    /**
     * Convert Euler's angles to vector. Applies angles in x, y, z order.
     * @param x Euler angle in radians along x axis
     * @param y Euler angle in radians along y axis
     * @param z Euler angle in radians along z axis
     */
    def eulersToVector(x: Double, y: Double, z: Double): A = {

        val mx = factory.zeros(3, 3)

        val sx = sin(x)
        val sy = sin(y)
        val sz = sin(z)
        val cx = cos(x)
        val cy = cos(y)
        val cz = cos(z)

        mx(0, 0) = cy * cz
        mx(0, 1) = -cy * sz
        mx(0, 2) = sy
        mx(1, 0) = cz * sx * sy + cx * sz
        mx(1, 1) =  cx * cz - sx * sy * sz
        mx(1, 2) = -cy * sx
        mx(2, 0) = -cx * cz * sy + sx * sz
        mx(2, 1) = cz * sx + cx * sy * sz
        mx(2, 2) = cx * cy

        mx

    }


}
