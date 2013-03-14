package scilube.imglib2.fn

import ij.process.{ImageProcessor}
import org.mbari.math.Statlib
import scilube.imglib2.{ProcessorType, extendImageProcessor}

/**
 * From Gonzalez and Woods page 758.
 *
 * @author Brian Schlining
 * @since 2012-05-14
 */
class VariableLocalThreshold(val a: Double, val b: Double, val k: Int) extends ProcessorTransform {

    def apply(image: ImageProcessor): ImageProcessor = {
        val w = image.width
        val h = image.height

        // Create a new image filled with black
        val to = image.duplicate()
        val t = ProcessorType.get(to)
        val black = t.minValue.toInt
        val white = t.maxValue.toInt
        for (i <- 0 until w; j <- 0 until h) {
            to(i, j) = black
        }

        for (i <- k until (w - k); j <- k until (h - k)) {
            val length = (k + k + 1) * (k + k + 1)
            val buf = Array.ofDim[Double](length)
            for (u <- -k to k; v <- -k to k) {
                val idx = (u + k) + ((u + k) * (v + k))
                buf(idx) = image(i + u, j + v)
            }
            val std = Statlib.standardDeviation(buf)
            val mean = Statlib.mean(buf)
            val pixel = image(i, j)
            to(i, j) = if (pixel > a * std && pixel > b * mean) white else black
        }

        to

    }

}
