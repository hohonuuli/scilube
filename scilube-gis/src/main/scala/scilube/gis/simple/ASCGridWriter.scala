package scilube.gis.simple

import scilube.grid.{NumericGrid, Grid}
import java.io.{FileWriter, BufferedWriter, File}

/**
 *
 * @author Brian Schlining
 * @since 2012-04-23
 */

object ASCGridWriter {

    def write(file: File, grid: Grid[Double, Double, Double] with NumericGrid[Double, Double, Double],
            nodataValue: Double = -9999D) = {

        val dx = grid.dx
        //val dy = grid.dy
        val cellsize = dx
        val xllcorner = grid.x.filter(v => !v.isInfinite && !v.isNaN).min
        val yllcorner = grid.y.filter(v => !v.isInfinite && !v.isNaN).min
        val ncols = grid.x.size
        val nrows = grid.y.size

        val writer = new BufferedWriter(new FileWriter(file))
        writer.write("ncols " + ncols + "\n")
        writer.write("nrows " + nrows + "\n")
        writer.write("xllcorner " + xllcorner + "\n")
        writer.write("yllcorner " + yllcorner + "\n")
        writer.write("cellsize " + cellsize + "\n")
        writer.write("nodata_value " + nodataValue + "\n")

        val fmt = "%.4f "
        val nodataString = fmt.format(nodataValue)
        for (j <- 0 until nrows ) {
            for (i <- 0 until ncols) {
                val z = grid(i, j)
                if (z.isNaN || z.isInfinity) {
                    writer.write(nodataString)
                }
                else {
                    writer.write(fmt.format(z))
                }
            }
            writer.write("\n")
        }

        writer.close()

    }

}
