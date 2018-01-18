package scilube.geometry

/**
 * Calculate directions between all permutations of a set of points.
 *
 * @author Brian Schlining
 * @since 2012-03-07
 */
object PointDirectionsFn extends (Iterable[LabeledDoublePoint2D] => Seq[PointPair[Double]]) {

  /**
   * Calculates the directions between all combinations of points.
   * @param points A List of points
   * @return A collection of PointPairs containing the 2 points and the direction from
   *     aa to bb
   */
  def apply(points: Iterable[LabeledDoublePoint2D]): Seq[PointPair[Double]] = {
    val directionFn = (p0: LabeledDoublePoint2D, p1: LabeledDoublePoint2D) => p0.direction(p1)
    new ToPointPairsFn[Double].apply(directionFn, points)
  }

}
