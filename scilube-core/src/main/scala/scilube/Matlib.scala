package scilube

import _root_.spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import geometry.Trigonometry
import probability.KDE
import scala.math._
import scala.util.Random
import org.mbari.math.{DoubleMath, Statlib, Matlib => JMatlib}
import reflect.ClassTag


/**
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object Matlib
        extends Mathematics
        with Probabilities
        with Statistics
        with Trigonometry {

    /**
     * Calculate the relative cumulative density between 2 sample sets. Internally the CDF is
     * calculated using the empirical CDF, `tocdf`
     * @param y Samples from the comparison outcome space
     * @param y0 Samples from a reference outcome space
     * @param r The relative density at r
     * @return The relative cumaltive density
     * @see ‘‘Relative Distribution Methods in the Social Sciences’’ by Mark S. Handcock and
     *      Martina Morris, Springer-Verlag, 1999,Springer-Verlag, ISBN 0387987789
     */
    def relativecdf(y: Array[Double], y0: Array[Double], r: Array[Double]): Array[Double] = {
        val (c, x) = tocdf(y)
        val (c0, x0) = tocdf(y0)
        val q0 = quantile(y0, r)        // value of y0 at r
        val f0 = interp1(x0, c0, q0)    // value of c0 at q0(r), i.e. probability density
        val f = interp1(x, c, q0)       // value of c at q0(r)
        (for (i <- 0 until f.size) yield f(i) / f0(i)).toArray
    }

    /**
     * Calculate relative probability density between 2 sample sets. Internally the PDF is
     * calculated using [[scilube.probability.KDE]].
     *
     * @param y Samples from the comparison outcome space
     * @param y0 Samples from a reference outcome space
     * @param r The proportion (e.g. probability of y / y0 values relative to q0(r) where
     *          0 <- r(n) <= 1
     * @return The relative density at r
     * @see ‘‘Relative Distribution Methods in the Social Sciences’’ by Mark S. Handcock and
     *      Martina Morris, Springer-Verlag, 1999,Springer-Verlag, ISBN 0387987789
     */
    def relativepdf(y: Array[Double], y0: Array[Double], r: Array[Double]): Array[Double] = {
        val min_ = min(y.min, y0.min)
        val max_ = max(y.max, y0.max)
        val n = max(y.size, y0.size) * 2
        val p = KDE(y, n, min_, max_)
        val p0 = KDE(y0, n, min_, max_)
        val q0 = quantile(y0, r) // Value of y0 at r
        val f0 = interp1(p0.x, p0.pdf, q0) // value of f0 at q0(r) i.e. probability density
        val f = interp1(p.x, p.pdf, q0) // value of f at q0(r)
        (for (i <- 0 until f.size) yield f(i) / f0(i)).toArray
    }


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
        (c, x)
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
    def subset[A: ClassTag](data: Array[A], idx: Seq[Int]): Array[A] = {
        (for (i <- 0 until idx.size) yield data(idx(i))).toArray
    }



}
