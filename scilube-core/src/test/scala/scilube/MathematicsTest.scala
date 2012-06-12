package scilube

import _root_.spire.math.Complex
import org.junit.Assert._
import org.junit.Test

/**
 *
 * @author Brian Schlining
 * @since 2012-06-11
 */

class MathematicsTest {

    private[this] val math = new Mathematics {}
    private[this] val tolerance = 0.00000000001

    @Test
    def testFft() {
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
        val d = (1 to 10).map(_.toDouble).toArray
        val a = math.prod(d)
        val e = 3628800D
        assertEquals(e, a, tolerance)
    }

    def compare(s: Complex[Double], c: Complex[Double]) {
        assertEquals(s.real, c.real, tolerance)
        assertEquals(s.imag, c.imag, tolerance)
    }

}
