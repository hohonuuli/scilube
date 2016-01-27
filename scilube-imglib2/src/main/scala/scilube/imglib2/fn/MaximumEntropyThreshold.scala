package scilube.imglib2.fn

import ij.process.ImageProcessor
import scilube.imglib2.Histograms

/**
 * Threshold an image using a maximum entropy threshold technique.
 * @author Brian Schlining
 * @since 2012-03-07
 */
object MaximumEntropyThreshold extends ProcessorTransform {
  def apply(from: ImageProcessor): ImageProcessor = {
    val to = from.duplicate()
    val threshold = Histograms.maximumEntropy(from.getHistogram)
    to.threshold(threshold)
    to
  }
}

