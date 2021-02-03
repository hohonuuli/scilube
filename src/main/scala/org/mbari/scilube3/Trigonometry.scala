package org.mbari.scilube3

import mbarix4j.math.DoubleMath
import scala.math._

/** @author Brian Schlining
  * @since 2012-06-07
  */
protected trait Trigonometry {

  val Tau = 2 * Pi

  val HalfPi = Pi / 2

  /** Adjusts an angle in radians to fall between 0 and 2 * PI
    *
    * @param radians
    * @return The same angle but adjusted so that if falls between 0 and 2 PI
    */
  def normalizeAngle(radians: Double) = DoubleMath.normalizeRadianAngle(radians)

  /** Cosecant
    * @param radians
    * @return
    */
  def csc(radians: Double) = 1 / sin(radians)

  /** Secant
    * @param radians
    * @return
    */
  def sec(radians: Double) = 1 / cos(radians)

  /** Cotangent
    * @param radians
    * @return
    */
  def cot(radians: Double) = 1 / tan(radians)

}
