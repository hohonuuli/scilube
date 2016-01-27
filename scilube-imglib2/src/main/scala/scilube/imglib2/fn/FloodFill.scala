package scilube.imglib2.fn

import java.util.LinkedList
import ij.process.{ ColorProcessor, ImageProcessor }
import scilube.imglib2.extendImageProcessor

/**
 * Flood fill a binary image.
 *
 * @author Brian Schlining
 * @since 2012-03-07
 */
object FloodFill extends ProcessorTransform {

  private[this] val unlabledValue = 1

  def apply(from: ImageProcessor): ImageProcessor = {
    require(
      from.getClass != classOf[ColorProcessor],
      "ImageProcessor can not be a ColorProcessor"
    )
    val to = from.duplicate()
    labelRegions(to)
    to
  }

  /**
   * Uses a flood fill to sequentially label contiguous non-background (i.e. not 0) regions of
   * an image.
   *
   * Destructively modifies input image. Assumes Black (i.e. value of 0) pixels are background,
   * any other value is foreground. Foreground regions will be flood filled.
   *
   * @param to The input image. It should only contain white (foreground) and black (background)
   *     pixels
   */
  private def labelRegions(to: ImageProcessor) {
    val background = to.processorType.minValue.toInt

    val w = to.getWidth
    val h = to.getHeight

    // Set non-zero pixels to 1. Everything else to black
    for (u <- 0 until w; v <- 0 until h) {
      val pixel = to.get(u, v)
      val color = if (pixel == background) { background }
      else { unlabledValue }
      to.set(u, v, color)
    }

    // Flood fill
    var label = unlabledValue + 1 // Start labeling at 1 above our 'unlabeled' value
    for (u <- 0 until w; v <- 0 until h) {
      val pixel = to.get(u, v)
      if (pixel == unlabledValue) {
        floodFill(to, u, v, label)
        label += 1
      }
    }

  }

  private def floodFill(to: ImageProcessor, x: Int, y: Int, label: Int) {
    val w = to.getWidth
    val h = to.getHeight
    case class Node(x: Int, y: Int)
    val q = new LinkedList[Node]
    q.addFirst(Node(x, y))
    while (!q.isEmpty) {
      val n = q.removeLast()
      if (n.x >= 0 &&
        n.x < w &&
        n.y >= 0 &&
        n.y < h &&
        to.getPixel(n.x, n.y) == unlabledValue) {
        to.putPixel(n.x, n.y, label)
        q.addFirst(Node(n.x + 1, n.y))
        q.addFirst(Node(n.x, n.y + 1))
        q.addFirst(Node(n.x, n.y - 1))
        q.addFirst(Node(n.x - 1, n.y))
      }
    }
  }
}

