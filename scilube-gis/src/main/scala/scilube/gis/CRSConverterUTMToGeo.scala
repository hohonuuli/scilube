package scilube.gis

import org.geotoolkit.referencing.CRS
import scilube.geometry.Point2D
import org.geotoolkit.geometry.DirectPosition2D

/**
 *
 * @author Brian Schlining
 * @since 2013-05-20
 */
object CRSConverterUTMToGeo {

  private[this] val crsConverter = {
    val sourceCRS = CRS.decode("EPSG:32610") // UTM Zone 10N
    val targetCRS = CRS.decode("EPSG:4326") // WGS84 (lat, lon)
    new CRSConverter(sourceCRS, targetCRS)
  }

  /**
   * Convert the point to the new coordinate system
   */
  def apply(point: Point2D[Double]): (Double, Double) = apply(point.x, point.y)

  def apply[A](easting: A, northing: A)(implicit numeric: Numeric[A]): (Double, Double) = {
    val d = crsConverter.convert(easting, northing)
    (d.getY, d.getX) // Have to flip x/y for this CRS
  }

}
