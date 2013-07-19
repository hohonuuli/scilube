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

    def +[T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val sd = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) + sd).toArray
    }

    def -[T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val sd = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) - sd).toArray
    }

    def *[T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val sd = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) * sd).toArray
    }

    def /[T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val sd = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) / sd).toArray
    }

    def subset(idx: Seq[Int]): Array[Double] = Matlib.subset(a, idx)

  }

}