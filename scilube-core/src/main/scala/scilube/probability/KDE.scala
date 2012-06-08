package scilube.probability

import scala.math._
import org.mbari.math.Statlib
import spire.math.Complex
import scilube.spire.ComplexLib
import scilube.Matlib

/**
 * Kernel Density Estimator
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object KDE {

    def apply(data: Array[Double]): Array[Double] = apply(data, pow(2, 14).toLong)

    def apply(data: Array[Double], n: Long): Array[Double] = {
        val min = data.min
        val max = data.max
        val d = (max - min) / 10
        apply(data, n, min - d, max + d)
    }

    def apply(data: Array[Double], n: Long, min: Double, max: Double): Array[Double] = {

        // Set up the grid over which the density estimate is computed
        val R = max - min
        val dx = R / (n - 1)

        val xmesh = (0D to R by dx).map(_ + min).toArray
        val N = data.distinct.size

        // Bin the data uniformly using the grid defined above
        val initial_data2 = Statlib.histc(data, xmesh).map( _ / N)
        val initial_data_sum = initial_data2.sum
        val initial_data = initial_data2.map( _ / initial_data_sum )



        null

    }

    /**
     * Discrete cosine transform
     * @param data
     */
    private def dct1d(data: Array[Double]) = {
        val nrows = data.size
        // Compute weights to multiply DFT coefficients
        val weight = Complex[Double](1, 0) +: (for (j <- 1 until nrows) yield {
            ComplexLib.exp(Complex[Double](0, -j) * Pi / (2 * nrows))
        })
        val dataA = for (i <- 0 until data.size by 2) yield data(i)
        val dataB = for (i <- (data.size - 1) until 2 by -2) yield data(i)
        val data2 = dataA ++ dataB
        //val fft = Matlib.fft(data2)





    }

}
