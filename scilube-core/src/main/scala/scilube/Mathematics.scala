package scilube

import _root_.spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import org.mbari.math.{DoubleMath, Matlib => JMatlib, Statlib}

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
        // FIXME: real data array needs to be converted to real/imag array
        val fft1d = new DoubleFFT_1D(data.size)
        val result = Array.ofDim[Double](data.size * 2)
        fft1d.complexForward(result)
        val f = for (i <- 0 until (result.size - 1) by 2) yield {
            Complex(result(i), result(i + 1))
        }
        f.toArray
    }

    def fix(x: Double): Double = JMatlib.fix(x)

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


    def rem(x: Double, y: Double): Double = DoubleMath.rem(x, y)

    /**
     * Signum function
     * @param x
     * @return
     */
    def sign(x: Double) = DoubleMath.sign(x)

}
