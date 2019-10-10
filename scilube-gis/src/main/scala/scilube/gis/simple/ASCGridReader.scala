package scilube.gis.simple

import java.net.URL
import java.util.StringTokenizer

import scilube.grid.{ArrayGrid, MutableGrid}
import java.io.{BufferedReader, File, InputStreamReader}

import mbarix4j.util.StringUtilities
import scilube.Matlib

object ASCGridReader {

  def read(url: URL): MutableGrid[Double, Double, Double] = {
    val reader = new BufferedReader(new InputStreamReader(url.openStream))
    val ncols = StringUtilities.getToken(reader.readLine(), 2).toInt
    val nrows = StringUtilities.getToken(reader.readLine(), 2).toInt
    val xllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
    val yllCorner = StringUtilities.getToken(reader.readLine(), 2).toDouble
    val cellSize = StringUtilities.getToken(reader.readLine(), 2).toDouble
    val noDataValue = StringUtilities.getToken(reader.readLine(), 2).toFloat

    //val x = xllCorner to (xllCorner + ((ncols - 1) * cellSize)) by cellSize
    //val y = yllCorner to (yllCorner + ((nrows - 1) * cellSize)) by cellSize

    val x =
      Matlib.linspace(xllCorner, xllCorner + (ncols - 1) * cellSize, ncols)
    val y = Matlib.linspace(yllCorner, yllCorner + (nrows - 1) * cellSize, nrows).reverse

    val grid = ArrayGrid(x, y, Double.NaN)
    for (j <- 0 until nrows) {
      val line = reader.readLine()
      val st = new StringTokenizer(line)
      for (i <- 0 until ncols) {
        val buf = st.nextToken().toDouble
        grid(i, j) = if (buf == noDataValue) Double.NaN else buf
      }
    }

    grid

  }

  def read(file: File): MutableGrid[Double, Double, Double] =
    read(file.toURI.toURL)

}
