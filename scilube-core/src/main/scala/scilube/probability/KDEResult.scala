package scilube.probability

/**
 * Bean class that holds the output of KDE
 * @param bandwidth The optimal bandwidth (Gaussian kernel assumed)
 * @param x The array over which the density estimate is computed
 * @param pdf The probability density values over x
 * @param cdf The cumulative density values over x
 *
 * @author Brian Schlining
 * @since 2012-06-12
 */
class KDEResult(
    val bandwidth: Double,
    val x: Array[Double],
    val pdf: Array[Double],
    val cdf: Array[Double]
)
