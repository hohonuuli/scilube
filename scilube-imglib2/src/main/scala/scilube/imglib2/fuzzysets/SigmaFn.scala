package scilube.imglib2.fuzzysets

import scala.math._
/**
 * Fuzzy set membership function; Sigma
 *
 * See page 177 and 178 of Digital Image Processing 3rd Edition, Gonzalez and Woods
 * {{{
 *             a (one)
 *             -------
 *           /
 *         /
 *       /
 *  ----
 *     b (zero)
 *
 * }}}
 *
 * @author Brian Schlining
 * @since 2012-03-13
 *
 * @param one value above which z = 1
 * @param zero value below which z = 0. zero < one is valid, we just flip the sigma calculation
 */

class SigmaFn(val zero: Double, val one: Double) extends (Double => Double) {
    
    private[this] val a = one
    private[this] val b = abs(one - zero)

    def apply(z: Double): Double = {
        if (one > zero) {
            if (zero <= z  && z <= one) {
                1 - (a - z) / b
            }
            else if (z > one) {
                1
            }
            else {
                0
            } 
        }
        else {
            if (z <= zero && one <= z) {
                1 - (z - a) / b
            }
            else if (one > z) {
                1
            }
            else {
                0
            }
        }
    }
}
