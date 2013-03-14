package scilube.imglib2.fn

import ij.process.ImageProcessor
import scala.math._
import scilube.imglib2.extendImageProcessor

/**
 * Apply Power-Law (Gamma) Transformation to image intensity
 * @param gamma The gamma value to apply
 * @author Brian Schlining
 * @since 2012-03-14
 */
class GammaContrast(val gamma: Double) extends (ImageProcessor => ImageProcessor) {
    require(gamma > 0)

    /**
     * @param from Image to apply gamma to. It is not modified
     * @return A new ImageProcessor with gamma applied
     */
    def apply(from: ImageProcessor): ImageProcessor = {
        val maxValue = from.maxTypeValue
        val c = maxValue / pow(maxValue, gamma)
        val to = from.duplicate()
        for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
            val newValue = round(c * pow(to.get(i, j), gamma)).toInt
            to.set(i, j, newValue)
        }
        to
    }
}

object GammaContrast {
    def apply(gamma: Double, from: ImageProcessor) = {
        val fn = new GammaContrast(gamma)
        fn(from)
    }
}