package scilube.probability

import scala.math._
import org.mbari.math.Statlib
import spire.math.Complex
import scilube.spire.ComplexLib
import scilube.Matlib
import org.apache.commons.math3.analysis.solvers.BisectionSolver
import org.apache.commons.math3.analysis.UnivariateFunction

/**
 * Kernel Density Estimator
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object KDE {

    private[this] val fzero = new BisectionSolver()


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
        val a = dct1d(initial_data)
        val I = (1 to n - 1).map(pow(_, 2)).toArray
        val a2 = a.tail.map(j => pow(j / 2D, 2))

        // use  fzero to solve the equation t=zeta*gamma^[5](t)
        val fn = new UnivariateFunction {
            def value(p1: Double): Double = fixedPoint(p1, N, I, a2)
        }
        val t_star = try {
            fzero.solve(10000, fn, 0, .1)
        }
        catch {
            case _ => .28 * pow(N, -2 / 5D)
        }

        // smooth the discrete cosine transform of initial data using t_star
        // MATLAB: a_t=a.*exp(-[0:n-1]'.^2*pi^2*t_star/2);
        val a_t =




        null

    }

    /**
     * this implements the function t-zeta*gamma^[l](t)
     * @param t
     * @param N
     * @param I
     * @param a2
     */
    protected[probability] def fixedPoint(t: Double, N: Double, I: Array[Double], a2: Array[Double]) = {
        require(I.size == a2.size)

        val l = 7

        // MATLAB: f=2*pi^(2*l)*sum(I.^l.*a2.*exp(-I*pi^2*t));
        val f = {
            val ff = for (i <- 0 until I.size) yield {
                pow(I(i), l) * a2(i) * exp(pow(-I(i) * Pi, 2) * t)
            }
            pow(2 * Pi, 2 * l) * ff.sum
        }


        for (s <- (l - 1) to 2 by -1) {
            var fv = f
            // MATLAB: K0=prod([1:2:2*s-1])/sqrt(2*pi);
            val K0 =  {
                val a = (1 until (2 * s - 1) by 2).map(_.toDouble).toArray
                Matlib.prod(a) / sqrt(2 * Pi)
            }
            // MATLAB: const=(1+(1/2)^(s+1/2))/3;
            val const = pow(1 + (1 / 2), (s + 1 / 2)) / 3

            // MATLAB: time=(2*const*K0/N/f)^(2/(3+2*s));
            val time = pow(2 * const * K0 / N / f, 2 / (3 + 2 * s))

            // MATLAB: f=2*pi^(2*s)*sum(I.^s.*a2.*exp(-I*pi^2*time));
            fv = {
                val ff = for (i <- 0 until I.size) yield {
                    pow(I(i), s) * a2(i) * exp(pow(-I(i) * Pi, 2) * time)
                }
                pow(2 * Pi, 2 * s) * ff.sum
            }

        }

        // MATLAB: out=t-(2*N*sqrt(pi)*f)^(-2/5);
        t - pow(2 * N * sqrt(Pi) * f, -2 / 5D)


    }


    protected[probability] def idct1d(data: Array[Double]): Array[Double] = {
        val nrows = data.size

        // MATLAB: weights = nrows*exp(i*(0:nrows-1)*pi/(2*nrows)).';
        val weights = for (j <- 0 until nrows) yield {
            nrows * ComplexLib.exp(Complex[Double](0, j) * Pi / (2 * nrows))
        }

        // MATLAB: data = real(ifft(weights.*data));
        val data2 = (for (j <- 0 until data.size) yield {
            weights(j) * data(j)
        }).toArray
        val data3 = ComplexLib.ifft(data2).map(_.real)


        val out = Array.ofDim[Double](nrows)

        // MATLAB: out(1:2:nrows) = data(1:nrows/2);
        for (j <- 0 until (nrows / 2)) {
            out(j * 2) = data3(j)
        }

        // MATLAB: out(2:2:nrows) = data(nrows:-1:nrows/2+1);
        val data4 = data3.slice((nrows / 2), nrows).reverse
        for (i <- 0 until data4.size) {
            val idx = 1 + (i * 2)
            out(idx) = data4(i)
        }

        out

    }

    /**
     * Discrete cosine transform
     * @param data
     */
    protected[probability] def dct1d(data: Array[Double]): Array[Double] = {
        val nrows = data.size

        // Compute weights to multiply DFT coefficients
        // MATLAB: weight = [1;2*(exp(-i*(1:nrows-1)*pi/(2*nrows))).'];
        val weight = Complex[Double](1, 0) +: (for (j <- 1 until nrows) yield {
            2D * ComplexLib.exp(Complex[Double](0, -j) * Pi / (2 * nrows))
        })

        // Re-order the elements of the columns of x
        // MATLAB: data = [ data(1:2:end,:); data(end:-2:2,:) ];
        val dataA = for (i <- 0 until data.size by 2) yield data(i)
        val dataB = for (i <- (data.size - 1) until 2 by -2) yield data(i)
        val data2 = (dataA ++ dataB).toArray

        // Multiply FFT by weights:
        // MATLAB: data= real(weight.* fft(data));
        val fft = Matlib.fft(data2)
        val d = for (i <- 0 until fft.size) yield {
            (weight(i) * fft(i)).real
        }
        d.toArray
    }

}
