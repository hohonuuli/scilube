package scilube.imglib2.fn

import scilube.imglib2.fuzzysets.{ TriangularFn, SigmaFn }
import ij.process.ImageProcessor
import scala.math._
import scilube.imglib2.Histograms
import scilube.imglib2.extendImageProcessor

/**
 * '''Experimental'''
 *
 * Contrast an image using thresholds calculated by the [[scilube.imglib2.Histograms]].maximumEntropy
 * @author Brian Schlining
 * @since 2012-03-13
 */
object DynamicFuzzyContrast extends ProcessorTransform {
  def apply(from: ImageProcessor): ImageProcessor = {

    // --- Setup fuzzy set transformations
    val vd = 0D // dark singleton
    val vb = from.maxTypeValue // bright singleton
    val vg = floor(vb / 2D) // gray singleton

    val (maxEntropy, maxBlackEntropy, maxWhiteEntropy) = if (vb > 256) {
      val buf = To8BitGray(from)
      val (mE, mB, mW) = Histograms.maximumEntropySplits(buf.getHistogram)
      (mE * 255, mB * 255, mW * 255)
    } else {
      Histograms.maximumEntropySplits(from.getHistogram)
    }

    val splits = Seq(maxEntropy, maxBlackEntropy, maxWhiteEntropy)

    val black = splits.min
    val white = splits.max
    val gray = white - (white - black) / 2

    val μd = new SigmaFn(gray, black) // Dark function (Sigma)
    val μg = new TriangularFn(black, gray, white) // Gray function (Triangular)
    val μb = new SigmaFn(gray, white) // Bright function (Sigma)

    // --- Apply transform
    val to = from.duplicate()
    for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
      val z = to.get(i, j)
      val v = round((μd(z) * vd + μg(z) * vg + μb(z) * vb) / (μd(z) + μg(z) + μb(z))).toInt
      to.set(i, j, v)
    }

    to
  }

}

