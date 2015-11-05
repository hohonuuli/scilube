import java.text.SimpleDateFormat
import java.util.{Date, TimeZone}

/**
 * '''scilube-core''' - Science libraries for Scala.
 *
 * The scilube-core package contains the base math functions for Scilube. These functions are loosely
 * modeled after [[http://www.mathworks.com/products/matlab/ Matlab's]] functions. Why Matlab? For
 * many science applications, the code is prototyped in Matlab and then ported to other languages
 * for efficiency reasons or to satisfy deployment requirements. By having a functions with simlilar
 * names and actions, I hope to simplify porting of applications.
 *
 * A large portion of the functionality of this package can be accessed using a single object,
 * [[scilube.Matlib]]. The usage will be obvious to anyone who's worked in Matlab. Many functions,
 * rather than accepting 2D matrices take 1-D `Array[Double]` instead.
 */
package object scilube {

  /**
   * Adds function to support simple math with scalar values
   * @param a
   */
  implicit class RichArray(a: Array[Double]) {

    def +[@specialized(Int, Long, Float, Double) T: Numeric](s: Array[T]): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      (for (i <- 0 until a.size) yield a(i) + numeric.toDouble(s(i))).toArray
    }

    def -[@specialized(Int, Long, Float, Double) T: Numeric](s: Array[T]): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      (for (i <- 0 until a.size) yield a(i) - numeric.toDouble(s(i))).toArray
    }

    def *[@specialized(Int, Long, Float, Double) T: Numeric](s: Array[T]): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      (for (i <- 0 until a.size) yield a(i) * numeric.toDouble(s(i))).toArray
    }

    def /[@specialized(Int, Long, Float, Double) T: Numeric](s: Array[T]): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      (for (i <- 0 until a.size) yield a(i) / numeric.toDouble(s(i))).toArray
    }

    def subset(idx: Seq[Int]): Array[Double] = Matlib.subset(a, idx)

    def find(fn: Double => Boolean): Array[Int] = Matlib.find(a, fn).toArray

  }


}


// Allow Calling Java 8 functions from Scala
// From http://www.michaelpollmeier.com/2014/10/12/calling-java-8-functions-from-scala/
/*
import java.util.function.{ Function ⇒ JFunction, Predicate ⇒ JPredicate, BiPredicate }
//usage example: `i: Int ⇒ 42`
implicit def toJavaFunction[A, B](f: Function1[A, B]) = new JFunction[A, B] {
  override def apply(a: A): B = f(a)
}

//usage example: `i: Int ⇒ true`
implicit def toJavaPredicate[A](f: Function1[A, Boolean]) = new JPredicate[A] {
  override def test(a: A): Boolean = f(a)
}

//usage example: `(i: Int, s: String) ⇒ true`
implicit def toJavaBiPredicate[A, B](predicate: (A, B) ⇒ Boolean) = new BiPredicate[A, B] {
  def test(a: A, b: B) = predicate(a, b)
}
*/
