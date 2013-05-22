package scilube.grid

import scilube.geometry.{IntPoint2D, Point2D}
import scilube.Matlib


/**
 * Wrapper class the can provide search functionality on Grids with
 * axis defined as double values. Grid origins are the lower, left corners
 *
 * @author Brian Schlining
 * @since 2013-01-10
 */
class GridSearcher[A : Numeric, B](val grid: Grid[A, A, B]) {


  private[this] val (xCoords, yCoords, isXForward, isYForward) =  {

    val numeric = implicitly[Numeric[A]]

    val x0 = numeric.toDouble(grid.x.head)
    val x1 = numeric.toDouble(grid.x.last)
    val isXForward = x0 < x1

    val y0 = numeric.toDouble(grid.y.head)
    val y1 = numeric.toDouble(grid.y.last)
    val isYForward = y0 < y1

    val xx = if (isXForward) grid.x else grid.x.reverse
    val yy = if (isYForward) grid.y else grid.y.reverse

    (xx.map(numeric.toDouble(_)).toArray[Double],
        yy.map(numeric.toDouble(_)).toArray[Double],
        isXForward,
        isYForward)
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
    val i = Matlib.near(xCoords, xd, inclusive = true)
    if (i >= 0) {
      val j = Matlib.near(yCoords, yd, inclusive = true)
      if (j >= 0) {
        val ii = if (isXForward) i else grid.x.size - i - 1
        val jj = if (isYForward) j else grid.y.size - j - 1
        Option(new IntPoint2D(ii, jj))
      } else None
    }
    else None
  }

}
