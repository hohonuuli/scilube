package scilube.jfreechart.renderer

import scala.beans.BeanProperty
import java.awt.{Color, Paint}
import java.lang.{Double => JDouble}
import scala.math._
import org.jfree.chart.renderer.PaintScale
import mbarix4j.math.Matlib

/**
  * Reference http://grepcode.com/file/repo1.maven.org/maven2/jfree/jfreechart/1.0.11/org/jfree/chart/renderer/GrayPaintScale.java#GrayPaintScale
  * @author Brian Schlining
  * @since Oct 11, 2010
  */
class JetPlusPaintScale(
    @BeanProperty val lowerBound: Double,
    @BeanProperty val upperBound: Double,
    m: Int = 64
) extends PaintScale {

  require(
    lowerBound < upperBound,
    "Requires lowerBound (" + lowerBound + ") < upperBound (" +
      upperBound + ")"
  )

  private final val emptyColor = new Color(255, 255, 255)

  val colors = {
    val n = (round(m / 5d) - 1.toInt).toInt
    val nd = n.toDouble
    val k = (n + 5).toInt
    val k0 = (for (i <- 1 to k) yield { 0d }).toList
    val x = (1 to n).map(_ / nd).toList
    val y = ((nd / 2).toInt to n).map(_ / nd).toList
    val y0 = y.map(_ * 0d)
    val e = x.map { a =>
      1d
    }.toList
    val e0 = e.map(_ * 0d)

    val r = Matlib
      .linspace(0.9, 0, k)
      .toList ::: y0 ::: e0 ::: x ::: e ::: Matlib
      .linspace(1d, 0.3, y.size)
      .toList
    val g = k0 ::: y0 ::: x ::: e ::: x.reverse ::: y0
    val b = Matlib
      .linspace(0.75, 0.50, k)
      .toList ::: y ::: e ::: x.reverse ::: e0 ::: y0

    for (i <- 0 until m) yield {
      new Color((r(i) * 255).toInt, (g(i) * 255).toInt, (b(i) * 255).toInt)
    }

  }

  def getPaint(value: Double): Paint = {
    val color = if (JDouble.isNaN(value)) {
      emptyColor
    } else {
      colors(getIndex(value))
    }

    color

  }

  def getIndex(value: Double): Int = {
    val v = min(max(value, lowerBound), upperBound)
    min(round((v - lowerBound) / (upperBound - lowerBound) * m).toInt, m - 1)
  }

}
