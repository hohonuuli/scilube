package scilube.grid

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.Matchers

/**
 *
 * @author Brian Schlining
 * @since 2013-05-20
 */

@RunWith(classOf[JUnitRunner])
class GridSearcherTest extends FunSpec with Matchers {

  describe("A Grid Searcher") {
    it("should find indices of a pixel containing a point") {
      val grid = new DoubleArrayGrid((1 to 10), (21 to 30), 0)
      val gridSearcher = new GridSearcher(grid)
      val p = gridSearcher.search(5, 25).get
      p.x should equal (4)
      p.y should equal (4)
      val p2 = gridSearcher.search(1, 21).get
      p2.x === 0
      p2.y === 0
      val p3 = gridSearcher.search(0, 20)
      p3 === None
      val p4 = gridSearcher.search(11, 31)
      p4 === None
      val p5 = gridSearcher.search(10, 30).get
      p5.x === 9
      p5.y === 9
    }

    it ("should find indices of a pixel even if axes are reverse ordered") {
      val grid = new DoubleArrayGrid((10 to 1 by -1), (30 to 21 by -1), 0)
      val gridSearcher = new GridSearcher(grid)
      val p = gridSearcher.search(5, 25).get
      p.x === 5
      p.y === 5
      val p2 = gridSearcher.search(1, 21).get
      p2.x === 9
      p2.y === 9
      val p3 = gridSearcher.search(0, 20)
      p3 === None
      val p4 = gridSearcher.search(11, 31)
      p4 === None
      val p5 = gridSearcher.search(10, 30).get
      p5.x === 0
      p5.y === 0
    }

  }

}
