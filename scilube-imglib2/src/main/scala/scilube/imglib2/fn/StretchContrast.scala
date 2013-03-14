package scilube.imglib2.fn

import ij.process.ImageProcessor
import org.mbari.math.{Matlib}
import scilube.imglib2.extendImageProcessor

/**
 * Stetch contrast in an image between 2 intensities. Values between lowIdx and highIdx will be
 * remapped to values between lowValue and highValue. values above and below the provided idx will
 * be remapped between 0 and lowValue and highValue and the maximum-possible value.
 *
 * @param lowIdx The low value to start stretch
 * @param lowValue The value to remap lowIdx to.
 * @param highIdx The high value to end stretch
 * @param highValue THe value to remap highIdx to.
 *
 * @author Brian Schlining
 * @since 2012-03-14
 *
 */
class StretchContrastFn(val lowIdx: Int, val lowValue: Int, val highIdx: Int, val highValue: Int)
        extends (ImageProcessor => ImageProcessor) {

    private[this] val lowX = Array[Double](0, lowIdx)
    private[this] val lowY = Array[Double](0, lowValue)
    private[this] val midX = Array[Double](lowIdx, highIdx)
    private[this] val midY = Array[Double](lowValue, highValue)

    private[this] val lowInterp = Matlib.interpolate(lowX, lowY, _: Array[Double])
    private[this] val midInterp = Matlib.interpolate(midX, midY, _: Array[Double])


    /**
     * @param from The image to stretch. It will not be modified
     * @return the stretched image
     */
    def apply(from: ImageProcessor): ImageProcessor = {
        val maxValue = from.maxTypeValue
        val highX = Array[Double](highIdx, maxValue)
        val highY = Array[Double](highValue, maxValue)
        val highInterp = Matlib.interpolate(highX, highY, _: Array[Double])
        def interp(v: Int): Int = {
            val va = Array(v.toDouble)
            val u = if (v <= lowIdx) {lowInterp(va)}
            else if (v >= highIdx) {highInterp(va)}
            else {midInterp(va)}
            u(0).toInt
        }

        val to = from.duplicate()
        for (i <- 0 until to.getWidth; j <- 0 until to.getHeight) {
            val z = interp(to.get(i, j))
            to.set(i, j, z)
        }
        to
    }

}
