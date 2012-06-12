package scilube.spire

import spire.math.Complex
import org.junit.Assert._
import org.junit.Test
import org.apache.commons.math3.complex.{Complex => AComplex}

/**
 * Use Apache Commons 3 Complex class to provide '''truth'''
 *
 * @author Brian Schlining
 * @since 2012-06-08
 */
class ComplexLibTest {

    private[this] val spire = Seq(Complex(3D, 4D), Complex(8D, -3D))
    private[this] val commons = Seq(new AComplex(3D, 4D), new AComplex(8D, -3D))
    private[this] val tolerance = 0.00000000001

    @Test
    def testMod() {
        spire.indices.foreach{ i =>
            compare(ComplexLib.mod((spire(i))), new AComplex(commons(i).abs(), 0))
        }
    }

    @Test
    def testExp() {
        spire.indices.foreach { i =>
            compare(ComplexLib.exp(spire(i)), commons(i).exp())
        }
    }

    @Test
    def testLog() {
        spire.indices.foreach { i =>
            compare(ComplexLib.log(spire(i)), commons(i).log())
        }
    }

    @Test
    def testSqrt() {
        spire.indices.foreach { i =>
            compare(ComplexLib.sqrt(spire(i)), commons(i).sqrt())
        }
    }

    @Test
    def testSin() {
        spire.indices.foreach { i =>
            compare(ComplexLib.sin(spire(i)), commons(i).sin())
        }
    }

    @Test
    def testCos() {
        spire.indices.foreach { i =>
            compare(ComplexLib.cos(spire(i)), commons(i).cos())
        }
    }

    @Test
    def testSinh() {
        spire.indices.foreach { i =>
            compare(ComplexLib.sinh(spire(i)), commons(i).sinh())
        }
    }

    @Test
    def testCosh() {
        spire.indices.foreach { i =>
            compare(ComplexLib.cosh(spire(i)), commons(i).cosh())
        }
    }

    @Test
    def testTan() {
        spire.indices.foreach { i =>
            compare(ComplexLib.tan(spire(i)), commons(i).tan())
        }
    }

    @Test
    def testFft() {
        val d = (1 to 10).map(i => Complex(i.toDouble, 0D)).toArray
        val a = ComplexLib.fft(d)
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


        val a2 = ComplexLib.fft(a)
        def e2 = Seq(Complex(10D, 0D),
            Complex(100D, 0D),
            Complex(90D, 0D),
            Complex(80D, 0D),
            Complex(70D, 0D),
            Complex(60D, 0D),
            Complex(50D, 0D),
            Complex(40D, 0D),
            Complex(30D, 0D),
            Complex(20D, 0D))
        a2.indices.foreach { i =>
            compare(e2(i), a2(i))
        }
    }

    @Test
    def testIfft() {
        val d = (1 to 10).map(i => Complex(i.toDouble, 0D)).toArray
        val a = ComplexLib.ifft(ComplexLib.fft(d), true)
        a.indices.foreach { i =>
            compare(d(i), a(i))
        }
    }



    private def compare(s: Complex[Double], c: AComplex) {
        assertEquals(s.real, c.getReal, tolerance)
        assertEquals(s.imag, c.getImaginary, tolerance)
    }

    private def compare(s: Complex[Double], c: Complex[Double]) {
        assertEquals(s.real, c.real, tolerance)
        assertEquals(s.imag, c.imag, tolerance)
    }

}
