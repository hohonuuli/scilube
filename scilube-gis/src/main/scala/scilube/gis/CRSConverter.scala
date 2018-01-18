package scilube.gis

import org.geotoolkit.referencing.CRS
import org.geotoolkit.geometry.DirectPosition2D
import org.opengis.referencing.crs.CoordinateReferenceSystem
import scilube.geometry.{DoublePoint2D, Point2D}

/**
 * @param sourceCRS The  source coordinate system (e.g. {{{
 *  CRS.decode("EPSG:4326"); // WGS84 (lat, lon)
 *  DefaultGeographicCRS.WGS84 // WGS84 (lon, lat)
 * }}}
 * @param targetCRS The target coordinate system (e.g. {{{
 *  CRS.decode("EPSG:32610") // UTM Zone 10N
 * }}}
 */
class CRSConverter(val sourceCRS: CoordinateReferenceSystem,
                   val targetCRS: CoordinateReferenceSystem) {

  /**
   * Alternate constructor that uses the string representations of the CRS
   * @param src The source crs. e.g. "EPSG:4326"
   * @param target The target crs. e.g. "EPSG:32610"
   */
  def this(src: String, target: String) =
    this(CRS.decode(src), CRS.decode(target))

  private val mathTransform = CRS.findMathTransform(sourceCRS, targetCRS)

  /**
   * Convert the point to the new coordinate system
   */
  def convert(point: Point2D[Double]): DirectPosition2D = {
    val sourcePoint = new DirectPosition2D(sourceCRS)
    sourcePoint.setLocation(point)
    val targetPoint = new DirectPosition2D(targetCRS)
    mathTransform.transform(sourcePoint, targetPoint).asInstanceOf[DirectPosition2D]
  }

  def convert[A](x: A, y: A)(implicit numeric: Numeric[A]): DirectPosition2D =
    convert(new DoublePoint2D(numeric.toDouble(x), numeric.toDouble(y)))

}
