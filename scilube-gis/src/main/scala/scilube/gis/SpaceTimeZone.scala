package scilube.gis

import java.util.Date
import scilube.geometry.{ Envelope, Point4D }
import scilube.time.MomentInterval

/**
 * A representation of some spatial area and temporal period.
 *
 * @author Brian Schlining
 * @since 2011-05-12
 */
trait SpaceTimeZone {

  /**
   * Checks that the zone contains the given 4D point
   *
   * @param point The point of interest
   * @param numeric implicit conversion of the points coordiates to a numeric value
   * @tparam A The points coordinate types
   * @return true if the point falls with in the zone. false if it is outside the zone
   */
  def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean

  /**
   * Compose zones.
   * {{{
   *     val a = // some SpaceTimeZone
   *     val b = // some other SpaceTimeZone
   *     val c = a and b
   *
   *     // true if both a and b contains the point
   *     val contained = c.contains(point4D)
   *
   * }}}
   * @param zone
   * @tparam A
   * @return
   */
  def and[A](zone: SpaceTimeZone): SpaceTimeZone = new SpaceTimeZone {

    def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean =
      SpaceTimeZone.this.contains(point) && zone.contains(point)

  }

}

/**
 * Wrapper that converts an [[scilube.geometry.Envelope]] to a
 * [[scilube.gis.SpaceTimeZone]]
 */
class EnvelopeSpaceTimeZone(val envelope: Envelope) extends SpaceTimeZone {
  def contains[B](point: Point4D[B, Date])(implicit numeric: Numeric[B]): Boolean = envelope.contains(point)
}

/**
 * Wrapper that converts a [[scilube.time.MomentInterval]] to a SpaceTimeZone. Thiz zone contains
 * a point if it's time falls within
 *
 * @param momentInterval The moment
 */
class MomentIntervalSpaceTimeZone(val momentInterval: MomentInterval) extends SpaceTimeZone {
  /**
   * Checks that the zone contains the given 4D point
   *
   * @param point The point of interest
   * @param numeric implicit conversion of the points coordiates to a numeric value
   * @tparam A The points coordinate types
   * @return true if the point falls with in the zone. false if it is outside the zone
   */
  def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean = momentInterval.contains(point.w)
}

/**
 * Does not apply any filtering
 */
object NoopSpaceTimeZone extends SpaceTimeZone {
  def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean = true
}

/**
 * Contains conversions
 */
object SpaceTimeZone {

  def apply(envelope: Envelope): EnvelopeSpaceTimeZone = new EnvelopeSpaceTimeZone(envelope)

  def apply(momentInterval: MomentInterval): MomentIntervalSpaceTimeZone = new MomentIntervalSpaceTimeZone(momentInterval)

  def apply(envelope: Envelope, momentInterval: MomentInterval): SpaceTimeZone = apply(envelope) and apply(momentInterval)

  def apply(envelope: Envelope, start: Date, end: Date): SpaceTimeZone = apply(envelope) and apply(MomentInterval(start, end))

}
