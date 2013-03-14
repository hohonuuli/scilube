package scilube.imglib2.fn

import ij.process.{FloatProcessor, ImageProcessor}
import scilube.imglib2.extendImageProcessor

/**
 * Normalize an image between a max and min value
 * @author Brian Schlining
 * @since 2012-03-07
 */
class Normalize(val min: Double, val max: Double) extends ProcessorTransform {

    def apply(from: ImageProcessor): ImageProcessor = {
        val to = from.duplicate()
        if (from.isInstanceOf[FloatProcessor]) {
            normalizeFloat(from)
        }
        else {
            val max2 = from.maxTypeValue.toInt
            val range = max2 + 1

            val lut = Array.ofDim[Int](range)
            for (i <- 0 until range) {
                lut(i) = if (i <= min) {0}
                else if (i >= max) {max2}
                else (((i - min).toDouble / (max - min)) * max2).toInt
            }
            to.applyTable(lut)
        }
        to
    }

    private def normalizeFloat(to: ImageProcessor) = {
        val scale = if (max > min) {1D / (max - min)}
        else {1D}

        val size = to.getWidth * to.getHeight
        val pixels = to.getPixels.asInstanceOf[Array[Float]] // Actually an Array[float] where float is the java primitive
        var v: Double = 0
        for (i <- 0 until size) {
            v = pixels(i) - min
            if (v < 0) {
                v = 0
            }
            v = v * scale
            if (v > 1.0) {
                v = 1
            }
            pixels(i) = v.toFloat
        }

    }
}

object Normalize {
    def apply(min: Double, max: Double, from: ImageProcessor) = {
        val fn = new Normalize(min, max)
        fn(from)
    }
}
