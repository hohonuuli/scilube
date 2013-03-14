package scilube.imglib.fn

import ij.process.{FloatProcessor, ImageProcessor}
import org.mbari.math.Statlib

import scilube.imglib2.extendImageProcessor

/**
 * Calculates standard deviation in an image over n * n pixel blocks
 * @author Brian Schlining
 * @since 2012-05-15
 */
object RectangularStd extends ((ImageProcessor, Int) => FloatProcessor) {

    def apply(image: ImageProcessor, n: Int = 1): FloatProcessor = {
        val w = image.width
        val h = image.height
        val stdImage = new FloatProcessor(image.width, image.height)
        for (i <- n until (w - n); j <- n until (h - n)) {
            val buf = Array.ofDim[Double](n + n + 1, n + n + 1)
            for (u <- -n to n; v <- -n to n) {
                buf(u + n)(v + n) = image(i + u, j + v)
            }

            stdImage(i, j) = Statlib.standardDeviation(buf.flatten).toFloat
        }
        stdImage
    }
}
