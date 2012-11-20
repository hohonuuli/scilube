package scilube

import org.mbari.math.Statlib
import math._
import probability.KDE
import scala.util.Random

/**
 * Probability functions
 *
 * These are factored out into a trait just for modularity. Everyone should use these from the
 * [[org.mbari.math.Matlib]] object
 *
 * @author Brian Schlining
 * @since 2012-06-08
 */

protected trait Probabilities {

    /**
     * Computes the possible number of distinct combinations of n items into groups of c items
     * @param n The number of items
     * @param c The number of items in a set
     * @return The number of combinations of n items in c sets.
     */
    def combinations(n: BigInt, c: BigInt): BigInt = factorial(n) / (factorial(c) * factorial(n - c))


    /**
     * Computes factorial. We need this for imaging in order because the number of permutations
     * of ''n'' distinct objects is equal to the factorial of ''n''
     *
     * @param n The value for the factorial
     * @param result The accumulator of the previous iteration of factorial. Normally
     *      developers do not supply this value. Instead the default value of 1 is used.
     * @return factorial of n
     */
    def factorial(n: BigInt, result: BigInt = 1): BigInt = if (n == 0) result
            else factorial(n - 1, n * result)

    /**
     * Bins elements into bins with specified centers
     * @param data The data to bin
     * @param centers The bins where each value represents a center of a bin. They MUST
     *                be ordered or the data will not be valid.
     * @param inclusive if true values outside of the centers are excluded from results. The default
     *                  is `false`
     * @return The histogram of data
     */
    def hist(data: Array[Double], centers: Array[Double], inclusive: Boolean = false) = Statlib.hist(data, centers, inclusive)


    /**
     * Bins elements using specified edges
     * @param data The data to bin
     * @param edges array defining the edges of each bin
     * @return The histogram of data
     */
    def histc(data: Array[Double], edges: Array[Double]) = Statlib.histc(data, edges)


    def quantile(data:Array[Double], p: Double): Double = quantile(data, p, 1)

    def quantile(data:Array[Double], p: Double, method: Int): Double = {
        val a = quantile(data, Array(p), method)
        a(0)
    }

    def quantile(data: Array[Double], p: Array[Double]): Array[Double] = quantile(data, p, 1)

    /**
     * Empirical quantile (percentile). Like <i>prctile</i> in Matlab's statistics toolbox
     * @param data The data samples
     * @param p The percentiles of interest (0 <= p <= 1)
     * @param method The method for calculating the emperical quantile:
     *               1. Interpolation so that F(X_(k)) == (k-0.5)/n. ('''DEFAULT''')
     *               2. Interpolation so that F(X_(k)) == k/(n+1).
     *               3. Based on the empirical distribution.
     * @return
     */
    def quantile(data: Array[Double], p: Array[Double], method: Int): Array[Double] = {
        // TODO verify that pcts are between 0 and 1
        require(data != null && data.size > 0, "You did not supply and data")
        val x = data.sorted
        val n = x.size
        val q = if (method == 3) {

            val i1 = p.map(pp => ceil(max(1D, pp * n)).toInt)
            val qq1 = for (j <- 0 until i1.size) yield x(i1(j))

            val i2 = p.map(pp => floor(min(pp * n, n.toDouble)).toInt)
            val qq2 = for (j <- 0 until i2.size) yield x(i2(j))

            for (j <- 0 until qq1.size) yield (qq1(j) + qq2(j)) / 2D
        }
        else {
            val xx = x(1) +: x :+ x(n - 1)
            val i = if (method == 2) {
                // This method is from Hjort's "Computer intensive statistical methods" page 102
                p.map( _ * (n + 1) + 1 )
            }
            else {
                // Method 1
                p.map( _ * n + 1.5)
            }
            val iu = i.map(ceil(_).toInt)
            val il = i.map(floor(_).toInt)
            for (j <- 0 until p.size) yield {
                val d = i(j) - il(j)
                xx(il(j)) * (1 - d) + xx(iu(j)) * d
            }

        }
        q.toArray
    }






    /**
     * Is the cat dead or alive?
     * @param a The alive cat
     * @param b The deat bat
     * @tparam A The type of the alive cat
     * @tparam B The type of the dead cat
     * @return A box ... is the cat dead or alive?
     */
    def Schrödinger[A, B](a: A, b: B): Either[A, B] =  new Random().nextBoolean() match {
        case true => Right(b)
        case _ => Left(a)
    }


}
