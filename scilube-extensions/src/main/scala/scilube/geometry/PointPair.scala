package scilube.geometry

/**
 * Represents a pair of points as well as some value relating those 2 points
 * @author Brian Schlining
 * @since 2012-03-07
 */
case class PointPair[A](p0: LabeledDoublePoint2D, p1: LabeledDoublePoint2D, value: A)
