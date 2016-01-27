package scilube.imglib2.fn

import ij.process.ImageProcessor
import java.awt.image.BufferedImage
import java.awt.geom.Ellipse2D
import java.awt.{ Color, Graphics2D }
import ij.ImagePlus
import scilube.geometry.Point2D

/**
 * Creates a binary image
 *
 * @author Brian Schlining
 * @since 2012-05-14
 */
class PointsToImageFn extends ((Int, Int, Iterable[Point2D[Int]]) => ImageProcessor) {

  def apply(width: Int, height: Int, points: Iterable[Point2D[Int]]): ImageProcessor = {
    val bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY)
    val g2 = bufferedImage.getGraphics.asInstanceOf[Graphics2D]
    val circles = points.map(p =>
      new Ellipse2D.Double(p.x - 2, p.y - 2, 4, 4))
    g2.setPaint(Color.WHITE)
    circles.foreach(g2.fill(_))
    new ImagePlus("", bufferedImage).getProcessor
  }

}
