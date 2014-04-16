package scilube

import org.junit.Assert._
import org.junit.{Ignore, Test}

/**
 *
 * @author Brian Schlining
 * @since 2012-06-13
 */

class MatlibTest {

  private[this] val tolerance = 0.00000000001

  // wdata = [0:40 10:30 15:25 18:22 25 27 27 28 29 29 30 30 31 32 33 34 40 45 50];
  private[this] val wdata = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,
    17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,
    40,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,15,
    16,17,18,19,20,21,22,23,24,25,18,19,20,21,22,25,27,27,28,29,29,30,30,
    31,32,33,34,40,45,50)

  // mdata = [0:50 10: 50 10:50 20:40 20:40 20:40 25:35 25:35 35 35 35 20:70 60 63 70 71 80];
  private[this] val mdata = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11,12,13,14,15,16,17,18,
    19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,
    49,50,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,
    38,39,40,41,42,43,44,45,46,47,48,49,50,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,
    27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,20,21,22,23,24,25,
    26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,
    35,36,37,38,39,40,20,21,22,23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,25,26,27,
    28,29,30,31,32,33,34,35,25,26,27,28,29,30,31,32,33,34,35,35,35,35,20,21,22,23,24,25,26,27,
    28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,
    58,59,60,61,62,63,64,65,66,67,68,69,70,60,63,70,71,80)

  @Test
  def testSubset() {
    val src = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val idx = Seq(0, 2, 4, 6)
    val actual = Matlib.subset(src, idx)
    assertEquals("The results are not the correct size", 4, actual.size)
    for (i <- 0 until actual.size) {
      assertEquals("Subset did not return the correct value", idx(i), actual(i), tolerance)
    }
  }

  @Test
  def testTrapz() {
    val x = Array[Double](1, 2, 4, 8, 16)
    val y = Array[Double](2, 3, 2, 4, 8)
    val a = Matlib.trapz(x, y)
    val e = 67.5
    assertEquals(a, e, tolerance)
  }

  @Test
  def testTocdf() {
    def expected = Array[Double](0,
      0.010752688172043,
      0.021505376344086,
      0.032258064516129,
      0.043010752688172,
      0.053763440860215,
      0.064516129032258,
      0.075268817204301,
      0.086021505376344,
      0.096774193548387,
      0.107526881720430,
      0.129032258064516,
      0.150537634408602,
      0.172043010752688,
      0.193548387096774,
      0.215053763440860,
      0.247311827956989,
      0.279569892473118,
      0.311827956989247,
      0.354838709677419,
      0.397849462365591,
      0.440860215053763,
      0.483870967741935,
      0.526881720430108,
      0.559139784946237,
      0.591397849462366,
      0.634408602150538,
      0.655913978494624,
      0.698924731182796,
      0.731182795698925,
      0.774193548387097,
      0.817204301075269,
      0.838709677419355,
      0.860215053763441,
      0.881720430107527,
      0.903225806451613,
      0.913978494623656,
      0.924731182795699,
      0.935483870967742,
      0.946236559139785,
      0.956989247311828,
      0.978494623655914,
      0.989247311827957,
      1.0)

    val (actual, _) = Matlib.tocdf(wdata)
    assertEquals(expected.size, actual.size)
    for (i <- 0 until actual.size) {
      assertEquals(expected(i), actual(i), tolerance)
    }
  }

  @Test
  def testCorr() {
    /*
        wdata = [0:40 10:30 15:25 18:22 25 27 27 28 29 29 30 30 31 32 33 34 40 45 50];
        mdata = [0:50 10: 50 10:50 20:40 20:40 20:40 25:35 25:35 35 35 35 20:70 60 63 70 71 80];
        bins = 0:10:80;
        hw = histc(wdata, bins) / length(wdata)
        hm = histc(mdata, bins) / length(mdata)
        actual = corr(hw, hm)
     */
    val expected = 0.717507254854288
    val bins = (0D to 80 by 10).toArray
    val hw = Matlib.histc(wdata, bins).map(_ / wdata.size)
    val hm = Matlib.histc(mdata, bins).map(_ / mdata.size)
    val actual = Matlib.corr(hw, hm)
    assertEquals(expected, actual, tolerance)

  }

  @Test
  def testFind() {
    val d = Array[Double](0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
    val actual1 = Matlib.find(d, (v: Double) => v < 1).toArray
    val expected1 = Array(0)
    assertArrayEquals(expected1, actual1)

    val actual2 = Matlib.find(d, (v: Double) => v % 2 == 0).toArray
    val expected2 = Array(0, 2, 4, 6, 8)
    assertArrayEquals(expected2, actual2)
  }
}
