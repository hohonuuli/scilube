package scilube.geometry

/**
 * Calculate distances between all possible combinations of a colleciton of points
 * @author Brian Schlining
 * @since 2012-03-07
 */
object PointDistancesFn extends (Iterable[LabeledDoublePoint2D] => Seq[PointPair[Double]]) {

  /**
   * Calculates the distances between all combinations of points
   * @param points The collection of points
   * @return A collection of point pairs with the euclidean distance between the 2 points. All
   *     distances are positive.
   */
  def apply(points: Iterable[LabeledDoublePoint2D]): Seq[PointPair[Double]] = {
    val distanceFn = (p0: LabeledDoublePoint2D, p1: LabeledDoublePoint2D) => p0.distance(p1)
    new ToPointPairsFn[Double].apply(distanceFn, points)
  }
}
