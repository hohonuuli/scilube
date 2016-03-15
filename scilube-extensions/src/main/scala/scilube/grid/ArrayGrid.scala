package scilube.grid

import scala.reflect.ClassTag

/**
 * Mutable Grid implementation where the z values ae backed by Array[Array] data. ArrayGrids use
 * image coordinate system, (i.e. origin is upper left)
 *
 * @author Brian Schlining
 * @since 2012-09-04
 */
class ArrayGrid[A, B, C: ClassTag](val x: IndexedSeq[A], val y: IndexedSeq[B], val array: Array[Array[C]])
    extends Grid[A, B, C] with MutableGrid[A, B, C] {

  require(x.size == array.size, "x.size != array.size")
  require(y.size == array(0).size, "ysize != array(0).size")

  def z(i: Int, j: Int, k: C): Unit = { array(i)(j) = k }

  /**
   * Retrieves a value from grid based in the indices
   * @param i The x index
   * @param j The y index
   * @return The z value at i, j
   */
  def z(i: Int, j: Int): C = array(i)(j)

  /**
   *
   * @param i0 Starting x index
   * @param i1 Ending x index (inclusive)
   * @param j0 Starting y index
   * @param j1 Ending y index (inclusive)
   * @return
   */
  def subgrid(i0: Int, i1: Int, j0: Int, j1: Int) = {
    val a = Array.ofDim[C](i1 - i0 + 1, j1 - j0 + 1)
    var xi = 0
    var yi = 0
    for (i <- i0 to i1; j <- j0 to j1) {
      a(xi)(yi) = array(i)(j)
    }
    new ArrayGrid(x.slice(i0, i1 + 1), y.slice(j0, j1 + 1), a)
  }

}

/**
 * Factory methods to create ArrayGrid implementations. I'm only creating factories for the most
 * commonly used types
 */
object ArrayGrid {

  def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], z: Array[Array[Double]]): DoubleArrayGrid[Double, Double] = {
    new DoubleArrayGrid(x, y, z)
  }

  def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], defaultValue: Double): DoubleArrayGrid[Double, Double] = {
    new DoubleArrayGrid(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })
  }

  def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], z: Array[Array[Float]]): FloatArrayGrid[Double, Double] = {
    new FloatArrayGrid(x, y, z)
  }

  def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], defaultValue: Float): FloatArrayGrid[Double, Double] = {
    new FloatArrayGrid(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })
  }

}
