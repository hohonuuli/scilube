package scilube.imglib2

import ij.process.ImageProcessor
import scilube.geometry.Point2D

/**
 * Wrapper around an [[ij.process.ImageProcessor]] that adds convience methods.
 * Can be used as an implicit conversion by importing the
 * org.mbari.esp.ia.imglib._ package.
 *
 * @author Brian Schlining
 * @since 2012-03-19
 */
class ImageProcessorExt(val imageProcessor: ImageProcessor) {

  lazy val processorType = ProcessorType.get(imageProcessor)

  /**
   * @return The maximum possible value for the given [[ij.process.ImageProcessor]]
   */
  def maxTypeValue = processorType.maxValue

  /**
   * Calculates distance from a given point to the nearest point on each of the 4 edges of the
   * image
   * @param centerPoint The point of interest
   * @return A collection of (top, bottom, left, right) distances from the centerpoint to the
   *     edges
   */
  def distanceToEdges(centerPoint: Point2D[Double]): Seq[Double] = {
    val width = imageProcessor.getWidth.toDouble
    val height = imageProcessor.getHeight.toDouble
    val cx = centerPoint.x
    val cy = centerPoint.y

    val sides = Seq(
      Point2D(cx, 0D), //top
      Point2D(cx, height), //bottom
      Point2D(0D, cy), //left
      Point2D(width, cy)
    ) //right

    sides.map(_.distance(centerPoint))
  }

  /**
   * Calculates distance from a given point to all 4 corners of an image
   * @param centerPoint The point of interest
   * @return A collection (topLeft, topRight, bottomLeft, bottomRight) of distances from the
   *     ''centerPoint'' to each of the corners
   */
  def distanceToCorners(centerPoint: Point2D[Double]): Seq[Double] = {

    val width = imageProcessor.getWidth.toDouble
    val height = imageProcessor.getHeight.toDouble

    val sides = Seq(
      Point2D(0D, 0D), // topLeft
      Point2D(width, 0D), // topRight
      Point2D(0D, height), // bottomLeft
      Point2D(width, height)
    ) // bottomRight

    sides.map(_.distance(centerPoint))
  }

  /**
   * @param centerPoint The point of interest
   * @return the minimum distance from a given point to to an edge of the image
   */
  def minimumRadius(centerPoint: Point2D[Double]) =
    distanceToEdges(centerPoint).min

  /**
   * @param centerPoint The point of interest
   * @return the maximum distance from a given point to a corner of the image
   */
  def maximumRadius(centerPoint: Point2D[Double]) =
    distanceToCorners(centerPoint).max

  def update(x: Int, y: Int, z: Int) = imageProcessor.set(x, y, z)

  def update(x: Int, y: Int, z: Float) = imageProcessor.setf(x, y, z)

  def apply(x: Int, y: Int) = imageProcessor.get(x, y)

  def width = imageProcessor.getWidth
  def height = imageProcessor.getHeight

  def bufferedImage = imageProcessor.getBufferedImage

}
