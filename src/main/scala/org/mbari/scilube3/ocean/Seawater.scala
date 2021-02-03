package org.mbari.scilube3.ocean

import mbarix4j.ocean.{Seawater => JSeawater}

/** @author Brian Schlining
  * @since 2012-12-11
  */
trait Seawater {

  /** Adiabatic temperature gradient (salinity,temperature,pressure)
    * <p/>
    * From: UNESCO Tech Paper Mar Sci 44 (1983)
    *
    * @param salinity    (psu)
    * @param temperature (celsius)
    * @param pressure    (decibar)
    * @return Adiabatic temperature gradient (C/dbar)
    */
  def atg(salinity: Double, temperature: Double, pressure: Double) =
    JSeawater.atg(salinity, temperature, pressure)

  /** Seawater bulk modulus
    *
    * @param salinity    (psu)
    * @param temperature (celsius)
    * @param pressure    (dbar)
    * @return Seawater bulk modulus in BARS!! (not dbar)
    */
  def bulkmod(salinity: Double, temperature: Double, pressure: Double) =
    JSeawater.bulkmod(salinity, temperature, pressure)

  /** Specific volume anomaly = f(S,T,P)
    * International Equation of State of Seawater (1980)
    *
    * @param salinity    psu
    * @param temperature Celsius
    * @param pressure    decibar (Supply P = 0 to obtain delta-t), default is 0
    * @return Specific Volume Anomaly (centiliters/ton or 1E-8 m^3/kg)
    */
  def delta(salinity: Double, temperature: Double, pressure: Double = 0) =
    JSeawater.delta(salinity, temperature, pressure)

  /** DENSITY - Seawater density (S,T,P)
    * <p/>
    * International Equation of State of Seawater (1980)
    * UNESCO Tech Paper Mar Sci 44 (1983)
    * <p/>
    * Example:  density(34.567,5.00,2000) -> 1.036409
    * density(35,25,10000)      -> 1.06253817  UNESCO 44 p19
    *
    * @param salinity = Salinity (psu)
    * @param temperature = Temperature (C)
    * @param pressure = Pressure (dbar), default is 0
    * @return density (kg/liter)
    */
  def density(salinity: Double, temperature: Double, pressure: Double = 0) =
    JSeawater.density(salinity, temperature, pressure)

  /** Calculate ocean depth from measured pressure and latitude.<br>
    * <p/>
    * Example: depth_(5000,36) -> 4906.08<br>
    * depth_(1000,90) -> 9674.23  UNESCO 44 p28<br><br>
    * <p/>
    * Note:    For more accurate results an additional factor of the
    * ratio of the actual geopotential anomaly/gravity must
    * be added. This correction will be less than 2 m.<br>
    *
    * @param pressure dbar
    * @param latitude decimal degrees
    * @return
    */
  def depth(pressure: Double, latitude: Double) =
    JSeawater.depth(pressure, latitude)

  /** Freezing point of seawater. Ref: UNESCO Tech Paper Mar Sci 44 (1983)
    *
    * TODO: Create unit test
    * freeze(33, 0)  -> -1.808
    * freeze(35,500) -> -2.299  UNESCO 44 p30
    *
    * @param salinity Salinity (psu)
    * @param pressure Pressure (dbar)
    * @return Freezing point (Celsius)
    */
  def freeze(salinity: Double, pressure: Double) =
    JSeawater.freeze(salinity, pressure)

  /** Conversion of conductivity ratio to practical salinity (R, T, P)
    * UNESCO Tech Paper Mar Sci 44 (1983)
    *
    * @param conductivity Conductivity ratio
    * @param temperature  C
    * @param pressure     (dbar)
    * @return Practical salinity (psu or ~g/kg)
    */
  def salinity(conductivity: Double, temperature: Double, pressure: Double = 0) =
    JSeawater.salinity(conductivity, temperature, pressure)

  /** 'Spiciness', an oceanographic variable for characterization
    * of intrusions and water masses.
    * <p/>
    * Spiceness is orthogonal to isopycnals of potential density
    * <p/>
    * Algorithmn from P. Flament, Subduction and finestructure associated
    * with upwelling filaments. Ph. D. Dissertation. University of
    * California, San Diego. Vol 32. No.10. pp.1195 to 1207. 1985
    *
    * @param salinity psu
    * @param theta    Potential temperature Celsius (see theta)
    * @param pressure decibar
    * @return
    */
  def spiciness(salinity: Double, theta: Double, pressure: Double) =
    JSeawater.spiciness(salinity, theta, pressure)

  /** Sound velocity in seawater = f(S, T, P)
    * UNESCO Tech Paper Mar Sci 44 (1983)
    *
    * @param salinity    psu
    * @param temperature Celsius
    * @param pressure    dbar
    * @return Speed of sound in seawater (m/s)
    */
  def svel(salinity: Double, temperature: Double, pressure: Double) =
    JSeawater.svel(salinity, temperature, pressure)

  /** Local potential temperature at the reference pressure = f(S, T, P)
    * UNESCO Tech Paper Mar Sci 44 (1983)
    *
    * @param salinity    psu
    * @param temperature Celsius
    * @param p0          (decibar)
    * @param pr          Reference pressure (decibar), default = 0
    * @return Potential Temperature at the reference pressure (Celsius)
    */
  def theta(salinity: Double, temperature: Double, p0: Double, pr: Double = 0) =
    JSeawater.theta(salinity, temperature, p0, pr)

}
