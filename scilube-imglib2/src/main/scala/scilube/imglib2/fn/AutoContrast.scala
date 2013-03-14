package scilube.imglib2.fn

import ij.process.{ImageStatistics, ImageProcessor}
import org.slf4j.LoggerFactory
import ij.measure.Measurements

/**
 * Function that applies autocontrast to an image
 *
 * @param saturated percentile saturation. Clamps outliers outside saturation bounds to the
 * min max saturation value. This keeps autocontrast from using extremely outliers as
 * the min/max.
 * @param normalize if true then it Stretches the pixels between min and max values.
 *  Otherwise it clamps values.
 */
class AutoContrast(val saturated: Double, val normalize: Boolean = false)
        extends ProcessorTransform {

    require(saturated >= 0 && saturated <= 1, "Saturated parameter mus be between 0 and 1")

    private[this] lazy val log = LoggerFactory.getLogger(getClass)

    /**
     * @param from The [[ij.process.ImageProcessor]] to autocontrast. It will not be modified.
     * @return  A new autocontrasted [[ij.process.ImageProcessor]]
     */
    def apply(from: ImageProcessor): ImageProcessor = {
        val imageStatistics = ImageStatistics.getStatistics(from, Measurements.MIN_MAX, null)
        val (hMin, hMax) = getMinAndMax(from, imageStatistics)
        if (hMax > hMin) {
            val min = imageStatistics.histMin + hMin * imageStatistics.binSize
            val max = imageStatistics.histMin + hMax * imageStatistics.binSize
            if (normalize) {
                new Normalize(min, max).apply(from)
            }
            else {
                val to = from.duplicate()
                to.setMinAndMax(min, max)
                to
            }
        }
        else {
            log.warn("HistMin was not greater than HistMax for " + from + ". Returning original image.")
            from
        }
    }

    private def getMinAndMax(imageProcessor: ImageProcessor, imageStatistics: ImageStatistics) = {

        val threshold = if (saturated > 0) {
            (imageStatistics.pixelCount * saturated / 200D).toInt
        }
        else {
            0
        }


        val histogram = imageStatistics.histogram

        val bottomUpCumSum = histogram.scanLeft(0)(_ + _).tail
        val hMin = bottomUpCumSum.zipWithIndex.find(a => a._1 > threshold) match {
            case Some(x) => x._2
            case _ => 0
        }

        val topDownCumSum = histogram.reverse.scanLeft(0)(_ + _).tail
        val idxMax = topDownCumSum.zipWithIndex.find(a => a._1 > threshold) match {
            case Some(x) => x._2
            case _ => 0
        }
        val hMax = 256 - idxMax

        (hMin, hMax)

    }

}

object AutoContrast {

    /**
     * Autocontrast an [[ij.process.ImageProcessor]]
     * @param saturated percentile saturation. Clamps outliers outside saturation bounds to the
     *   min max saturation value. This keeps autocontrast from using extremely outliers as
     *   the min/max.
     * @param normalize if true then it Stretches the pixels between min and max values.
     *   Otherwise it clamps values.
     * @param from The ImageProcessor to autocontrast. It will not be modifed
     * @return A new autocontrasted ImageProcessor
     */
    def apply(saturated: Double, normalize: Boolean, from: ImageProcessor): ImageProcessor = {
        val fn = new AutoContrast(saturated, normalize)
        fn(from)
    }

    /**
     * Autocontrast an [[ij.process.ImageProcessor]]
     * @param saturated percentile saturation. Clamps outliers outside saturation bounds to the
     *   min max saturation value. This keeps autocontrast from using extremely outliers as
     *   the min/max.
     * @param from The ImageProcessor to autocontrast. It will not be modifed
     * @return A new autocontrasted ImageProcessor
     */
    def apply(saturated: Double, from: ImageProcessor): ImageProcessor = apply(saturated, false, from)

}

