package scilube.ocean

import scala.math._

trait Atmosphere {

    /** standard atmospheric pressure (mbar)  */
    val standardAtmosphericPressure = 1013.25

    def airmass(zen: Double): Double = {
        val zenr = zen * Pi / 180
        if (zen < 75) {
            val X = 1 / cos(zenr) - 1;
            1 + X - 1.867e-3 * X - 2.875e-3 * pow(X, 2) - 8.083e-4 * pow(X, 3);
        }
        else {
            var X = 0D; // the following iteration is Kasten's formula
            for (i <- 1 to 4) {
                // careful... X is degrees here
                val Y = zenr - X;
                X = pow(10, (-116.94 + 4.41925 * Y - 0.056623 * pow(Y, 2) + 0.00024364 * pow(Y, 3)))
            }
            X = zenr - X; // careful X is degrees here too
            1 / (cos(Pi * X / 180.0) + 0.15 * pow((180 - X + 3.855), (-1.235)));
        }
    }

    /**
     * Atmospheric thickness for all constituents; Kasten (1966)
     *
     * Atmospheric thickness or path length calculated using Kastens (1966)
     * empirical formula.  The airmass_ is valid for all angles (by correcting
     * for the sphericity of the earth) and can be corrected for nonstandard
     * atmospheric pressure.
     *
     * @param zen solar zenith angle (degrees)
     * @param p = nonstandard atmospheric pressure (mbar). if not input P = 1013.25
     *            mbar standard atmospheric pressure
     * @return Atmospheric thickness
     *
     */
    def airmass1(zen: Double, p: Double = standardAtmosphericPressure): Double = {
        val p0 = standardAtmosphericPressure
        val zenr = (Pi / 180) * zen;
        val am = 1 / (cos(zenr) + 0.15 * pow((93.885 - zenr), -1.253))  // G&C(90) eq 13
        am * (p / 1013.25);
    }

    /**
     * Atmospheric thickness for ozone (Platridge & Platt,1976)
     *
     * Ozone requires a slightly longer path length or atmospheric thickness
     * than for the major atmospheric constituents. Paltridge and Platt's (1976)
     * formula is used to calculate ozone optical thickness.  The airmass is
     * valid for all angles.
     *
     * @param zen solar zenith angle (degrees)
     * @param ozone atmospheric thickness
     */
    def airmass2(zen: Double): Double = {
        val zenr = (Pi / 180) * zen
        1.0035 / pow(pow(cos(zenr), 2) + 0.007, 0.5);
    }

    //def almanac(date: Date)

}