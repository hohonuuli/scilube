package scilube.imglib2.fn

import ij.ImagePlus
import ij.process.{ImageConverter, ImageProcessor}

/**
 * Function to convert an image to 8-bit gray.
 *
 * @author Brian Schlining
 * @since 2012-03-12
 */

object To8BitGray extends ProcessorTransform {

    /**
     * @param from The image to convert. It will not be modified
     * @return A now 8-bit gray image
     */
    def apply(from: ImageProcessor): ImageProcessor = {
        val imagePlus = new ImagePlus("", from.duplicate())
        val imageConvert = new ImageConverter(imagePlus)
        imageConvert.convertToGray8()
        imagePlus.getProcessor
    }
}

