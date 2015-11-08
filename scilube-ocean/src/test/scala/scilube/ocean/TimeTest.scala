package scilube.ocean

import java.util.Date
import org.junit.runner.RunWith
import org.mbari.util.GmtCalendar
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers
import org.threeten.bp._  
import scala.math._

@RunWith(classOf[JUnitRunner])
class TimeTest extends FunSpec with Matchers {
  
  private[this] val eps = 0.0001

  describe("The Time trait") {

    it("should calculate the solar position correctly") {
      
      val calendar = new GmtCalendar(1996, 6 - 1, 12)
      val sp = Ocean.solarPosition(calendar.getTime.getTime, -121.8, 36)
      sp.azimuth should be ((23.1916 * Pi / 180D) +- eps)
    }

    it("should calculate local noon correctly") {
      val calendar = new GmtCalendar(1996, 5 - 1, 18)
      val noon = Ocean.noon(calendar.getTime.getTime, 121.8)
      val expected = 20D + (3 + 22 / 60D) / 60D // 20:03:22 HH:MM:SS
      noon should be (expected +- (1D / 60))
    }

  }
  
  describe("A SolarPosition") {
  
    it ("should calcluate solar ephemeris correctly") {
      val date = ZonedDateTime.of(1996, 6, 12, 16, 6, 0, 0, ZoneOffset.UTC)
      val sp = Ocean.solarPosition(date.toEpochSecond, -121, 36)
      //sp.declination should be (toRadians(23.1916) +- eps)
      //toDegrees(sp.greenwichHourAngle) should be (61.5231 +- eps)
      sp.earthSunDistance should be (1.0156 +- eps)
    }
  
  }
  
}