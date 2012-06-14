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


    /**
     * Emperical cumulative density function. This makes no assumptions about the data
     *
     * @param data Data set to generate a CDF for
     * @return A tuple of (cdf, x) where cdf is the cumulative density defined over x
     */
    def tocdf(data: Array[Double]): (Array[Double], Array[Double]) = {
        // Remove missing observations indicated by NaN's
        val s = data.filter(!_.isNaN).sorted
        val binEdges = Double.NegativeInfinity +: s.distinct :+ Double.PositiveInfinity
        val binCounts = histc(s, binEdges)
        val bcSum = binCounts.sum
        val sumCounts = cumsum(binCounts).map(_ / bcSum)
        val c = subset(sumCounts, 0 until (sumCounts.size - 1))
        val (x, is, ix) = unique(data)
        (subset(c, is), x)
    }


    /**
     * Extract a subset of an Array. Example:
     * {{{
     *     // MATLAB equivalent:
     *     // a = 1:10
     *     // idx = [2 3 5 6]
     *     // b = a(idx)
     *     val a = (0 to 10).toArray
     *     val idx = Seq(2, 3, 5, 6)
     *     val b = Matlib.subset(a, idx)
     * }}}
     *
     * '''NOTE:''' This does no bounds checking!! Makes sure your idices are between 0 and data.size
     *
     * @param data The array to subset
     * @param idx Indices into data to exist in the subset
     * @tparam A The Type of the data array
     * @return An a subset of data (e.g. In Matlab
     */
    def subset[A: ClassManifest](data: Array[A], idx: Seq[Int]): Array[A] = {
        (for (i <- 0 until idx.size) yield data(i)).toArray
    }



}
