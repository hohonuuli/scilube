package scilube.imglib2.fn

import ij.process.{ImageProcessor}
import scala.math._
import scilube.imglib2.fuzzysets.{TriangularFn, SigmaFn}

import scilube.imglib2.extendImageProcessor

/**
 * Ue fuzzy sets to adjust the contrast of an image. This technique is from Digital Image
 * Processing, 3rd Edition, Gonzalez and Woods. pg. 186
 *
 *
 * @author Brian Schlining
 * @since 2012-03-12
 */
object FuzzyContrast extends ProcessorTransform {
    def apply(from: ImageProcessor): ImageProcessor = {

        // --- Setup fuzzy set transformations
        val vd = 0D // dark singleton
        val vb = from.maxTypeValue // bright singleton
        val vg = floor(vb / 2D) // gray singleton

        val black = floor(vb / 3D)
        val gray = vg
        val white = vb - black

        val μd = new SigmaFn(gray, black) // Dark function (Sigma)
        val μg = new TriangularFn(black, gray, white) // Gray function (Triangular)
        val μb = new SigmaFn(gray, white) // Bright function (Sigma)

        // --- Apply transform
        val to = from.duplicate()
        for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
            val z = to.get(i, j)
            val v = round((μd(z) * vd + μg(z) * vg + μb(z) * vb) / (μd(z) + μg(z) + μb(z))).toInt
            to.set(i, j, v)
        }

        to
    }

}



