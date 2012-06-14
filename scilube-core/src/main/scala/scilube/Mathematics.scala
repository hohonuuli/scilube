package scilube

import _root_.spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import org.mbari.math.{DoubleMath, Matlib => JMatlib, Statlib}
import org.apache.commons.math3.analysis.solvers.BisectionSolver
import org.apache.commons.math3.analysis.UnivariateFunction

/**
 * Math functions.
 *
 * These are factored out into a trait just for modularity. Everyone should use these from the
 * [[org.mbari.math.Matlib]] object
 * @author Brian Schlining
 * @since 2012-06-08
 */
protected trait Mathematics {

    /**
     * Calculate Pearson's correlation coefficient. This is also known as the <strong>Correlation
     * Coefficient</strong> or the <strong>Pearson product-moment correlation
     * coefficient</strong>
     *
     * @param x
     * @param y
     * @return
     */
    def corr(x: Array[Double], y: Array[Double]) = Statlib.pearsonsCorrelation(x, y)

    def corrcoef(x: Array[Double], y: Array[Double]) = Statlib.correlationCoefficient(x, y)

    def cumsum(data: Array[Double]): Array[Double] = JMatlib.cumsum(data)


    def fft(data: Array[Double]): Array[Complex[Double]] = {
        // real data array needs to be converted to real/imag array
        val complexData = Array.ofDim[Double](data.size * 2)
        data.indices.foreach { i =>
            complexData(i * 2) = data(i)
        }

        // Run FFT
        val fft1d = new DoubleFFT_1D(data.size)
        fft1d.complexForward(complexData)

        // Convert real-imag packed array to an Array of Complex numbers
        val f = for (i <- 0 until (complexData.size - 1) by 2) yield {
            Complex(complexData(i), complexData(i + 1))
        }
        f.toArray
    }

    def fix(x: Double): Double = JMatlib.fix(x)

    /**
     * Single variable nonlinear zero finding
     * @param fn The function whose zero we're searching for
     * @param start A starting quess for the location of the zero
     * @return
     */
    def fzero(fn: Double => Double, start: Double): Double = {
        val solver = new BisectionSolver()
        val ufn = new UnivariateFunction {
            def value(p1: Double): Double = fn(p1)
        }
        solver.solve(10000, ufn, start)
    }

    /**
     * Single variable nonlinear zero finding over an interval
     * @param fn The function whose zero we're searching for
     * @param min The interval minimum
     * @param max The interval maximum
     * @return
     */
    def fzero(fn: Double => Double, min: Double, max: Double): Double = {
        val solver = new BisectionSolver()
        val ufn = new UnivariateFunction {
            def value(p1: Double): Double = fn(p1)
        }
        solver.solve(10000, ufn, min, max)
    }


    /**
     * Linear interpolation
     * @param x
     * @param y
     * @param xi
     * @return
     *
     * @throws IllegalArgumentException
     */
    def interp1(x: Array[Double], y: Array[Double], xi: Array[Double]) = JMatlib.interpolate(x, y, xi)

    /**
     * generates n linearly-spaced points between d1 and d2.
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @return an array of lineraly space points.
     */
    def linspace(d1: Double, d2: Double, n: Int) = JMatlib.linspace(d1, d2, n)

    /**
     * generates n logarithmically-spaced points between d1 and d2.
     * @param d1 The min value
     * @param d2 The max value
     * @param n The number of points to generated
     * @return an array of lineraly space points.
     */
    def logspace(d1: Double, d2: Double, n: Int) = JMatlib.logspace(d1, d2, n)

    def mod(a: Double, b: Double) = a % b

    /**
     * Find the index of the array nearest to the value. The values array can
     * contain only unique values. If it doesn't the first occurence of a value
     * in the values array is the one used, subsequent duplicate are ignored. If
     * the value falls outside the bounds of the array, <b>null</b> is returned
     *
     * @param data Values to search through for the nearest point
     * @param key THe value to search for the nearest neighbor in the array
     * @param inclusive If true the key must be within the values array
     * @return The index of the array value nearest the value. -1 will be returned if the
     *  key is outside the array values
     */
    def near(data: Array[Double], key: Double, inclusive: Boolean = true) = JMatlib.near(data, key)

    /**
     * @param data The data array
     * @return The product of the elements of '''data'''
     */
    def prod(data: Array[Double]): Double = data.fold(1D)((a, b) => a * b)

    def rem(x: Double, y: Double): Double = DoubleMath.rem(x, y)

    /**
     * Signum function
     * @param x
     * @return
     */
    def sign(x: Double) = DoubleMath.sign(x)

    /**
     * Same as Matlab's unique. FOr performance if you just need the unique values use {{{a.distinct}}}.
     * This method also returns the order indices.
     *
     * @param a An array
     * @param occurrence The default value is occurrence = "last", which returns the index of the
     *                   last occurrence of each repeated value (or row) in A, while
     *                   occurrence = 'first' returns the index of the first occurrence of each
     *                   repeated value (or row) in A
     * @return A tuple of (c, ia, ic). c is the same values as in A but with no repetitions. C will be sorted.
     *         ia and ic are index arrays such that c = a(ia) and a = c(ic
     */
    def unique(a: Array[Double], occurrence: String = "last"): (Array[Double], Array[Int], Array[Int]) = {

        val useFirst = occurrence match {
            case "first" => true
            case _ => false
        }

        val c = a.distinct.sorted

        val ia = (for (i <- 0 until c.length) yield {
            val v = c(i)
            if (useFirst) {
                a.indexOf(v)
            }
            else {
                a.lastIndexOf(v)
            }
        }).toArray

        val ic = (for (i <- 0 until a.length) yield c.indexOf(a(i)) ).toArray

        (c, ia, ic)
    }


}
