package scilube.imglib2.fn

import ij.process.ImageProcessor
import scilube.imglib2.extendImageProcessor

/**
 * Extract regions from an image by ''slicing'' it. Simply put, you:
 * - Specify bottom and top intensities
 * - Extract a binary image where pixels with intensity values outside your range are set to
 *   black (0), pixels out your range are set to white.
 * @author Brian Schlining
 * @since 2012-03-14
 */
class IntensitySlice(val bottom: Int, val top: Int) extends (ImageProcessor => ImageProcessor) {
  require(bottom <= top, "Check your args!! bottom (" + bottom +
    ") should be less than or equal to top (" + top + ")")

  def apply(from: ImageProcessor): ImageProcessor = {
    val to = from.duplicate()
    val black = to.processorType.minValue.toInt
    val white = to.processorType.maxValue.toInt
    for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
      val z = to.get(i, j)
      val newZ = if (to.get(i, j) < bottom || to.get(i, j) > top) black else white
      to.set(i, j, newZ)
    }
    to
  }

}

object IntensitySlice {
  def apply(bottom: Int, top: Int, imageProcessor: ImageProcessor) =
    new IntensitySlice(bottom, top).apply(imageProcessor)
}
