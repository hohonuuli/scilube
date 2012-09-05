package scilube.grid

/**
 * A mixin for Grids that store backing data as numeric values.
 *
 * @author Brian Schlining
 * @since 2012-09-04
 */
trait NumericGrid[A, B, C] {
    self: Grid[A, B, C] =>

    /**
     * @return The minimum value found in the grid
     */
    def min(implicit numeric: Numeric[C]): C = {
        var m = z(0, 0)
        val isMNaN = numeric.toDouble(m).isNaN
        for {xi <- 0 until x.size
             yi <- 0 until y.size} {
            val v = z(xi, yi)
            val isVNaN = numeric.toDouble(v).isNaN
            if (isMNaN && !isVNaN) {
                m = v
            }
            else if (!numeric.toDouble(v).isNaN) {
                m = numeric.min(m, v)
            }
        }
        return m
    }

    /**
     * @return The minimum value found in the grid
     */
    def max(implicit numeric: Numeric[C]): C = {
        var m = z(0, 0)
        val isMNaN = numeric.toDouble(m).isNaN
        for {xi <- 0 until x.size
             yi <- 0 until y.size} {
            val v = z(xi, yi)
            val isVNaN = numeric.toDouble(v).isNaN
            if (isMNaN && !isVNaN) {
                m = v
            }
            else if (!isVNaN) {
                m = numeric.max(m, v)
            }
        }
        return m
    }

    /**
     * Get the difference between the x values in a grid. Assumes an equally spaced x grid
     */
    def dx(implicit numeric: Numeric[A]): A = numeric.minus(x.take(2).tail(0), x.head)

    /**
     * Get the difference between the x values in a grid. Assumes an equally spaced x grid
     */
    def dy(implicit numeric: Numeric[B]): B = numeric.minus(y.take(2).tail(0), y.head)

    /**
     * Sum all the cells in a grid. NaN's are ignored.
     * @return the sum of all cells as a Double
     */
    def sum()(implicit numeric: Numeric[C]): Double = {
        var total: Double = 0
        for {xi <- 0 until x.size
             yi <- 0 until y.size} {
            val v = numeric.toDouble(z(xi, yi))
            if (!v.isNaN) {
                total = total + v
            }
        }
        return total
    }

    /**
     * Convert to X,Y,Z column oriented string
     */
    def toXYZ()(implicit numeric: Numeric[C]): String = {
        val sb = new StringBuilder("X,Y,Z\n")
        for (xi <- 0 until x.size; yi <- 0 until y.size) {
            val zval = numeric.toDouble(z(xi, yi))
            if (zval != 0 && !zval.isNaN && !zval.isInfinity) {
                sb.append(x(xi)).append(",").append(y(yi)).append(",").append(z(xi, yi)).append("\n")
            }
        }
        return sb.toString
    }

    /**
     * Sum a grid by columns producing an grid.x.size X 1 Array
     */
    def sumX()(implicit numeric: Numeric[C]): Array[Double] = {
        val totals = new Array[Double](x.size);
        for (i <- 0 until x.size) {
            for (j <- 0 until y.size) {
                totals(i) = totals(i) + numeric.toDouble(z(i, j))
            }
        }
        return totals
    }

    /**
     * Sum a grid by rows producing an grid.y.size X 1 Array
     */
    def sumY()(implicit numeric: Numeric[C]): Array[Double] = {
        val totals = new Array[Double](y.size);
        for (i <- 0 until y.size) {
            for (j <- 0 until x.size) {
                totals(i) = totals(i) + numeric.toDouble(z(j, i))
            }
        }
        return totals
    }
}
