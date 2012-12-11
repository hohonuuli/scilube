package scilube.ocean

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2011-11-29
 */

trait Light {

    /**
     * Convert beam attenuation (/m) to percent beam transmission
     *
     * @param c beam attenuation coefficient (/m)
     * @param pathLength m (default = 1.0 m)
     * @return beam transmission (percentage per pathlength)
     */
    def c2t(c: Double,  pathLength: Double = 1.0) = 100 * exp(-c * pathLength)

    /**
     * Convert beam transmission to beam attenuation
     *
     * @param t beam transmission (percentage per pathlength)
     * @param pathLength m (default = 1.0 m)
     * @return c beam attenuation coefficient (/m)
     */
    def t2c(t: Double,  pathLength: Double) = -(log(t / 100) / pathLength)

}