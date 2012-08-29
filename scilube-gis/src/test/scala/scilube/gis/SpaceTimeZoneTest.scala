package scilube.gis

import java.util.Date
import org.junit.Test
import org.junit.Assert._
import scilube.time.MomentInterval
import scilube.geometry.{Point4D, SpatialEnvelope}


/**
 *
 * @author Brian Schlining
 * @since 2012-07-12
 */

class SpaceTimeZoneTest {

    private[this] val envelope = SpaceTimeZone(new SpatialEnvelope(0D, 0D, 10D, 10D))
    private[this] val momentInterval = SpaceTimeZone(MomentInterval(new Date(0), new Date(1000000)))

    @Test
    def test01() {
        val inside = Point4D(1D, 2D, 3D, new Date(1000))
        assertTrue(envelope.contains(inside))
        assertTrue(momentInterval.contains(inside))

        val outside = Point4D(-1D, 12D, 100D, new Date(2000000))
        assertFalse(envelope.contains(outside))
        assertFalse(momentInterval.contains(outside))

    }

    @Test
    def test02() {
        val inside = Point4D(2D, 2D, 2D, new Date(1000))
        val outsideSpace = Point4D(100D, 2D, 2D, new Date(1000))
        val outsideTime = Point4D(2D, 2D, 2D, new Date(2000000))
        val zone = envelope and momentInterval

        assertTrue(zone.contains(inside))
        assertFalse(zone.contains(outsideSpace))
        assertFalse(zone.contains(outsideTime))

    }

}
