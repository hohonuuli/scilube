package scilube.grid

/**
 * An ArrayGrid backed by an Array[Array[Float]]
 *
 * @author Brian Schlining
 * @since 2012-09-04
 */
class FloatArrayGrid[A, B](x: IndexedSeq[A], y: IndexedSeq[B], z: Array[Array[Float]])
        extends ArrayGrid[A, B, Float](x, y, z) with NumericGrid[A, B, Float] {


    def this(x: IndexedSeq[A], y: IndexedSeq[B], defaultValue: Float = 0) = this(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })

    def +[C](grid: Grid[A, B, C])(implicit numeric: Numeric[C]): FloatArrayGrid[A, B] = {
        // Check dimensions
        require(grid.x.size == x.size, "Unable to add grid with different X dimensions")
        require(grid.y.size == y.size, "Unable to add grid with different Y dimensions")
        val newGrid = new FloatArrayGrid[A, B](x, y, 0F)
        for (i <- 0 until x.size; j <- 0 until y.size) {
            newGrid(i, j) = z(i, j) + numeric.toFloat(grid(i, j))
        }
        return newGrid
    }

    /**
     * Normalize one grid by another. The returned grid is also normalized to itself so that
     * the sum of all it's cells = 1.
     *
     * @param effort The grid to use as a normalizer. For example ROV effort
     */
    def normalize(effort: Grid[A, B, Float] with NumericGrid[A, B, Float]): FloatArrayGrid[A,  B] = {
        // Check dimensions
        require(effort.x.size == x.size, "Unable to normalize grid with different X dimensions")
        require(effort.y.size == y.size, "Unable to normalize grid with different Y dimensions")
        val normalizedEffort = FloatArrayGrid.normalize(effort)
        val normalizedGrid = new FloatArrayGrid[A, B](x, y, 0F)
        for (xi <- 0 until x.size; yi <- 0 until y.size) {
            normalizedGrid(xi, yi) = (if (effort(xi, yi) != 0) {
                z(xi, yi) / normalizedEffort(xi, yi)
            }
            else {
                // If there's no effort we have to toss this grid value. Otherwise
                // It can become Infinity, which causes problems.
                Float.NaN
            }).toFloat
        }
        FloatArrayGrid.normalize(normalizedGrid)
    }

    def normalize() = FloatArrayGrid.normalize(this)

}

object FloatArrayGrid {
    def normalize[A, B](grid: Grid[A, B, Float] with NumericGrid[A, B, Float]): FloatArrayGrid[A, B] = {
        val total = grid.sum()
        val normalizedGrid = new FloatArrayGrid(grid.x, grid.y, 0F)
        for (xi <- 0 until grid.x.size; yi <- 0 until grid.y.size) {
            normalizedGrid(xi, yi) = (grid(xi, yi) / total).toFloat
        }
        normalizedGrid
    }
}
