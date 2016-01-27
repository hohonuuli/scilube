package scilube.imglib2.fuzzysets

/**
 * Fuzzy set membership function; Triangular
 *
 * See page 177 and 178 of Digital Image Processing 3rd Edition, Gonzalez and Woods
 * {{{
 *           a
 *          one
 *          /\
 *        /  \
 *      /    \
 *    /      \
 *  a-b     a+c
 * lowZero  highZero
 * }}}
 *
 * @author Brian Schlining
 * @since 2012-03-12
 *
 * @param one The center or peak of the triangle
 * @param lowZero value less than __one__ value
 * @param highZero value greater than __one__ value
 */
class TriangularFn(val lowZero: Double, val one: Double, val highZero: Double) extends (Double => Double) {
  require(lowZero < one)
  require(highZero > one)

  private[this] val a = one
  private[this] val b = one - lowZero
  private[this] val c = highZero - one

  def apply(z: Double): Double = if (lowZero <= z && z < one) {
    1 - (a - z) / b
  } else if (one <= z && z <= highZero) {
    1 - (z - a) / c
  } else {
    0
  }
}

