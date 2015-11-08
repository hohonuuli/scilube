package scilube

import org.scalatest._

class MatlibSpec extends FlatSpec with Matchers {
  
  "Matlib" should "sort an array correctly" in {
    val a = Array("D", "A", "C", "B")
    val expected = Array("A", "B", "C", "D")
    val i = Matlib.sort(a, (i: String, j: String) => i < j)
    val b = Matlib.subset(a, i)
    b should contain theSameElementsInOrderAs expected

    import scala.math.Ordering._
    val j = Matlib.sort(a)
    val c = Matlib.subset(a, j)
    c should contain theSameElementsInOrderAs expected
  }
}