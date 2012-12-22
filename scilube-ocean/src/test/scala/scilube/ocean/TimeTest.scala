package scilube.ocean

import org.junit.runner.RunWith
import org.mbari.util.GmtCalendar
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class TimeTest extends FunSpec with ShouldMatchers {

  val time = new Time

  describe("The Time trait") {

    it("should calculate the solar position correctly") {
      val calendar = new GmtCalendar(1996, 6 - 1, 12, 16)
      val sp = time.solarPosition(calendar.getTime, )
    }

    it("should calculate local noon correctly") {
      val calendar = new GmtCalendar(1996 5 - 1, 18)
      val noon = time.noon(calendar.getTime, 121.8)
      val expected = 20D + (3 + 22 / 60D) / 60D // 20:03:22 HH:MM:SS
      noon should be (expected plusOrMinus (1D / 60))
    }

  }
  
}