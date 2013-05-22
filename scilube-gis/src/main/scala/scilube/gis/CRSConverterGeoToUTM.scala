package scilube.gis

import org.geotoolkit.referencing.CRS
import scilube.geometry.Point2D

/**
 * CoordinateReferenceSystem converter
 */
object CRSConverterGeoToUTM {

  private[this] val crsConverter = {
    val sourceCRS = CRS.decode("EPSG:4326")   // WGS84 (lat, lon)
    val targetCRS = CRS.decode("EPSG:32610")  // UTM Zone 10N
    new CRSConverter(sourceCRS, targetCRS)
  }

  /**
   * Convert the point to the new coordinate system
   */
  def apply(point: Point2D[Double]): (Double, Double) = apply(point.x, point.y)

  def apply[A](longitude: A, latitude: A)(implicit numeric: Numeric[A]): (Double, Double) = {
    val d = crsConverter.convert(latitude, longitude) // Have to flip x/y for this CRS
    (d.getX, d.getY)
  }

}