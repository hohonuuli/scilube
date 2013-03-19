package scilube.ocean

import java.util.Date
import java.util.GregorianCalendar
import org.mbari.solar.{SolarPosition => JSolarPosition}
import scilube.Matlib

trait Time {

  /**
   * @param date Time of observation
   * @param latitude Location of observation in decimal degrees (+N/-S)
   * @param longitude Locaiton of observation in decimal degrees (-W/+E)
   */
  def solarPosition(date: Date, latitude: Double, longitude: Double): SolarPosition = {
    val sp = new JSolarPosition(date.getTime, latitude, longitude)
    SolarPosition(sp)
  }

  /**
   * @param date The date of interest
   * @param longitude The longitude in decimal degrees (-W/+E)
   * @return GMT time of local apparent noon in decimal hours
   */
  def noon(date: Date, longitude: Double): Double = {
    val sp = solarPosition(date, 0, longitude)
    val gha = sp.greenwichHourAngle
    val hour1 = 12 + -longitude/15 // approximate time of local area noon 
    if (gha > 180) {
      hour1 - (gha - Matlib.TAU) / 15 + -longitude / 15
    }
    else {
      hour1 - gha / 15 + -longitude / 15
    }
  }
   
}

/**
 *
 * @param date The observation date of the solar position
 * @param altitude Solar altitude angle in radians
 * @param azimuth The Sun azimuth in radians
 * @param declination
 * @param distance The Earth-Sun distance in Astronimical Units (A.U)
 * @param greenwichHourAngle
 * @param latitude Observers latitude in radians
 * @param longitude Observers longitude in radians
 * @param zenith Solar zenith angle in radians
 */
case class SolarPosition protected (date: Date,
    altitude: Double, 
    azimuth: Double, 
    declination: Double,
    distance: Double,
    greenwichHourAngle: Double,
    latitude: Double,
    longitude: Double, 
    zenith: Double)


object SolarPosition {
  /** 
   * Factory method
   *
   * @param sp The Java SolarPosition object to convert to a Scala SolarPosition
   */
  def apply(sp: JSolarPosition): SolarPosition = {
    SolarPosition(new Date(sp.getTime), sp.getAltitude, sp.getAzimuth, sp.getDeclination, 
        sp.getDistance, sp.getGreenwichHourAngle, sp.getLatitude, 
        sp.getLongitude, sp.getZenith)
  }
}