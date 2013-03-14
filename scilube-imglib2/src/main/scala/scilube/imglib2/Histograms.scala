package scilube.imglib2

import scala.math._
import org.slf4j.LoggerFactory

/**
 *
 * @author Brian Schlining
 * @since 2012-03-07
 */

object Histograms {


    def normalizeHistogram(hist: Array[Int]): Array[Double] = {
        val sum = hist.sum
        require(sum > 0, "Empty histogram: sum of all bins is zero.")
        hist.map(_.toDouble / sum)
    }

    /**
     * Calculate the point of maximum entropy in a histogram
     * @param hist The histogram to process
     * @return the index into hist of the maximum entropy split
     */
    def maximumEntropy(hist: Array[Int]): Int = {
        // Normalize histogram, that is makes the sum of all bins equal to 1.
        val normalizedHist = normalizeHistogram(hist)

        // cumulative sum
        val pT = normalizedHist.scanLeft(0D)(_ + _).tail

        // Entropy for black and white parts of the histogram
        val hB = Array.ofDim[Double](hist.size)
        val hW = Array.ofDim[Double](hist.size)
        for (t <- 0 until hist.length) {

            // Black entropy
            hB(t) = if (pT(t) > 0) {
                var hhB = 0D
                for (i <- 0 to t) {
                    if (normalizedHist(i) > 0) {
                        hhB -= normalizedHist(i) / pT(t) * log(normalizedHist(i) / pT(t))
                    }
                }
                hhB // return
            }
            else {
                0D // return
            }

            // White entropy
            val pTW = 1 - pT(t)
            hW(t) = if (pTW > 0) {
                var hhW = 0D
                for (i <- (t + 1) until hist.size) {
                    if (normalizedHist(i) > 0) {
                        hhW -= normalizedHist(i) / pTW * log(normalizedHist(i) / pTW)
                    }
                }
                hhW // return
            }
            else {
                0D // return
            }
        }

        // Find histogram index with maximum entropy
        val hSums = hB.zip(hW).map(i => i._1 + i._2) // Add both histograms
        hSums.indexOf(hSums.max)

    }


    /**
     * Calculate the point of maximum entropy in a histogram
     * @param hist he histogram to process
     * @return A tuple of (maximumEntropySplit, maximumBlackEntropy, maximumWhiteEntropy)
     */
    def maximumEntropySplits(hist: Array[Int]): (Int, Int, Int) = {
        // Normalize histogram, that is makes the sum of all bins equal to 1.
        val normalizedHist = normalizeHistogram(hist)

        // cumulative sum
        val pT = normalizedHist.scanLeft(0D)(_ + _).tail

        // Entropy for black and white parts of the histogram
        val hB = Array.ofDim[Double](hist.size)
        val hW = Array.ofDim[Double](hist.size)
        for (t <- 0 until hist.length) {

            // Black entropy
            hB(t) = if (pT(t) > 0) {
                var hhB = 0D
                for (i <- 0 to t) {
                    if (normalizedHist(i) > 0) {
                        hhB -= normalizedHist(i) / pT(t) * log(normalizedHist(i) / pT(t))
                    }
                }
                hhB // return
            }
            else {
                0D // return
            }

            // White entropy
            val pTW = 1 - pT(t)
            hW(t) = if (pTW > 0) {
                var hhW = 0D
                for (i <- (t + 1) until hist.size) {
                    if (normalizedHist(i) > 0) {
                        hhW -= normalizedHist(i) / pTW * log(normalizedHist(i) / pTW)
                    }
                }
                hhW // return
            }
            else {
                0D // return
            }
        }

        // Find histogram index with maximum entropy
        val hSums = hB.zip(hW).map(i => i._1 + i._2) // Add both histograms
        (hSums.indexOf(hSums.max), hB.indexOf(hB.max), hW.indexOf(hW.max))

    }

    /**
     * Kapur J.N., Sahoo P.K., and Wong A.K.C. (1985) "A New Method for
     * Gray-Level Picture Thresholding Using the Entropy of the Histogram"
     * Graphical Models and Image Processing, 29(3): 273-285
     * M. Emre Celebi 06.15.2007
     * Ported to ImageJ plugin by G.Landini from E Celebi's fourier_0.8 routines
     * Ported to Scala by B. Schlining
     * @param hist
     * @return
     */
    def renyiEntropy(hist: Array[Int]): Int = {
        val P1 = Array.ofDim[Double](256)
        val P2 = Array.ofDim[Double](256)
        val total: Double = hist.sum
        val norm_histo = hist.map(_ / total)
        P1(0) = norm_histo(0)
        P2(0) = 1D - P1(0)
        for (ih <- 1 until 256) {
            P1(ih) = P1(ih - 1) + norm_histo(ih)
            P2(ih) = 1 - P1(ih)
        }

        /* Determine the first non-zero bin */
        val first_idx = P1.indexWhere(abs(_) > 2.220446049250313E-16)
        val first_bin = if (first_idx >= 0) first_idx else 0

        /* Determine the last non-zero bin */
        val last_idx = P2.size - P2.reverse.indexWhere(abs(_) > 2.220446049250313E-16) - 1
        val last_bin = if (last_idx >= 0) last_idx else 255

        val t_star2 = maximumEntropy(hist)

        val alpha: Double = 0.5
        val term = 1D / (1 - alpha)
        val tot_ent = for (it <- first_bin to last_bin) yield {
            /* Entropy of the background pixels */
            val ent_back =  (for (ih <- 0 to it) yield {
                sqrt(norm_histo(ih) / P1(it))
            }).sum

            /* Entropy of the object pixels */
            val ent_obj = (for (ih <- it + 1 until 256) yield {
                sqrt(norm_histo(ih) / P2(it))
            }).sum

            /* Total entropy */
            term * (if ((ent_back * ent_obj) > 0) log(ent_back * ent_obj) else 0D)
        }
        // Find max value and take the largest index into hist where it occurs in hist.
        val t_star1 = tot_ent.size - 1 - tot_ent.reverse.indexOf(tot_ent.max)


        val alpha2 = 2D
        val term2 = 1 / (1 - alpha2)
        val tot_ent2 = for (it <- first_bin to last_bin) yield {
            /* Entropy of the background pixels */
            val ent_back =  (for (ih <- 0 to it) yield {
                sqrt(norm_histo(ih) * norm_histo(ih) / (P1(it) * P1(it)))
            }).sum

            /* Entropy of the object pixels */
            val ent_obj = (for (ih <- it + 1 until 256) yield {
                sqrt(norm_histo(ih) * norm_histo(ih) / (P2(it) * P2(it)))
            }).sum

            /* Total entropy */
            term2 * (if ((ent_back * ent_obj) > 0) log(ent_back * ent_obj) else 0D)
        }
        // Find max value and take the largest index into hist where it occurs in hist.
        val t_star3 = tot_ent2.size - 1 - tot_ent2.reverse.indexOf(tot_ent2.max)

        /* Sort t_star values */
        val t_stars = Seq(t_star1, t_star2, t_star3).sorted


        /* Adjust beta values */
        val (beta1, beta2, beta3) = if (abs(t_stars(0) - t_stars(1)) <= 5) {
            if (abs(t_stars(1) - t_stars(2)) <= 5) {
                (1, 2, 1)
            }
            else {
                (0, 1, 3)
            }
        }
        else {
            if (abs(t_stars(1) - t_stars(2)) <= 5) {
                (3, 2, 1)
            }
            else {
                (1, 2, 1)
            }
        }

        /* Determine the optimal threshold value */
        val omega = P1(t_stars(2)) - P1(t_stars(0))

        (t_stars(0) * (P1(t_stars(0)) + 0.25 * omega * beta1) +
                0.25 * t_stars(1) * omega * beta2 + t_stars(2) *
                (P2(t_stars(2)) + 0.25 * omega * beta3)).toInt

    }

}


