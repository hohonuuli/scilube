package scilube

import _root_.spire.implicits._
import _root_.spire.math.Complex
import org.junit.Assert._
import org.junit.Test
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 *
 * @author Brian Schlining
 * @since 2012-06-11
 */
class MathematicsTest {

  private[this] val math = new Mathematics {}
  private[this] val tolerance = 0.00000000001

  // wdata = [0:40 10:30 15:25 18:22 25 27 27 28 29 29 30 30 31 32 33 34 40 45 50];
  private[this] val wdata = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,
    17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,
    40,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,15,
    16,17,18,19,20,21,22,23,24,25,18,19,20,21,22,25,27,27,28,29,29,30,30,
    31,32,33,34,40,45,50)


  @Test
  def testDiff() {
    val d = Array(1D, 2D, 4D)
    val a = math.diff(d)
    val e = Array(1D, 2D)
    e.indices.foreach { i =>
      assertEquals(e(i), a(i), tolerance)
    }
  }

  @Test
  def testFft() {
    /*
        d = 1:10
        a = fft(d)
     */
    val d = (1 to 10).map(_.toDouble).toArray
    val a = math.fft(d)
    val e = Array(Complex(55D, 0D),
      Complex(-5D, 15.388417685876266),
      Complex(-5D, 6.881909602355867),
      Complex(-5.000000000000000, 3.632712640026803),
      Complex(-5.000000000000000, 1.624598481164532),
      Complex(-5D, 0D),
      Complex(-5.000000000000000, -1.624598481164532),
      Complex(-5.000000000000000, -3.632712640026803),
      Complex(-5.000000000000000, -6.881909602355867),
      Complex(-5.000000000000000, -15.388417685876266))

    assertEquals(e.size, a.size)
    a.indices.foreach { i =>
      compare(e(i), a(i))
    }

  }

  @Test
  def testProd() {
    /*
        d = 1:10
        a = prod(d)
     */
    val d = (1 to 10).map(_.toDouble).toArray
    val a = math.prod(d)
    val e = 3628800D
    assertEquals(e, a, tolerance)
  }

  @Test
  def testUnique() {
    /*
        wdata = [0:40 10:30 15:25 18:22 25 27 27 28 29 29 30 30 31 32 33 34 40 45 50];
        [actual ia ic] = unique(wdata)
     */
    val expected = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17,
      18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
      39, 40, 45, 50)
    val eia = Array[Int](1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 42, 43, 44, 45, 46, 63, 64, 65, 74, 75,
      76, 77, 78, 71, 72, 79, 58, 81, 82, 84, 86, 87, 88, 89, 90, 36, 37, 38, 39, 40, 91, 92,
      93).map(_ - 1) // Subtract 1 to go from matlab to JVM indices
    val eic = Array[Int]( 1,  2,  3,  4,  5,  6,  7,  8,  9, 10, 11, 12, 13, 14, 15, 16, 17,
        18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37, 38,
        39, 40, 41, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 27, 28, 29,
        30, 31, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26, 19, 20, 21, 22, 23, 26, 28, 28, 29,
        30, 30, 31, 31, 32, 33, 34, 35, 41, 42, 43).map(_ - 1) // Subtract 1 to go from matlab to JVM indices

    val (actual, ia, ic) = math.unique(wdata)

    assertEquals(expected.size, actual.size)
    actual.indices.foreach { i =>
      assertEquals(expected(i), actual(i), tolerance)
    }

    assertEquals(eia.size, ia.size)
    eia.indices.foreach { i =>
      assertEquals(eia(i), ia(i))
    }

    assertEquals(eic.size, ic.size)
    eic.indices.foreach { i =>
      assertEquals(eic(i), ic(i))
    }

  }

  def compare(s: Complex[Double], c: Complex[Double]) {
    assertEquals(s.real, c.real, tolerance)
    assertEquals(s.imag, c.imag, tolerance)
  }

}
