package scilube.jfreechart.renderer

import scala.beans.BeanProperty
import org.jfree.chart.renderer.PaintScale
import java.awt.{Color, Paint}
import scala.math._
import scala.Double
import scilube.Matlib

/**
 *
 * @author Brian Schlining
 * @since 2012-08-29
 */
class HaxbyPaintScale(@BeanProperty val lowerBound: Double,
                      @BeanProperty val upperBound: Double,
                      m: Int = 64)
    extends PaintScale {

  require(
      lowerBound < upperBound,
      "Requires lowerBound (" + lowerBound + ") < upperBound (" +
        upperBound + ")")

  private[this] val emptyColor = new Color(255, 255, 255)

  private[this] val ncolors = 11D
  private[this] val c1 =
    Array[Double](37, 40, 50, 106, 138, 205, 240, 255, 255, 255, 255)
  private[this] val c2 =
    Array[Double](57, 127, 190, 235, 236, 255, 236, 189, 161, 186, 255)
  private[this] val c3 =
    Array[Double](175, 251, 255, 255, 174, 162, 121, 87, 68, 133, 255)

  val colors = {
    val pp = (1D to m.toDouble by ((m - 1) / (ncolors - 1))).toArray
    val mm = (1D to m.toDouble by 1D).toArray
    val r = Matlib.interp1(pp, c1, mm)
    val g = Matlib.interp1(pp, c2, mm)
    val b = Matlib.interp1(pp, c3, mm)

    for (i <- 0 until m) yield {
      new Color(r(i).toInt, g(i).toInt, b(i).toInt)
    }

  }

  def getPaint(value: Double): Paint = {
    val color = if (value.isNaN) {
      emptyColor
    }
    else {
      colors(getIndex(value))
    }

    color

  }

  def getIndex(value: Double): Int = {
    val v = min(max(value, lowerBound), upperBound)
    min(round((v - lowerBound) / (upperBound - lowerBound) * m).toInt, m - 1)
  }

}
