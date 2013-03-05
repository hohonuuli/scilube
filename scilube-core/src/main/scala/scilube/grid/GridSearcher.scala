package scilube.grid

import org.mbari.math.{Matlib}
import scilube.geometry.{IntPoint2D, Point2D}


/**
 * Wrapper class the can provide search functionality on Grids with
 * axis defined as double values.
 *
 * @author Brian Schlining
 * @since 2013-01-10
 */
class GridSearcher[A : Numeric, B](val grid: Grid[A, A, B]) {


  private[this] val (xCoords, yCoords) =  {
    val numeric = implicitly[Numeric[A]]
    (grid.x.map(numeric.toDouble(_)).toArray[Double],
        grid.x.map(numeric.toDouble(_)).toArray[Double])
  }

  /**
   * Searches the grid for the pixel that contains the provided coordinate
   * @param x The x coordinate
   * @param y The y coordinate
   * @return Option value, if x and y are within the bounds of the grid then a
   *         [[scilube.geometry.Point2D]] is returned that contains the x and y
   *         indices in the grid pixel for the given coordinates. If x and y are
   *         outside the bounds then [[scala.None]] is returned.
   */
  def search[@specialized(Int, Long, Float, Double) C : Numeric](x: C, y: C): Option[Point2D[Int]] = {
    val numericC = implicitly[Numeric[C]]
    val xd = numericC.toDouble(x)
    val yd = numericC.toDouble(y)
    val i = Matlib.near(xCoords, xd, true)
    if (i >= 0) {
      val j = Matlib.near(yCoords, yd, true)
      if (j >= 0) Option(new IntPoint2D(i, j)) else None
    }
    else None
  }

}
