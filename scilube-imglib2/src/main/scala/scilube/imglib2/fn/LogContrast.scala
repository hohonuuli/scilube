package scilube.imglib2.fn

import ij.process.ImageProcessor
import scala.math._
import scilube.imglib2.extendImageProcessor

/**
 * Apply a log transform to the image intensity
 *
 * @author Brian Schlining
 * @since 2012-03-14
 */

object LogContrast extends (ImageProcessor => ImageProcessor) {

  def apply(from: ImageProcessor): ImageProcessor = {
    val maxValue = from.maxTypeValue
    val c = maxValue / log10(maxValue)
    val to = from.duplicate()
    for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
      val newValue = round(c * log10(1 + to.get(i, j))).toInt
      to.set(i, j, newValue)
    }
    to
  }
}

