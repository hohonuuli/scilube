package scilube.geometry

/**
 * Calculate the distances between all permutations of points in Set A and Set B
 *
 * @param valueCalculation The function used to calculate a value relation between 2 points
 * @tparam T the type that the distance function returns. For most of our analysis this will just
 *      be [[scala.Double]]
 *
 * @author Brian Schlining
 * @since 2012-03-21
 */

class SetsToPointPairsFn[T : Ordering](valueCalculation: (LabeledDoublePoint2D, LabeledDoublePoint2D) => T)
        extends ((Iterable[LabeledDoublePoint2D], Iterable[LabeledDoublePoint2D]) => Seq[PointPair[T]]) {

    def apply(setA: Iterable[LabeledDoublePoint2D], setB: Iterable[LabeledDoublePoint2D]): Seq[PointPair[T]] = {

        (setA.map { a =>
            setB.map { b =>
                PointPair(a, b, valueCalculation(a, b))
            }
        }).flatten.toSeq.sortBy(_.value)
    }

}
