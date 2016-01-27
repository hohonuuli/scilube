package scilube.imglib2.fn

import ij.process.ImageProcessor
import scilube.imglib2.extendImageProcessor

/**
 * Applies a mask to an image. Black areas in the mask are set to black in the ''truth'' image
 * @author Brian Schlining
 * @since 2012-03-20
 */
object ApplyMask extends ((ImageProcessor, ImageProcessor) => ImageProcessor) {

  /**
   * Apply a mask to an image.
   * @param mask The mask to apply. Black pixels in the mask will be set to black in the ''truth''
   *     image
   * @param truth The image of interest. It will not be modified
   * @return A new truth image with masked pixels set to black
   */
  def apply(mask: ImageProcessor, truth: ImageProcessor): ImageProcessor = {
    require(
      mask.getWidth == truth.getWidth && mask.getHeight == truth.getHeight,
      "Images must be the same size"
    )

    val maskBlack = mask.processorType.minValue.toInt
    val truthBlack = truth.processorType.minValue.toInt
    val to = truth.duplicate()

    for (i <- 0 until mask.width; j <- 0 until mask.height) {
      if (mask(i, j) == maskBlack) {
        to(i, j) = truthBlack
      }
    }
    to
  }
}

