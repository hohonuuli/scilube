package scilube.grid

import java.util.{Arrays => JArrays}

/**
 * An ArrayGrid backed by an Array[Array[Double]]
 *
 * @author Brian Schlining
 * @since 2012-09-04
 */
class DoubleArrayGrid[A, B](x: IndexedSeq[A], y: IndexedSeq[B], z: Array[Array[Double]])
        extends ArrayGrid[A, B, Double](x, y, z)
        with NumericGrid[A, B, Double] {


    def this(x: IndexedSeq[A], y: IndexedSeq[B], defaultValue: Double = 0) = this(x, y, Array.tabulate(x.size, y.size) { (u, v) => defaultValue })

    def +[C](grid: Grid[A, B, C])(implicit numeric: Numeric[C]): DoubleArrayGrid[A, B] = {
        // Check dimensions
        require(grid.x.size == x.size, "Unable to add grid with different X dimensions")
        require(grid.y.size == y.size, "Unable to add grid with different Y dimensions")
        val newGrid = new DoubleArrayGrid[A, B](x, y, 0D)
        for (i <- 0 until x.size; j <- 0 until y.size) {
            newGrid(i, j) = z(i, j) + numeric.toDouble(grid(i, j))
        }
        return newGrid
    }

    /**
     * Normalize one grid by another. The returned grid is also normalized to itself so that
     * the sum of all it's cells = 1.
     *
     * @param effort The grid to use as a normalizer. For example ROV effort
     */
    def normalize[C : Numeric](effort: Grid[A, B, C] with NumericGrid[A, B, C]): DoubleArrayGrid[A,  B] = {
        // Check dimensions
        require(effort.x.size == x.size, "Unable to normalize grid with different X dimensions")
        require(effort.y.size == y.size, "Unable to normalize grid with different Y dimensions")
        val normalizedEffort = DoubleArrayGrid.normalize(effort)
        val normalizedGrid = new DoubleArrayGrid[A, B](x, y, 0D)
        for (xi <- 0 until x.size; yi <- 0 until y.size) {
            normalizedGrid(xi, yi) = if (effort(xi, yi) != 0) {
                z(xi, yi) / normalizedEffort(xi, yi)
            }
            else {
                // If there's no effort we have to toss this grid value. Otherwise
                // It can become Infinity, which causes problems.
                Double.NaN
            }
        }
        DoubleArrayGrid.normalize(normalizedGrid)

    }

    def normalize() = DoubleArrayGrid.normalize(this)

}

object DoubleArrayGrid {

    /**
     * Merges to grids together. Fills in missing values from the first grid with values
     * from the second one. They can be different sizes and resolutions.
     */
    def merge(grid1: Grid[Double, Double, Double], grid2: Grid[Double, Double, Double]) = {

        val grid = new DoubleArrayGrid(grid1.x, grid1.y, Double.NaN);

        val jx2 = grid2.x toArray
        val jy2 = grid2.y toArray

        val minX = grid2.x(0)
        val maxX = grid2.x(grid2.x.size - 1)
        val minY = grid2.y(0)
        val maxY = grid2.y(grid2.y.size - 1)

        for (i <- 0 until grid1.x.size; j <- 0 until grid1.y.size) {

            if ( (i * j) % 10000 == 0) {
                print(" (" + i + "," + j + ") ")
            }

            val z1 = grid1(i, j)
            if (z1.isNaN) {
                val x1 = grid1.x(i)
                val y1 = grid1.y(j)

                if (x1 >= minX && x1 <= maxX && y1 >= minY && y1 <= maxY) {
                    var ix: Int = JArrays.binarySearch(jx2, x1)
                    ix = if (ix < 0) { -ix - 2 } else ix - 1
                    var iy: Int = JArrays.binarySearch(jy2, y1)
                    iy = if (iy < 0) { -iy - 2 } else iy - 1
                    if (ix >= 0 && ix < grid2.x.size && iy >= 0 && iy < grid2.y.size) {
                        grid(i, j) = grid2.z(ix, iy)
                    }
                }
            }
            else {
                grid(i, j) = z1
            }
        }
        grid
    }

    /**
     * Normalizes a grid so that the sum of all it's values equals 1
     *
     * @param grid
     * @tparam A
     * @tparam B
     * @return
     */
    def normalize[A, B, C : Numeric](grid: Grid[A, B, C] with NumericGrid[A, B, C]): DoubleArrayGrid[A, B] = {
        val numeric = implicitly[Numeric[C]]
        val total = grid.sum()
        val normalizedGrid = new DoubleArrayGrid(grid.x, grid.y, 0D)
        for (xi <- 0 until grid.x.size; yi <- 0 until grid.y.size) {
            normalizedGrid(xi, yi) = numeric.toDouble(grid(xi, yi)) / total
        }
        normalizedGrid
    }

    /**
     * Dilates non-zero pixels in a grid
     * @param grid The grid to buffer
     * @param bufferSize The number of surrounding pixels to dilate by
     * @param minAcceptableZ The minimum valu in a grid to buffer around
     */
    def dilate(grid: Grid[Double, Double, Double], bufferSize: Int,
            minAcceptableZ: Double = 1): DoubleArrayGrid[Double, Double] = {

        val nx = grid.x.size
        val ny = grid.y.size
        println("nx = " + nx + ", ny = " + ny)

        // Create a new emtpy grid
        val newGrid = new DoubleArrayGrid(grid.x, grid.y)
        for (i0 <- 0 until nx;
             j0 <- 0 until ny;
             if grid(i0, j0) >= minAcceptableZ) {

            for (i1 <- -bufferSize to bufferSize; j1 <- -bufferSize to bufferSize) {
                val i = i0 + i1
                val j = j0 + j1
                if (i >= 0 && i < nx && j >= 0 && j < ny) {
                    newGrid(i, j) = newGrid(i, j) + grid(i, j)
                }
            }
        }

        newGrid

    }

    /**
     * Dilates non-zero pixels in a grid. Instead of adding the values, like
     * __dilate__ does. It just counts the number of times a pixels been
     * dilated.
     *
     * @param grid The grid to buffer
     * @param bufferSize The number of surrounding pixels to dilate by
     * @param minAcceptableZ The minimum valu in a grid to buffer around
     */
    def dilateByCount(grid: Grid[Double, Double, Double], bufferSize: Int,
            minAcceptableZ: Double = 1): DoubleArrayGrid[Double, Double] = {

        val nx = grid.x.size
        val ny = grid.y.size
        println("nx = " + nx + ", ny = " + ny)

        // Create a new emtpy grid
        val newGrid = new DoubleArrayGrid(grid.x, grid.y)
        for (i0 <- 0 until nx;
             j0 <- 0 until ny;
             if grid(i0, j0) >= minAcceptableZ) {

            for (i1 <- -bufferSize to bufferSize; j1 <- -bufferSize to bufferSize) {
                val i = i0 + i1
                val j = j0 + j1
                if (i >= 0 && i < nx && j >= 0 && j < ny) {
                    newGrid(i, j) = newGrid(i, j) + 1
                }
            }
        }

        newGrid

    }

}
