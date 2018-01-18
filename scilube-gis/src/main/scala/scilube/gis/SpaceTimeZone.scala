package scilube.gis

import java.util.Date
import scilube.geometry.{Envelope, Point4D}
import scilube.time.{DateInterval, MomentInterval}

/**
 * A representation of some spatial area and temporal period.
 *
 * @tparam B is the temporal type. e.g. java.util.Date or java.time.Instant
 *
 * @author Brian Schlining
 * @since 2011-05-12
 */
trait SpatialTemporalZone[B] {

  /**
   * Checks that the zone contains the given 4D point
   *
   * @param point The point of interest
   * @tparam A The points coordinate types
   * @return true if the point falls with in the zone. false if it is outside the zone
   */
  def contains[A: Numeric, C <: B](point: Point4D[A, C]): Boolean

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
   * @return
   */
  def and[C <: B](zone: SpatialTemporalZone[B]): SpatialTemporalZone[B] =
    new SpatialTemporalZone[B] {

      def contains[A: Numeric, C <: B](point: Point4D[A, C]): Boolean =
        SpatialTemporalZone.this.contains(point) && zone.contains(point)
    }

}

/**
 * Wrapper that converts an [[scilube.geometry.Envelope]] to a
 * [[scilube.gis.SpaceTimeZone]]
 */
class EnvelopeSpaceTimeZone(val envelope: Envelope) extends SpatialTemporalZone[Date] {
  def contains[A: Numeric, B <: Date](point: Point4D[A, B]): Boolean =
    envelope.contains(point)
}

/**
 * Wrapper that converts a [[scilube.time.MomentInterval]] to a SpaceTimeZone. Thiz zone contains
 * a point if it's time falls within
 *
 * @param momentInterval The moment
 */
class MomentIntervalSpaceTimeZone(val momentInterval: DateInterval)
    extends SpatialTemporalZone[Date] {

  /**
   * Checks that the zone contains the given 4D point
   *
   * @param point The point of interest
   * @tparam A The points coordinate types
   * @return true if the point falls with in the zone. false if it is outside the zone
   */
  def contains[A: Numeric, B <: Date](point: Point4D[A, B]): Boolean =
    momentInterval.contains(point.w)

}

/**
 * Does not apply any filtering
 */
object NoopSpaceTimeZone extends SpaceTimeZone {
  def contains[A: Numeric, B](point: Point4D[A, B]): Boolean = true
}

/**
 * Contains conversions
 */
object SpaceTimeZone {

  def apply(envelope: Envelope): EnvelopeSpaceTimeZone =
    new EnvelopeSpaceTimeZone(envelope)

  def apply(momentInterval: DateInterval): MomentIntervalSpaceTimeZone =
    new MomentIntervalSpaceTimeZone(momentInterval)

  def apply(envelope: Envelope, momentInterval: DateInterval): SpaceTimeZone =
    apply(envelope) and apply(momentInterval)

  def apply(envelope: Envelope, start: Date, end: Date): SpaceTimeZone =
    apply(envelope) and apply(MomentInterval(start, end))

}
