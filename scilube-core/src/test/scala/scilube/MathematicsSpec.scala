package scilube

import org.scalatest.{FlatSpec, Matchers}

/**
  * @author Brian Schlining
  * @since 2017-01-11T10:22:00
  */
class MathematicsSpec extends FlatSpec with Matchers {

  "Mathematics" should "extrapolate using extrap1" in {

    val x = Array[Double](1, 2, 3, 4, 5)
    val y = Array[Double](1, 2, 3, 4, 5)
    val xi = Array[Double](2, 4, 6, 8, 10)
    val yi = Matlib.extrap1(x, y, xi)
    yi should be (Array[Double](2, 4, 6, 8, 10))

  }

  it should "extrapolate using extrap1 with complexity" in {
    val x = Array[Double](1, 2, 3, 4, 5)
    val y = Array[Double](1, 2, 8, 4, 5)
    val xi = Array[Double](2, 4, 6, 8, 10)
    val yi = Matlib.extrap1(x, y, xi)
    yi should be (Array[Double](2, 4, 6, 8, 10))
  }

  it should "extrapolate using extrap1 with more complexity" in {
    val x = Array[Double](1, 2, 3, 4, 5)
    val y = Array[Double](1, 2, 8, 4, 8)
    val xi = Array[Double](2, 4, 6, 8, 10)
    val yi = Matlib.extrap1(x, y, xi)
    yi should be (Array[Double](2, 4, 12, 20, 28))
  }

}
