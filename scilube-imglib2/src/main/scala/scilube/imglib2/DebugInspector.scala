package scilub.imglib2

import net.imglib2.img.ImgPlus
import net.imglib2.{IterableRealInterval, Interval, RealInterval, EuclideanSpace}
import net.imglib2.meta.{ImageMetadata, CalibratedSpace, Sourced, Named}

/**
 * Utility for dumping out information about imglib2 classes
 *
 * @author Brian Schlining
 * @since 2012-03-16
 */
object DebugInspector {

    def toString(obj: AnyRef): String = {
        val sb = new StringBuilder
        if (obj.isInstanceOf[Named]) {
            namedToString(obj.asInstanceOf[Named], sb)
        }
        if (obj.isInstanceOf[Sourced]) {
            sourcedToString(obj.asInstanceOf[Sourced], sb)
        }
        if (obj.isInstanceOf[ImgPlus[_]]) {
            imgPlusToString(obj.asInstanceOf[ImgPlus[_]], sb)
        }
        if (obj.isInstanceOf[EuclideanSpace]) {
            euclidianSpaceToString(obj.asInstanceOf[EuclideanSpace], sb)
        }
        if (obj.isInstanceOf[CalibratedSpace]) {
            calibrationSpaceToString(obj.asInstanceOf[CalibratedSpace], sb)
        }
        if (obj.isInstanceOf[RealInterval]) {
            realIntervalToString(obj.asInstanceOf[RealInterval], sb)
        }
        if (obj.isInstanceOf[Interval]) {
            intervalToString(obj.asInstanceOf[Interval], sb)
        }
        if (obj.isInstanceOf[IterableRealInterval[_]]) {
            iterableRealIntervalToString(obj.asInstanceOf[IterableRealInterval[_]], sb)
        }
        if (obj.isInstanceOf[ImageMetadata]) {
            imageMetadataToString(obj.asInstanceOf[ImageMetadata], sb)
        }

        if (sb.length > 0) {
            sb.toString()
        }
        else {
            obj.toString
        }
    }

    private def imgPlusToString(obj: ImgPlus[_], sb: StringBuilder) {
        sb.append("ImgPlus\n")
        sb.append("\timgPlus = ").append(obj).append('\n')
        sb.append("\timg = ").append(obj.getImg).append('\n')
    }

    private def euclidianSpaceToString(obj: EuclideanSpace, sb: StringBuilder) {
        sb.append("EuclidieanSpace\n\tnumDimensions = ").append(obj.numDimensions()).append('\n')
    }

    private def namedToString(obj: Named, sb: StringBuilder) {
        sb.append("Named\n\tname = ").append(obj.getName).append('\n')
    }

    private def sourcedToString(obj: Sourced, sb: StringBuilder) {
        sb.append("Source\n\tsource = ").append(obj.getSource).append('\n')
    }

    private def calibrationSpaceToString(obj: CalibratedSpace, sb: StringBuilder) {
        sb.append("CalibratedSpace").append('\n')
        for (i <- 0 until obj.numDimensions()) {
            sb.append("\taxis = ").append(obj.axis(i)).append('\n')
            sb.append("\tcalibration = ").append(obj.calibration(i)).append('\n')
        }
    }

    private def realIntervalToString(obj: RealInterval, sb: StringBuilder) {
        sb.append("RealInterval").append('\n')
        for (i <- 0 until obj.numDimensions()) {
            sb.append("\tDim #" + i).append('\n')
            sb.append("\t\trealMin = ").append(obj.realMin(i)).append('\n')
            sb.append("\t\trealMax = ").append(obj.realMax(i)).append('\n')
        }
    }

    private def intervalToString(obj: Interval, sb: StringBuilder) {
        sb.append("Interval").append('\n')
        for (i <- 0 until obj.numDimensions()) {
            sb.append("\tDim #" + i).append('\n')
            sb.append("\t\tmin = ").append(obj.min(i)).append('\n')
            sb.append("\t\tmMax = ").append(obj.max(i)).append('\n')
        }
    }

    private def iterableRealIntervalToString(obj: IterableRealInterval[_], sb: StringBuilder) {
        sb.append("IterableRealInterval").append('\n')
        sb.append("\tsize = ").append(obj.size()).append('\n')
    }

    private def imageMetadataToString(obj: ImageMetadata, sb: StringBuilder) {
        sb.append("ImageMetadata").append('\n')
        sb.append("\tvalidBits = ").append(obj.getValidBits).append('\n')
        sb.append("\tcompositChanelCount = ").append(obj.getCompositeChannelCount).append('\n')
        for (i <- 0 until obj.getCompositeChannelCount) {
            sb.append("\t\tChannel # " + i).append('\n')
            sb.append("\t\tchannelMinimum = ").append(obj.getChannelMinimum(i)).append('\n')
            sb.append("\t\tchannelMaximum = ").append(obj.getChannelMaximum(i)).append('\n')
        }
        sb.append("\tcolorTableCount = ").append(obj.getColorTableCount).append('\n')
        for (i <- 0 until obj.getColorTableCount) {
            sb.append("\t\tColorTable #" + i).append('\n')
            //sb.append("\t\tcolorTable8 = ").append(obj.getColorTable8(i)).append('\n')
            //sb.append("\t\tcolorTable16 = ").append(obj.getColorTable16(i)).append('\n')
        }
    }

}
