package scilube

import org.junit.Test
import org.junit.Assert._

/**
 *
 * @author Brian Schlining
 * @since 2012-06-13
 */

class ProbabilitiesTest {

  val x = (1 to 100).map(_.toDouble).toArray


  @Test
  def testQuantile {
    val correct = Array(3.0, 25.5, 50.5, 75.5, 98.0)
    val q = Matlib.quantile(x, Array(0.025, 0.25, 0.50, 0.75, 0.975))
    assertArrayEquals(correct, q, 0.00001)
    q.foreach(d => print(d + " "))

  }


}
