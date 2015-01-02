package scilube.ocean

import org.junit.runner.RunWith
import org.mbari.util.GmtCalendar
import org.scalatest.FunSpec
import org.scalatest.junit.JUnitRunner
import org.scalatest.Matchers

@RunWith(classOf[JUnitRunner])
class TimeTest extends FunSpec with Matchers {

  describe("The Time trait") {

    it("should calculate the solar position correctly") {
      val calendar = new GmtCalendar(1996, 6 - 1, 12)
      //val sp = Ocean.solarPosition(calendar.getTime, )
    }

    ignore("should calculate local noon correctly") {
      val calendar = new GmtCalendar(1996, 5 - 1, 18)
      val noon = Ocean.noon(calendar.getTime, 121.8)
      val expected = 20D + (3 + 22 / 60D) / 60D // 20:03:22 HH:MM:SS
      noon should be (expected +- (1D / 60))
    }

  }
  
}