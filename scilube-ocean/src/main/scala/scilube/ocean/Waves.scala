package scilube.ocean

import scala.math._
import org.mbari.ocean.{ Waves => JWaves }

/**
 *
 * @author Hohonuuli
 * @since 2011-11-29
 */

trait Waves {

  /**
   * Ideal wave phase speed = f(Period, Depth)
   * @param t = ideal wave period (seconds)
   * @param z = water depth (meters)
   * @return (c, Ld, L) where: C = wave phase speed (m/s), Ld = deepwater wavelength (M),
   *         and L = wavelength in water depth, Z
   */
  def celerity(t: Double, z: Double): (Double, Double, Double) = {
    val v = JWaves.celerity(t, z)
    (v.getA, v.getB, v.getC)
  }

  /**
   * Ideal wave phase speed = f(Period), Deep water approximation
   *
   * @param t = ideal wave period (seconds)
   * @return (c, Ld, L) where: C = wave phase speed (m/s), Ld = deepwater wavelength (M),
   *         and L = wavelength in deep water
   */
  def celerity(t: Double): (Double, Double, Double) = {
    val v = JWaves.celerity(t)
    (v.getA, v.getB, v.getC)
  }
}
