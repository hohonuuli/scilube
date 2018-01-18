package scilube.geometry

/**
 * Calculate the hausdorff distances between 2 sets of points. Returns the ''nearest'' values
 * to each point in both sets.
 *
 * @param distanceFn The function used to calculate the distance between 2 points. Typically,
 *      we'll be using the [[org.mbari.esp.ia.geometry.EuclideanDistanceFn]].
 * @tparam T The type that the ''distanceFn'' returns. Typically for our analysis we'll use
 *      [[scala.Double]]
 *
 * @author Brian Schlining
 * @since 2012-03-21
 */
class HausdorffDistances[T: Ordering](distanceFn: (LabeledDoublePoint2D, LabeledDoublePoint2D) => T)
    extends ((Iterable[LabeledDoublePoint2D], Iterable[LabeledDoublePoint2D]) => (Seq[PointPair[T]],
                                                                                  Seq[PointPair[
                                                                                      T]])) {

  private[this] val calculateDistances = new SetsToPointPairsFn(distanceFn)

  /**
   *
   * @param aa The Set A
   * @param bb The Set B
   * @return Tuple of (nearestBtoA, nearestAtoB)
   */
  def apply(aa: Iterable[LabeledDoublePoint2D],
            bb: Iterable[LabeledDoublePoint2D]): (Seq[PointPair[T]], Seq[PointPair[T]]) = {

    // --- Calculate all distances
    val distances = calculateDistances(aa, bb)

    // --- Get directed Hausdorff distance of A -> B
    val bLabels = bb.map(_.label)
    val nearestAToB = bLabels
      .map { bl =>
        distances.filter(_.p1.label == bl).minBy(_.value)
      }
      .toSeq
      .sortBy(_.value)

    // --- Get directed Hausdorf distance B -> A
    val aLabels = aa.map(_.label)
    val nearestBToA = aLabels
      .map { al =>
        distances.filter(_.p0.label == al).minBy(_.value)
      }
      .toSeq
      .sortBy(_.value)

    (nearestBToA, nearestAToB)

  }
}
