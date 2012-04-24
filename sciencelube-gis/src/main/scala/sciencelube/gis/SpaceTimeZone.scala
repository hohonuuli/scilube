package sciencelube.gis

import java.util.Date
import sciencelube.geometry.{Envelope, Point4D}
import sciencelube.time.MomentInterval

/**
 * A representation of some spatial area and temporal period.
 *
 * @author Brian Schlining
 * @since 2011-05-12
 */
trait SpaceTimeZone {
    def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean
}

/**
 * Wrapper that converts an [[sciencelube.geometry.Envelope]] to a
 * [[sciencelube.gis.SpaceTimeZone]]
 */
class EnvelopeToSpaceTimeZone(envelope: Envelope) extends SpaceTimeZone {
    def contains[B](point: Point4D[B, Date])(implicit numeric: Numeric[B]): Boolean = envelope.contains(point)
}

/**
 * Wrapper that converts an [[sciencelube.geometry.Envelope]] and
 * [[sciencelube.time.MomentInterval]] to a SpaceTimeZone
 * @param envelope The rectangular spatial envelope
 * @param momentInterval The Moment interval defining the time bounds of the Zone
 */
class RectangularSpaceTimeZone(val envelope: Envelope, val momentInterval: MomentInterval)
        extends SpaceTimeZone {
    def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean = {
        envelope.contains(point) && momentInterval.contains(point.t)
    }
}

/**
 * Does not apply any filtering
 */
object NoopSpaceTimeZone extends SpaceTimeZone {
    def contains[A](point: Point4D[A, Date])(implicit numeric: Numeric[A]): Boolean = true
}

/**
 * Contains implicit conversions
 */
object SpaceTimeZone {

    def apply(envelope: Envelope) = new EnvelopeToSpaceTimeZone(envelope)

    def apply(envelope: Envelope, momentInterval: MomentInterval) =
        new RectangularSpaceTimeZone(envelope, momentInterval)

    def apply(envelope: Envelope, start: Date, end: Date) =
        new RectangularSpaceTimeZone(envelope, MomentInterval(start, end))
}
