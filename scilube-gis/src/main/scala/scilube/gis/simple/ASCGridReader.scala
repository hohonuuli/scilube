package scilube.gis.simple


import java.net.URL
import org.mbari.util.{StringUtilities}
import java.util.StringTokenizer
import scilube.grid.{MutableDoubleGrid, MutableGrid}
import java.io.{File, InputStreamReader, BufferedReader}


object ASCGridReader {

    def read(url: URL): MutableGrid[Double, Double, Double] = {
        val reader = new BufferedReader(new InputStreamReader(url.openStream))
        val ncols = StringUtilities.getToken(reader.readLine(), 2).toInt
        val nrows = StringUtilities.getToken(reader.readLine(), 2).toInt
        val xllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val yllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val cellSize = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val noDataValue = StringUtilities.getToken(reader.readLine(), 2).toFloat

        val x = xllCorner to (xllCorner + (ncols * cellSize)) by cellSize
        val y = yllCorner to (yllCorner + (nrows * cellSize)) by cellSize

        val grid = new MutableDoubleGrid(x, y, Double.NaN)
        for (j <- 0 until nrows) {
            val line = reader.readLine()
            val st = new StringTokenizer(line)
            for (i <- 0 until ncols) {
                val buf = st.nextToken().toDouble
                grid(i, j) =  if (buf == noDataValue) Double.NaN else buf
            }
        }

        return grid;

    }

    def read(file: File): MutableGrid[Double, Double, Double] = read(file.toURI.toURL)

}