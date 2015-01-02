package scilube.geometry

import scala.collection.mutable

/**
 * Apply a function between all possible pairings of points in a collection
 * @author Brian Schlining
 * @since 2012-03-07
 */
class ToPointPairsFn[A: Ordering]
    extends (((LabeledDoublePoint2D, LabeledDoublePoint2D) => A, Iterable[LabeledDoublePoint2D]) => Seq[PointPair[A]]) {

  /**
   * Calculates some value between all possible   combinations of points
   * @param valueCalculation A function that takes 2 points and returns a value
   * @param points The points to apply the funciton to
   * @return A collection of pointpairs with the value from the ''valueCalculation'' function
   */
  def apply(valueCalculation: (LabeledDoublePoint2D, LabeledDoublePoint2D) => A,
    points: Iterable[LabeledDoublePoint2D]): Seq[PointPair[A]] = {
    val set = new mutable.HashSet[PointPair[A]]
    val pointMap = points.map(p => p.label -> p).toMap

    for (key0 <- pointMap.keySet; key1 <- pointMap.keySet; if key0 < key1) {
      val p0 = pointMap(key0)
      val p1 = pointMap(key1)
      val value = valueCalculation(p0, p1)
      set.add(PointPair(p0, p1, value))
    }
    set.toSeq.sortBy(_.value)
  }
}
