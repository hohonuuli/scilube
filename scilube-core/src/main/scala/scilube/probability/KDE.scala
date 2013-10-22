package scilube.probability

import edu.emory.mathcs.jtransforms.dct.DoubleDCT_1D
import org.mbari.math.Statlib
import scala.math._
import scilube.Matlib
import scilube.spire.ComplexLib
import spire.implicits._
import spire.math.Complex

/**
 * Kernel Density Estimator for one-dimensional data. This is a port of kde.m at
 * http://mathworks.us/matlabcentral/fileexchange/authors/27236. The output of this matches
 * kde.m exactly. Help from kde.m:
 *
 * "Gaussian kernel is assumed and the bandwidth is chosen automatically;
 *    Unlike many other implementations, this one is immune to problems
 *    caused by multimodal densities with widely separated modes (see example). The
 *    estimation does not deteriorate for multimodal densities, because we never assume
 *    a parametric model for the data."
 *
 * '''Reference''':
 *   Z. I. Botev, J. F. Grotowski, and D. P. Kroese (2010)
 *   Annals of Statistics, Volume 38, Number 5, pages 2916-2957.
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object KDE {

  /**
   * Apply the kernel density estimator. Uses a default n of pow(2, 14)
   * @param data An array of data used to construct the density estimate
   * @return The results
   */
  def apply(data: Array[Double]): KDEResult = apply(data, pow(2, 14).toInt)

  /**
   * Apply the kernel density estimator
   * @param data An array of data used to construct the density estimate
   * @param n The number of points used in the uniform disretization of the interval (where
   *          interval is data.max and data.min). '''n''' should be a power of 2. If it's not
   *          then n is rounded up to the next power of 2.
   * @return
   */
  def apply(data: Array[Double], n: Int): KDEResult = {
    val min = data.min
    val max = data.max
    val d = (max - min) / 10
    apply(data, n, min - d, max + d)
  }

  /**
   * Apply the kernel density estimator
   * @param data An array of data used to construct the density estimate
   * @param n The number of points used in the uniform disretization of the interval (where
   *          interval is data.max and data.min). '''n''' should be a power of 2. If it's not
   *          then n is rounded up to the next power of 2.
   * @param min defines the minimum of the interval on which the density estimate is constructed
   * @param max defines the maximum of the interval on which the density estimate is constructed
   * @return
   */
  def apply(data: Array[Double], n: Int, min: Double, max: Double): KDEResult = {

    // Set up the grid over which the density estimate is computed
    val R = max - min
    val dx = R / (n - 1)

    val xmesh = (0D to R by dx).map(_ + min).toArray
    val N = data.distinct.size

    // Bin the data uniformly using the grid defined above
    val initial_data2 = Statlib.histc(data, xmesh).map(_ / N)
    val initial_data_sum = initial_data2.sum
    val initial_data = initial_data2.map(_ / initial_data_sum)
    val a = dct1d(initial_data)
    val I = (1 to (n - 1)).map(pow(_, 2)).toArray
    val a2 = a.tail.map(j => pow(j / 2D, 2))

    // use  fzero to solve the equation t=zeta*gamma^[5](t)
    val t_star = try {
      Matlib.fzero(fixedPoint(_: Double, N, I, a2), 0)
    } catch {
      case _: Exception => .28 * pow(N, -2 / 5D)
    }

    // smooth the discrete cosine transform of initial data using t_star
    // MATLAB: a_t=a.*exp(-[0:n-1]'.^2*pi^2*t_star/2);
    val a_t = (for (i: Int <- 0 to (n - 1)) yield {
      a(i) * exp(-pow(i, 2) * pow(Pi, 2) * t_star / 2)
    }).toArray

    val pdf = idct1d(a_t).map(_ / R)
    val bandwidth = sqrt(t_star) * R
    val cdf = {
      val fa = for (i <- 0 until I.size) yield {
        I(i) * a2(i) * exp(-I(i) * pow(Pi, 2) * t_star)
      }
      val f = 2 * pow(Pi, 2) * fa.sum
      val t_cdf = pow(sqrt(Pi) * f * N, -2D / 3)
      val a_cdf = (for (i: Int <- 0 to (n - 1)) yield {
        a(i) * exp(-pow(i, 2) * pow(Pi, 2) * t_cdf / 2)
      }).toArray
      Matlib.cumsum(idct1d(a_cdf)).map(_ * dx / R)
    }

    new KDEResult(bandwidth, xmesh, pdf, cdf)

  }

  /**
   * this implements the function t-zeta*gamma**[l](t)
   * @param t
   * @param N
   * @param I
   * @param a2
   */
  protected[probability] def fixedPoint(t: Double, N: Double, I: Array[Double], a2: Array[Double]) = {
    require(I.size == a2.size)

    // *** 2012-06-13 Brian Schlining - validated this method against kde.m. Matches exactly ***

    val l = 7

    // MATLAB: f=2*pi^(2*l)*sum(I.^l.*a2.*exp(-I*pi^2*t)); CHECK
    var f = {
      val fa = (for (i <- 0 until I.size) yield {
        pow(I(i), l) * a2(i) * exp(-I(i) * pow(Pi, 2) * t)
      }).sum
      2 * pow(Pi, 2 * l) * fa
    }

    for (s <- (l - 1) to 2 by -1) {

      // MATLAB: K0=prod([1:2:2*s-1])/sqrt(2*pi);
      val K0 = {
        val a = (1 to (2 * s - 1) by 2).map(_.toDouble).toArray
        Matlib.prod(a) / sqrt(2 * Pi)
      }
      // MATLAB: const=(1+(1/2)^(s+1/2))/3;
      val const = (1 + pow(1 / 2D, s + 1 / 2D)) / 3

      // MATLAB: time=(2*const*K0/N/f)^(2/(3+2*s));
      val time = pow(2 * const * K0 / N / f, 2 / (3D + 2 * s))

      // MATLAB: f=2*pi^(2*s)*sum(I.^s.*a2.*exp(-I*pi^2*time));
      f = {
        val fa = (for (i <- 0 until I.size) yield {
          pow(I(i), s) * a2(i) * exp(-I(i) * pow(Pi, 2) * time)
        }).sum
        2 * pow(Pi, 2 * s) * fa
      }

    }

    // MATLAB: out=t-(2*N*sqrt(pi)*f)^(-2/5);
    t - pow(2 * N * sqrt(Pi) * f, -2 / 5D)

  }

  //    protected[probability] def idct1d(data: Array[Double]): Array[Double] = {
  //        val nrows = data.size
  //
  //        // MATLAB: weights = nrows*exp(i*(0:nrows-1)*pi/(2*nrows)).';
  //        val weights = for (j <- 0 until nrows) yield {
  //            nrows * ComplexLib.exp(Complex[Double](0, j) * Pi / (2 * nrows))
  //        }
  //
  //        // MATLAB: data = real(ifft(weights.*data));
  //        val data2 = (for (j <- 0 until data.size) yield {
  //            weights(j) * data(j)
  //        }).toArray
  //        val data3 = ComplexLib.ifft(data2).map(_.real)
  //
  //
  //        val out = Array.ofDim[Double](nrows)
  //
  //        // MATLAB: out(1:2:nrows) = data(1:nrows/2);
  //        for (j <- 0 until (nrows / 2)) {
  //            out(j * 2) = data3(j)
  //        }
  //
  //        // MATLAB: out(2:2:nrows) = data(nrows:-1:nrows/2+1);
  //        val data4 = data3.slice((nrows / 2), nrows).reverse
  //        for (i <- 0 until data4.size) {
  //            val idx = 1 + (i * 2)
  //            out(idx) = data4(i)
  //        }
  //
  //        out
  //
  //    }

  protected[probability] def idct1d(data: Array[Double]): Array[Double] = {
    // *** 2012-06-13 Brian Schlining - validated this method against kde.m. Matches exactly ***
    val dct = new DoubleDCT_1D(data.size)
    val result = data.clone()
    dct.inverse(result, false)
    result
  }

  /**
   * Discrete cosine transform
   * @param data
   */
  protected[probability] def dct1d(data: Array[Double]): Array[Double] = {

    // *** 2012-06-12 Brian Schlining - validated this method against kde.m. Matches exactly ***

    val nrows = data.size

    // Compute weights to multiply DFT coefficients
    // MATLAB: weight = [1;2*(exp(-i*(1:nrows-1)*pi/(2*nrows))).'];
    val weight = Complex[Double](1, 0) +: (for (j <- 1 until nrows) yield {
      2D * ComplexLib.exp(Complex[Double](0, -j) * Pi / (2 * nrows))
    })

    // Re-order the elements of the columns of x
    // MATLAB: data = [ data(1:2:end,:); data(end:-2:2,:) ];
    val dataA = for (i <- 0 until data.size by 2) yield data(i)
    val dataB = for (i <- (data.size - 1) to 1 by -2) yield data(i)
    val data2 = (dataA ++ dataB).toArray

    // Multiply FFT by weights:
    // MATLAB: data= real(weight.* fft(data));
    val fft = Matlib.fft(data2)
    val d = for (i <- 0 until fft.size) yield {
      (weight(i) * fft(i)).real
    }
    d.toArray
  }

  //    protected[probability] def dct1d(data: Array[Double]): Array[Double] = {
  //        // This does not match the values of dct1d used in kde.m.
  //        // TODO: Need to investigate which one is correct
  //        val dct = new DoubleDCT_1D(data.size)
  //        val result = data.clone()
  //        dct.forward(result, false)
  //        result
  //    }

}
