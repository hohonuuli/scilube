package scilube.grid

/**
 * Mutable Grid implementation where the z values ae backed by Array[Array] data.
 *
 * @author Brian Schlining
 * @since 2012-09-04
 */
class ArrayGrid[A, B, C](val x: IndexedSeq[A], val y: IndexedSeq[B], val array: Array[Array[C]])
        extends Grid[A, B, C] with MutableGrid[A, B, C] {

    def z(i: Int, j: Int, k: C) { array(i)(j) = k }

    /**
     * Retrieves a value from grid based in the indices
     * @param i The x index
     * @param j The y index
     * @return The z value at i, j
     */
    def z(i: Int, j: Int): C = array(i)(j)

}

/**
 * Factory methods to create ArrayGrid implementations. I'm only creating factories for the most
 * commonly used types
 */
object ArrayGrid {

    def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], z: Array[Array[Double]]): DoubleArrayGrid[Double, Double] = {
        require(x.size == z.size, "x.size != z.size")
        require(y.size == z(0).size, "ysize != z(0).size")
        new DoubleArrayGrid(x, y, z)
    }

    def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], defaultValue: Double): DoubleArrayGrid[Double, Double] = {
        new DoubleArrayGrid(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })
    }

    def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], z: Array[Array[Float]]): FloatArrayGrid[Double, Double] = {
        require(x.size == z.size, "x.size != z.size")
        require(y.size == z(0).size, "ysize != z(0).size")
        new FloatArrayGrid(x, y, z)
    }

    def apply(x: IndexedSeq[Double], y: IndexedSeq[Double], defaultValue: Float): FloatArrayGrid[Double, Double] = {
        new FloatArrayGrid(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })
    }


}
