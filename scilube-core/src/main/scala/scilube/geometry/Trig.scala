package scilube.geometry

import org.mbari.math.DoubleMath
import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */

object Trig {

    val TAU = 2 * Pi

    /**
     * Adjusts an angle in radians to fall between 0 and 2 * PI
     *
     * @param radians
     * @return The same angle but adjusted so that if falls between 0 and 2 PI
     */
    def normalizeAngle(radians: Double) = DoubleMath.normalizeRadianAngle(radians)

}
