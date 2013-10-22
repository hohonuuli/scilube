package scilube.geometry

import scala.math._

/**
 * Calculates the bidirectional Hausdorff distance.
 *
 * Reference: D.P Huttenlocher, G.A. Klandermand, and W.J. Rucklidge., Comparing Images Using the
 *  Hausdorff Distance. IEE Transactions on Pattern Analysis and Machine Intelligence. Vol. 15,
 *  No. 9, September 1993
 *
 * @author Brian Schlining
 * @since 2012-03-19
 */

class HausdorffDistanceFn[T: Ordering](distanceFn: (LabeledDoublePoint2D, LabeledDoublePoint2D) => T,
  partialFractionA: Double,
  partialFractionB: Double)
    extends ((Iterable[LabeledDoublePoint2D], Iterable[LabeledDoublePoint2D]) => PointPair[T]) {

  require(partialFractionA > 0 && partialFractionA <= 1)
  require(partialFractionB > 0 && partialFractionB <= 1)

  private val toHausdorffDistances = new HausdorffDistances(distanceFn)

  def apply(aa: Iterable[LabeledDoublePoint2D], bb: Iterable[LabeledDoublePoint2D]): PointPair[T] = {

    // --- Calculate all distances
    val (nearestBToA, nearestAToB) = toHausdorffDistances(aa, bb)

    val aIdx = round((nearestBToA.size - 1) * partialFractionA).toInt
    val bIdx = round((nearestAToB.size - 1) * partialFractionB).toInt

    val ha = nearestBToA(aIdx)
    val hb = nearestAToB(bIdx)

    val ordering = implicitly[Ordering[T]]
    if (ordering.compare(ha.value, hb.value) >= 1) ha else hb

  }
}
