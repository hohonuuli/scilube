package scilube.imglib2.fn

import ij.ImagePlus
import ij.process.{ImageProcessor, ImageConverter}

/**
 * Function to convert an image to RGB
 *
 * @author Brian Schlining
 * @since 2012-03-12
 */
object ToRGB extends ProcessorTransform {

    def apply(from: ImageProcessor): ImageProcessor = {
        val imagePlus = new ImagePlus("", from.duplicate())
        val imageConvert = new ImageConverter(imagePlus)
        imageConvert.convertToRGB()
        imagePlus.getProcessor
    }

}

