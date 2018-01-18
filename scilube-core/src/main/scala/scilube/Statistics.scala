package scilube

import org.mbari.math.Statlib
import math._

/**
 * Statistics functions
 *
 * These are factored out into a trait just for modularity. Everyone should use these from the
 * [[org.mbari.math.Matlib]] object
 *
 * @author Brian Schlining
 * @since 2012-06-08
 */
protected trait Statistics {

  /**
   * Mean absolute deviation
   * @param data data
   * @return array of mean absolute deviation
   */
  def mad(data: Array[Double]): Array[Double] =
    Statlib.deviationFromMean(data).map(abs(_))

  /**
   * @param data data
   * @return mean value of `data`
   */
  def mean(data: Array[Double]): Double = Statlib.mean(data)

  /**
   * @param data data
   * @return median value of `data`
   */
  def median(data: Array[Double]): Double = Statlib.median(data)

  /**
   * Standard deviation is a statistical measure of spread or variability.The
   * standard deviation is the root mean square (RMS) deviation of the values
   * from their arithmetic mean.
   *
   * <b>std</b> normalizes values by N, where N is the sample size. This the
   * <i>Population Standard Deviation</i>
   * @param data data
   * @return standard deviation of data
   */
  def std(data: Array[Double]) = Statlib.populationStandardDeviation(data)

  /**
   * Population Variance (like Matlab's <i>var</i> function but var is a keyword in Scala so
   * I couldn't use it)
   *
   * @param data data
   * @return variance of data
   */
  def variance(data: Array[Double]) = Statlib.populationVariance(data)

}
