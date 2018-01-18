package scilube.spirelib

import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import scala.{math => M}
import spire.implicits._
import spire.math.Complex

/**
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object ComplexLib {

  implicit def doubleArrayToComplexArray(d: Array[Double]): Array[Complex[Double]] =
    d.map(Complex(_, 0D))

  /**
   * Modulus of this Complex number
   * (the distance from the origin in polar coordinates).
   * @param c The complex number
   * @return |z| where z is this Complex number.
   */
  def mod(c: Complex[Double]): Double = c.abs

  /**
   * Complex exponential
   * @param c The complex number
   * @return exp(z) where z is this Complex number.
   */
  def exp(c: Complex[Double]): Complex[Double] = Complex(
      M.exp(c.real) * M.cos(c.imag),
      M.exp(c.real) * M.sin(c.imag)
  )

  /**
   * Principal branch of the Complex logarithm of this Complex number.
   * The principal branch is the branch with -pi < arg <= pi.
   * @param c The complex number
   * @return log(z) where z is this Complex number.
   */
  def log(c: Complex[Double]): Complex[Double] =
    Complex(scala.math.log(c.abs), c.arg)

  /**
   * Complex square root
   * Computes the principal branch of the square root, which
   * is the value with 0 <= arg < pi.
   * @param c The complex number
   * @return sqrt(z) where z is this Complex number.
   */
  def sqrt(c: Complex[Double]): Complex[Double] = {
    val r = M.sqrt(mod(c))
    val theta = c.arg / 2
    Complex(r * M.cos(theta), r * M.sin(theta))
    //        val m = c.abs
    //        val s = Matlib.sign(c.imag)
    //        val p = 1 / M.sqrt(2) * M.sqrt(m + c.real)
    //        val q = s / M.sqrt(2) * M.sqrt((m - c.real))
    //        Complex(p, q)

  }

  /**
   * Sine of this Complex number (doesn't change this Complex number).
   * sin(z) = (exp(i*z)-exp(-i*z))/(2*i).
   * @return sin(z) where z is this Complex number.
   */
  def sin(c: Complex[Double]): Complex[Double] = Complex(
      M.cosh(c.imag) * M.sin(c.real),
      M.sinh(c.imag) * M.cos(c.real)
  )

  /**
   * Cosine of this Complex number (doesn't change this Complex number).
   * cos(z) = (exp(i*z)+exp(-i*z))/ 2.
   * @param c The complex number
   * @return cos(z) where z is this Complex number.
   */
  def cos(c: Complex[Double]): Complex[Double] = Complex(
      M.cosh(c.imag) * M.cos(c.real),
      -M.sinh(c.imag) * M.sin(c.real)
  )

  /**
   * Hyperbolic sine of this Complex number
   * sinh(z) = (exp(z)-exp(-z))/2.
   * @param c The complex number
   * @return sinh(z) where z is this Complex number.
   */
  def sinh(c: Complex[Double]): Complex[Double] = new Complex(
      M.sinh(c.real) * M.cos(c.imag),
      M.cosh(c.real) * M.sin(c.imag)
  );

  /**
   * Hyperbolic cosine of this Complex number
   * cosh(z) = (exp(z) + exp(-z)) / 2.
   * @param c The complex number
   * @return cosh(z) where z is this Complex number.
   */
  def cosh(c: Complex[Double]): Complex[Double] = new Complex(
      M.cosh(c.real) * M.cos(c.imag),
      M.sinh(c.real) * M.sin(c.imag)
  );

  /**
   * Tangent of this Complex number (doesn't change this Complex number).
   * tan(z) = sin(z)/cos(z).
   * @param c The complex number
   * @return tan(z) where z is this Complex number.
   */
  def tan(c: Complex[Double]): Complex[Double] = sin(c) / cos(c)

  def fft(data: Array[Complex[Double]]): Array[Complex[Double]] = {
    // 1d Array of complex values stored as values in seq, the real part then the imag part
    val data2 = data.map(c => Array(c.real, c.imag)).flatten
    val fft1d = new DoubleFFT_1D(data.size)
    fft1d.complexForward(data2)
    val f = for (i <- 0 until (data2.size - 1) by 2) yield {
      Complex(data2(i), data2(i + 1))
    }
    f.toArray
  }

  def ifft(data: Array[Complex[Double]], scale: Boolean = true): Array[Complex[Double]] = {
    // 1d Array of complex values stored as values in seq, the real part then the imag part
    val data2 = data.map(c => Array(c.real, c.imag)).flatten
    val fft1d = new DoubleFFT_1D(data.size)
    fft1d.complexInverse(data2, scale)
    val f = for (i <- 0 until (data2.size - 1) by 2) yield {
      Complex(data2(i), data2(i + 1))
    }
    f.toArray
  }

}
