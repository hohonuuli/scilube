package scilube.imglib2.fn

import java.awt.image.{ Kernel, ConvolveOp }
import ij.process.ImageProcessor
import ij.ImagePlus

/**
 * Uses Java2D's [[java.awt.image.ConvolveOp]] to perform edge detection. Does a nice job, but the
 * resulting image will not be a binary image. Background pixels will be 0, foreground will be any value
 * other than 0.
 *
 * @author Brian Schlining
 * @since 2010-11-23
 */
object EdgeDetection extends ProcessorTransform {

  private[this] lazy val imageOp = {
    // Laplace filter inverted
    val edgeKernel = Array(0F, -1F, 0F,
      -1F, 4F, -1F,
      0F, -1F, 0F)
    new ConvolveOp(new Kernel(3, 3, edgeKernel))
  }

  def apply(from: ImageProcessor): ImageProcessor = {
    val image = from.duplicate().getBufferedImage
    val edges = imageOp.filter(image, null)
    new ImagePlus("", edges).getProcessor
  }
}
