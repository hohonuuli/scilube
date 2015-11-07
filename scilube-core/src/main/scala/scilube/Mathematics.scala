package scilube


import spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import org.apache.commons.math3.analysis.solvers.BisectionSolver
import org.apache.commons.math3.analysis.UnivariateFunction
import org.mbari.math.{DoubleMath, Matlib => JMatlib, Statlib}
import scala.math.{floor, sqrt}


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
   * Element by element addition
   * @param a THe first array
   * @param b The second array
   * @return An array that is the element by element summation of a to b
   */
  def add(a: Array[Double], b: Array[Double]): Array[Double] = {
    require(a.length == b.length, "Whoops, arrays are different sizes (" + a.length +
        " and " + b.length + " ")
    (for (i <- 0 until a.length) yield a(i) + b(i)).toArray
  }

  /**
   * Calculate Pearson's correlation coefficient. This is also known as the <strong>Correlation
   * Coefficient</strong> or the <strong>Pearson product-moment correlation
   * coefficient</strong>
   *
   * @param x x values
   * @param y y values
   * @return correlation coefficient
   */
  def corr(x: Array[Double], y: Array[Double]) = Statlib.pearsonsCorrelation(x, y)

  def corrcoef(x: Array[Double], y: Array[Double]) = Statlib.correlationCoefficient(x, y)

  /**
   * Cumulative sum of elements
   *
   * @param data values
   * @return An array containing the cumulative sum of data
   */
  def cumsum(data: Array[Double]): Array[Double] = JMatlib.cumsum(data)

  def diff(x: Array[Double]): Array[Double] = if (x.size < 2) Array.empty[Double]
  else {
    JMatlib.diff(x);
    /*(for (i <- 0 until x.size - 1) yield {
      val j = i + 1
      x(j) - x(i)
    }).toArray */
  }

  /**
   * Element by element division
   * @param a THe first array
   * @param b The second array
   * @return An array that is the element by element summation of a to b
   */
  def divide(a: Array[Double], b: Array[Double]): Array[Double] = {
    require(a.length == b.length, "Whoops, arrays are different sizes (" + a.length +
        " and " + b.length + " ")
    (for (i <- 0 until a.length) yield a(i) / b(i)).toArray
  }

  /**
   * Calculates the dot product between 2 arrays (The dot is the project of b onto a).
   * a and b must be the same size
   * @param a The first array
   * @param b THe second array (b will be projected onto a)
   * @return The dot product. (b projected onto a)
   */
  def dot(a: Array[Double], b: Array[Double]): Double = {
    require(a.length == b.length, "Whoops, arrays are different sizes (" + a.length +
        " and " + b.length + " ")
    (for (i <- 0 until a.length) yield a(i) * b(i)).sum
  }

  /**
   * Discrete Fourier transform
   * @param data values
   * @return The fft of data
   */
  def fft(data: Array[Double]): Array[Complex[Double]] = {

    import _root_.spire.implicits._

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

  /**
   * Round towards zero
   * @param x The number
   * @return The number rounded towars zero
   */
  def fix(x: Double): Double = JMatlib.fix(x)

  /**
   * Single variable nonlinear zero finding
   * @param fn The function whose zero we're searching for
   * @param start A starting quess for the location of the zero
   * @return The x value of zero for the fn
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
   * @return The x value of zero for the fn
   */
  def fzero(fn: Double => Double, min: Double, max: Double): Double = {
    val solver = new BisectionSolver()
    val ufn = new UnivariateFunction {
      def value(p1: Double): Double = fn(p1)
    }
    solver.solve(10000, ufn, min, max)
  }

  /**
   * Greatest common denominator
   */
  def gcd(a: Int, b: Int): Int = if (b == 0) a else gcd(b, a % b)

  /**
   * Linear interpolation
   * @param x array of x values
   * @param y array of y values
   * @param xi array of x values to interpolate to
   * @return array of y values at xi
   *
   * @throws IllegalArgumentException
   */
  def interp1(x: Array[Double], y: Array[Double], xi: Array[Double]) = JMatlib.interpolate(x, y, xi)

  /**
   * @return true if the number is a prime number. False otherwise
   */
  def isprime[A : Numeric](n: A): Boolean = {
    val numeric = implicitly[Numeric[A]]
    val nd = numeric.toDouble(n)
    if (nd % 1 == 0) {
      val ni = floor(nd).toInt
      (2 until ni) forall { d => nd % d != 0 }
    }
    else false
  }

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

  /**
   * Modulus after divistion. THis just calls the `%` operator; it's included to make porting
   * Matlab code more familiar.
   * @param a a value
   * @param b the other value
   * @return the modulus
   */
  def mod(a: Double, b: Double) = a % b


  /**
   * Element by element multiplication
   * @param a THe first array
   * @param b The second array
   * @return An array that is the element by element summation of a to b
   */
  def multiply(a: Array[Double], b: Array[Double]): Array[Double] = {
    require(a.length == b.length, "Whoops, arrays are different sizes (" + a.length +
        " and " + b.length + " ")
    (for (i <- 0 until a.length) yield a(i) * b(i)).toArray
  }

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
  def near(data: Array[Double], key: Double, inclusive: Boolean = true): Int = JMatlib.near(data, key, inclusive)


  /**
   * Compute the norm. Treats the array as a vector of values.
   * @param x The array
   * @return The norm (aka magnitude) of the array.
   */
  def norm(x: Array[Double]): Double = sqrt(x.map(v => v * v).sum)

  /**
   * @param data The data array
   * @return The product of the elements of '''data'''
   */
  def prod(data: Array[Double]): Double = data.fold(1D)((a, b) => a * b)

  /**
   * @param x a value
   * @param y another value
   * @return Remainder after division
   */
  def rem(x: Double, y: Double): Double = DoubleMath.rem(x, y)

  /**
   * Signum function
   * @param x a value
   * @return The sign of x (-1 or 1)
   */
  def sign(x: Double) = DoubleMath.sign(x)

  /**
   * Element by element subtraction
   * @param a THe first array
   * @param b The second array
   * @return An array that is the element by element summation of a to b
   */
  def subtract(a: Array[Double], b: Array[Double]): Array[Double] = {
    require(a.length == b.length, "Whoops, arrays are different sizes (" + a.length +
        " and " + b.length + " ")
    (for (i <- 0 until a.length) yield a(i) - b(i)).toArray
  }

  def sum(x: Array[Double]) = JMatlib.sum(x);

  /**
   * Same as Matlab's unique. For performance, if you just need the unique values use {{{a.distinct}}}.
   * This method also returns the order indices.
   *
   * @param a An array
   * @param occurrence The default value is occurrence = "last", which returns the index of the
   *                   last occurrence of each repeated value (or row) in A, while
   *                   occurrence = 'first' returns the index of the first occurrence of each
   *                   repeated value (or row) in A
   * @return A tuple of (c, ia, ic). c is the same values as in A but with no repetitions. C will be sorted.
   *         ia and ic are index arrays such that c = a(ia) and a = c(ic)
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
