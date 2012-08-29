package scilube.gis.simple

import math._
import scala.Numeric
import scilube.Matlib

/**
 *
 * @author Brian Schlining
 * @since 2012-08-29
 */
object GIS {

    /**
     * Convert decimal degrees to UTM coordinates
     * @param latitude
     * @param longitude
     * @tparam A The type of latitude
     * @tparam B The type of longitude
     * @return A tuple of (easting, northing and zone) ... (Double, Double, String)
     */
    def deg2utm[A: Numeric, B : Numeric](latitude: A, longitude: B): (Double, Double, String) = {

        val numericA = implicitly[Numeric[A]]
        val numericB = implicitly[Numeric[B]]
        val la = numericA.toDouble(latitude)
        val lo = numericB.toDouble(longitude)


        val sa = 6378137.000000
        val sb = 6356752.314245

        val e2 = pow(pow(sa, 2) - pow(sb, 2), 0.5 ) / sb
        val e2cuadrada = pow(e2, 2)
        val c = pow(sa, 2) / sb

        val lat = la * Pi / 180
        val lon = lo * Pi / 180

        val Huso = Matlib.fix((lo / 6) + 31)
        val S = (Huso * 6) - 183
        val deltaS = lon - (S * (Pi / 180))

        val Letra = if (la < -72) {
            'C'
        }
        else if (la < -64) {
            'D'
        }
        else if (la < -56) {
            'E'
        }
        else if (la < -48) {
            'F'
        }
        else if (la < -40) {
            'G'
        }
        else if (la < -32) {
            'H'
        }
        else if (la < -24) {
            'J'
        }
        else if (la < -16) {
            'K'
        }
        else if (la < -8) {
            'L'
        }
        else if (la < 0) {
            'M'
        }
        else if (la < 8) {
            'N'
        }
        else if (la < 16) {
            'P'
        }
        else if (la < 24) {
            'Q'
        }
        else if (la < 32) {
            'R'
        }
        else if (la < 40) {
            'S'
        }
        else if (la < 48) {
            'T'
        }
        else if (la < 56) {
            'U'
        }
        else if (la < 64) {
            'V'
        }
        else if (la < 72) {
            'W'
        }
        else {
            'X'
        }

        val a = cos(lat) * sin(deltaS)
        val epsilon = 0.5 * log((1 +  a) / (1 - a))
        val nu = atan( tan(lat) / cos(deltaS) ) - lat
        val v = c / pow( ( 1 + ( e2cuadrada * pow( cos(lat),  2 ) ) ), 0.5 ) * 0.9996
        val ta = (e2cuadrada / 2) * pow(epsilon, 2) * pow(cos(lat), 2)
        val a1 = sin(2 * lat)
        val a2 = a1 * pow(cos(lat), 2)
        val j2 = lat + (a1 / 2)
        val j4 = ((3 * j2 ) + a2) / 4;
        val j6 = ((5 * j4) + (a2 * pow(cos(lat),2))) / 3
        val alfa = (3D / 4D) * e2cuadrada
        val beta = (5D / 3D) * pow(alfa, 2)
        val gama = ( 35D / 27D ) * pow(alfa, 3)
        val Bm = 0.9996 * c * ( lat - alfa * j2 + beta * j4 - gama * j6 )
        val xx = epsilon * v * ( 1 + ( ta / 3 ) ) + 500000D
        val yy = {
            val yt = nu * v * ( 1 + ta ) + Bm
            if (yt < 0) 9999999D + yt else yt
        }

        (xx, yy, "%02.0f %c".format(Huso, Letra))

    }

}
