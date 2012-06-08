package scilube

import _root_.spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import scala.math._
import scala.util.Random
import org.mbari.math.{DoubleMath, Statlib, Matlib => JMatlib}


/**
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object Matlib
        extends Mathematics
        with Probabilities
        with Statistics {









    /*
     * Find maximum value in an array along with it's index.
     * @param data A 1-D data array
     * @tparam A A numeric type
     * @return Tuple of (maximum value, index of maximum value)
     */
    //    def max[A : Numeric](data: Array[A]): (A, Int) = {
    //        val numeric = implicitly[Numeric[A]]
    //        var max = data(0)
    //        var maxIdx = 0
    //        for (i <- 1 until data.size) {
    //            if (numeric.gt(data(i), max)) {
    //                max = data(i)
    //                maxIdx = i
    //            }
    //        }
    //        (max, maxIdx)
    //    }


    /*
     * Find minimum value in an array along with it's index.
     * @param data A 1-D data array
     * @tparam A A numeric type
     * @return Tuple of (minimum value, index of minimum value)
     */
    //    def min[A : Numeric](data: Array[A]): (A, Int) = {
    //        val numeric = implicitly[Numeric[A]]
    //        var min = data(0)
    //        var minIdx = 0
    //        for (i <- 1 until data.size) {
    //            if (numeric.lt(data(i), min)) {
    //                min = data(i)
    //                minIdx = i
    //            }
    //        }
    //        (min, minIdx)
    //    }







}
