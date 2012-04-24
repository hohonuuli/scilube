package sciencelube.gis

import org.geotoolkit.geometry.DirectPosition2D
import org.geotoolkit.referencing.CRS
import sciencelube.geometry.Point2D

/**
 * CoordinateReferenceSystem converter
 */
class CRSConverterGeoToUTM {

    val crsConverter = {
        val sourceCRS = CRS.decode("EPSG:4326")   // WGS84 (lat, lon)
        val targetCRS = CRS.decode("EPSG:32610")  // UTM Zone 10N
        new CRSConverter(sourceCRS, targetCRS)
    }

    /**
     * Convert the point to the new coordinate system
     */
    def convert(point: Point2D[Double]): DirectPosition2D = convert(point.x, point.y)

    def convert[A](lon: A, lat: A)(implicit numeric: Numeric[A]): DirectPosition2D =
        crsConverter.convert(lat, lon) // Have to flip x/y for this CRS
}