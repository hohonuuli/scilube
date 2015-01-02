package scilube.gis.simple

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.Matchers
import scilube.grid.GridSearcher

/**
 *
 * @author Brian Schlining
 * @since 2013-05-21
 */
@RunWith(classOf[JUnitRunner])
class GridSearcherTest extends FunSpec with Matchers {

  val url = getClass.getResource("/mockup.asc")
  val tolerance = 0.00000001

  describe("A Grid Searcher wrapped around an ASCII grid") {
    it ("should work!") {
      val grid = ASCGridReader.read(url)
      val gridSearcher = new GridSearcher(grid)

      val p0 = gridSearcher.search(401111.0, 3001111.0).get
      p0.x should be (2)
      p0.y should be (9)
      grid(p0.x, p0.y) should be (22.0)

      val p1 = gridSearcher.search(400400, 3004567).get
      p1.x should be (1)
      p1.y should be (2)
      grid(p1.x, p1.y) should be (91.0)

    }
  }

}
