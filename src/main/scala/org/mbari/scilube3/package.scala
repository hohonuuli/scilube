package org.mbari

package object scilube3 {

  /** Adds function to support simple math with scalar values
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

    def findIdx(fn: Double => Boolean): Array[Int] = Matlib.find(a, fn).toArray

  }

  implicit class ScalarArrayOps(a: Array[Double]) {
    def +[@specialized(Int, Long, Float, Double) T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val d       = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) + d).toArray
    }

    def -[@specialized(Int, Long, Float, Double) T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val d       = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) - d).toArray
    }

    def *[@specialized(Int, Long, Float, Double) T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val d       = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) * d).toArray
    }

    def /[@specialized(Int, Long, Float, Double) T: Numeric](s: T): Array[Double] = {
      val numeric = implicitly[Numeric[T]]
      val d       = numeric.toDouble(s)
      (for (i <- 0 until a.size) yield a(i) / d).toArray
    }
  }

}
