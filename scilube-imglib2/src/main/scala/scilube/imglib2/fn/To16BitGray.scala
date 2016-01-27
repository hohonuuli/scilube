package scilube.imglib2.fn

import ij.ImagePlus
import ij.process.{ ImageProcessor, ImageConverter }

/**
 * Function to convert an image to 16-bit gray
 *
 * @author Brian Schlining
 * @since 2012-03-12
 */
object To16BitGray extends ProcessorTransform {

  def apply(from: ImageProcessor): ImageProcessor = {
    val imagePlus = new ImagePlus("", from.duplicate())
    val imageConvert = new ImageConverter(imagePlus)
    imageConvert.convertToGray16()
    imagePlus.getProcessor
  }

}

