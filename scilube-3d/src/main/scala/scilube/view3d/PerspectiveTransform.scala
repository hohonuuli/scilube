package scilube.view3d

import org.ejml.data.{Matrix64F, DenseMatrix64F}
import scala.math._




/**
 *
 * @author Brian Schlining
 * @since 2012-08-14
 */

trait PerspectiveTransforms {

    /**
     * Convert Euler's angles to vector. Applies angles in x, y, z order.
     * @param x Euler angle in radians along x axis
     * @param y Euler angle in radians along y axis
     * @param z Euler angle in radians along z axis
     */
    def eulersToVector(x: Double, y: Double, z: Double): Matrix64F = {

        val mx = new DenseMatrix64F(3, 3)

        val sx = sin(x)
        val sy = sin(y)
        val sz = sin(z)
        val cx = cos(x)
        val cy = cos(y)
        val cz = cos(z)

        mx.set(0, 0, cy * cz)
        mx.set(0, 1, -cy * sz)
        mx.set(0, 2, sy)
        mx.set(1, 0, cz * sx * sy + cx * sz)
        mx.set(1, 1, cx * cz - sx * sy * sz)
        mx.set(1, 2, -cy * sx)
        mx.set(2, 0, -cx * cz * sy + sx * sz)
        mx.set(2, 1, cz * sx + cx * sy * sz)
        mx.set(2, 2, cx * cy)

        mx

    }


}
