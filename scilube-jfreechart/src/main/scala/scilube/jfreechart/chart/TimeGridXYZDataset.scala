package scilube.jfreechart.chart

import java.lang.{Double => JDouble, Long => JLong}
import java.util.Date
import org.jfree.data.DomainOrder
import org.jfree.data.general.{DatasetGroup, DatasetChangeListener}
import org.jfree.data.xy.{XYZDataset}
import scala.math._
import scilube.grid.Grid

/**
 *
 * @author Brian Schlining
 * @since Oct 19, 2010
 */

class TimeGridXYZDataset(grid: Grid[Date, Double, Double], seriesKey: Comparable[_], 
    hideZeros: Boolean = false) extends XYZDataset {


    def setGroup(series: DatasetGroup): Unit = { /* Do nothing */ }

    def getGroup: DatasetGroup = null

    def removeChangeListener(series: DatasetChangeListener): Unit = { /* ignore - this dataset never changes */ }

    def addChangeListener(series: DatasetChangeListener): Unit = { /* ignore - this dataset never changes */}

    def getYValue(series: Int, item: Int): Double = grid.y(yIndex(item))

    def getY(series: Int, item: Int): Number = new JDouble(getYValue(series, item))

    def getXValue(series: Int, item: Int): Double = grid.x(xIndex(item)).getTime

    def getX(series: Int, item: Int): Number = getXValue(series, item).longValue

    def getItemCount(series: Int): Int = grid.x.size * grid.y.size

    def getDomainOrder: DomainOrder = DomainOrder.ASCENDING

    def indexOf(series: Comparable[_]): Int = 0

    def getSeriesKey(series: Int): Comparable[_] = seriesKey

    def getSeriesCount: Int = 1

    def getZValue(series: Int, item: Int): Double = {
        val v = grid(xIndex(item), yIndex(item))
        if (hideZeros && v == 0D) {
            JDouble.NaN
        }
        else {
            v
        }
    }

    def getZ(series: Int, item: Int): Number = new JDouble(getZValue(series, item))

    private def yIndex(item: Int) = item % grid.y.size

    private def xIndex(item: Int): Int = floor(item / grid.y.size).toInt

}