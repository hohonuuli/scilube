package sciencelube.gis.simple


import java.net.URL
import org.mbari.util.{StringUtilities}
import java.util.StringTokenizer
import sciencelube.grid.{MutableDoubleGrid, MutableGrid}
import java.io.{File, InputStreamReader, BufferedReader}


object ASCGridReader {

    def read(url: URL): MutableGrid[Double, Double, Double] = {
        val reader = new BufferedReader(new InputStreamReader(url.openStream))
        val xSize = StringUtilities.getToken(reader.readLine(), 2).toInt
        val ySize = StringUtilities.getToken(reader.readLine(), 2).toInt
        val xllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val yllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val cellSize = StringUtilities.getToken(reader.readLine(), 2).toDouble
        val noDataValue = StringUtilities.getToken(reader.readLine(), 2).toFloat

        //val x = xllCorner to (xllCorner + ((xSize - 1) * cellSize)) by cellSize
        //val y = yllCorner to (yllCorner + ((ySize - 1) * cellSize)) by cellSize

        val x = xllCorner to (xllCorner + (xSize * cellSize)) by cellSize
        val y = yllCorner to (yllCorner + (ySize* cellSize)) by cellSize

        val grid = new MutableDoubleGrid(x, y, Double.NaN)
        for (r <- 0 until ySize) {
            val line = reader.readLine()
            val st = new StringTokenizer(line)
            val idx = ySize - 1 - r // Largest y values are 1st in the array, so count backwards

            for (c <- 0 until xSize) {
                val buf: Double = st.nextToken().toDouble
                grid(c, idx) =  if (buf == noDataValue) {
                    Double.NaN
                }
                else {
                    buf
                }
            }
        }

        return grid;

    }

    def read(file: File): MutableGrid[Double, Double, Double] = read(file.toURI.toURL)

}