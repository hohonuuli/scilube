package scilube.spire

import spire.math.Complex
import org.junit.Assert._
import org.junit.Test

/**
 *
 * @author Brian Schlining
 * @since 2012-06-08
 */

class ComplexLibTest {

    private[this] val a = Complex(3D, 4D)
    private[this] val b = Complex(8D, -3D)
    private[this] val tolerance = 0.00000000001


    def testMod() {}

    @Test
    def testExp() {
        val fn = ComplexLib.exp(_: Complex[Double])
        val ae = Complex(-13.128783081462158, -15.200784463067954)
        run(a, ae, fn)

        val be = Complex(-2.951126039852479e+03, -4.206728151574542e+02)
        run(b, be, fn)
    }

    @Test
    def testLog() {
        val fn = ComplexLib.log(_: Complex[Double])
        val ae = Complex(1.609437912434100, 0.927295218001612)
        run(a, ae, fn)

        val be = Complex(2.145229720574195, 0.358770670270572)
        run(b, be, fn)

    }

    @Test
    def testSqrt() {
        val fn = ComplexLib.sqrt(_: Complex[Double])
        val ae = Complex(2.000000000000000, 1.000000000000000)
        run(a, ae, fn)

        val be = Complex(2.876108807513854, 0.521537987742758)
        run(b, be, fn)
    }

    def testSin() {}

    def testCos() {}

    def testSinh() {}

    def testCosh() {}

    def testTan() {}

    def testFft() {}


    def run(v: Complex[Double], e: Complex[Double], fn: Complex[Double] => Complex[Double]) {
        val a = fn(v)
        assertEquals(e.real, a.real, tolerance)
        assertEquals(e.imag, a.imag, tolerance)
    }

}
