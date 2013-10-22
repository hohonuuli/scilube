package scilube.ocean

import scala.math._

/**
 *
 * @author Brian Schlining
 * @since 2011-11-29
 */

trait Chlorophyll {

  /**
   * CZVS Calculate Chl using CZCS algorythmn
   * @param lw443 Water leaving radiance at 443nm
   * @param lw550  Water leaving radiance at 550nm
   * @return chlorophyll (ug/L)
   */
  def czcs(lw443: Double, lw550: Double) = {
    pow(10, (0.053 + 1.71 * log10(lw550 / lw443)))
  }

  /**
   * OC1A Optical chlorophyll algorithmn
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc1a(rrs490: Double, rrs555: Double): Double = {
    val a = Array(0.3734, -24529)
    val R = log10(rrs490 / rrs555)
    pow(10, (a(0) + a(1) * R))
  }

  /**
   * OC1B Optical chlorophyll algorithmn
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc1b(rrs490: Double, rrs555: Double): Double = {
    val a = Array(0.3636, -2.3500, -0.0100)
    val R = log10(rrs490 / rrs555)
    pow(10, (a(0) + a(1) * R)) + a(2)
  }

  /**
   * OC1C Optical chlorophyll algorithmn
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc1c(rrs490: Double, rrs555: Double) = {
    val a = Array(0.3920, -2.8550, 0.6580)
    val R = log10(rrs490 / rrs555);
    pow(10, (a(0) + a(1) * R + a(2) * R * R))
  }

  /**
   * OC1D Optical chlorophyll algorithmn
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc1d(rrs490: Double, rrs555: Double) = {
    val a = Array(0.3335, -2.9164, 2.4686, -2.5195)
    val R = log10(rrs490 / rrs555);
    pow(10, (a(0) + a(1) * R + a(2) * R * R + a(3) * R * R * R))
  }

  /**
   * OC2V2 Optical chlorophyll algorithmn
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc2v1(rrs490: Double, rrs555: Double) = {
    val a = Array(0.3410, -3.0010, 2.8110, -2.0410, -0.0400)
    val R = log10(rrs490 / rrs555)
    pow(10, (a(0) + a(1) * R + a(2) * R * R + a(3) * R * R * R)) + a(4)
  }

  /**
   * OC2V2 Modified ocean chlorphyll 2 algorithm
   *
   * OC2V2 calculates ocean chlorphyll from in water upwelled radiance (Lu).
   * The algorithm is from Stephane Maritorena <stephane@calval.gfsc.nasa.gov>
   *
   * It's modified in that the original algotithm uses:
   * R = log(Rrs_490/Rrs_555) where Rrs is the remote reflectance ratio
   *
   *  Be aware:
   *        R = log(Lu_490/Lu_555) as an approximation gives values that are closer to
   *        morel's algorythmn (at least for equatorial data).
   *
   *  Note:
   *        the units of LU490 and LU_555 are not important.
   *        however they must be the same for both inputs
   *
   * @param lu490 Upwelled radiance at 490 nm
   * @param lu555 Upwelled radiance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc2v2(lu490: Double, lu555: Double) = {
    val a = Array(0.2974, -2.2429, 0.8358, -0.0077, -0.0929)
    val R = log10(lu490 / lu555);
    pow(10, (a(0) + a(1) * R + a(2) * R * R + a(3) * R * R * R)) + a(4)
  }

  /**
   * OC2V4 2-band ocean chlorphyll algorithm (version 4)
   *
   * OC2V4 calculates ocean chlorphyll from in water upwelled radiance (Lu).
   * The algorithm is from Jay O'Reilly <oreilly@fish1.gso.uri.edu>
   *
   * Note: the units of Rrs490 and Rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs490 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc2v4(rrs490: Double, rrs555: Double) = {
    val a = Array(0.319, -2.336, 0.879, -0.135, -0.071)
    val R = log10(rrs490 / rrs555)
    pow(10.0, (a(0) + a(1) * R + a(2) * R * R + a(3) * R * R * R)) + a(4)
  }

  /**
   * OC4V4 4-band ocean chlorphyll algorithm (version 4)
   *
   * OC4V4 calculates ocean chlorphyll from in water upwelled radiance (Lu).
   * The algorithm is from Jay O'Reilly <oreilly@fish1.gso.uri.edu>
   *
   * Note: the units of rrs443, rrs490, rrs510 and rrs555 are not important.
   *       however they must be the same for both inputs. If Rrs is
   *       unavailable use Lw or Lwn.
   *
   * @param rrs443 Remote sensing reflectance at 490 nm
   * @param rrs490 Remote sensing reflectance at 555 nm
   * @param rrs510 Remote sensing reflectance at 490 nm
   * @param rrs555 Remote sensing reflectance at 555 nm
   * @return chlorophyll (ug/L)
   */
  def oc4v4(rrs443: Double, rrs490: Double, rrs510: Double, rrs555: Double) = {
    val a = Array(0.366, -3.067, 1.930, 0.649, -1.532)
    val R = List(log10(rrs443 / rrs555), log10(rrs490 / rrs555), log10(rrs510 / rrs555)).max
    pow(10.0, (a(0) + a(1) * R + a(2) * R * R + a(3) * R * R * R + a(4) * R * R * R * R))
  }

}