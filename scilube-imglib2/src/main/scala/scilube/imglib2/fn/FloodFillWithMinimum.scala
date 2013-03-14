package scilube.imglib2.fn

import ij.process.ImageProcessor
import java.util.LinkedList
import scala.collection.mutable
import scilube.imglib2.extendImageProcessor

/**
 * Does a flood fill but areas that don't meet the minimum size are discarded
 * @param minPixels The minimum acceptable number of pixels for each filled region. If a region
 *  does not have enough pixels it is set to black
 * @author Brian Schlining
 * @since 2012-03-09
 */
class FloodFillWithMinimum(minPixels: Int) extends ProcessorTransform {

    private[this] val unlabledValue = 1

    def apply(from: ImageProcessor): ImageProcessor = {
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
        val black = to.processorType.minValue.toInt

        val w = to.getWidth
        val h = to.getHeight

        // Set non-zero pixels to 1. Everything else to black
        for (u <- 0 until w; v <- 0 until h) {
            val pixel = to.get(u, v)
            val color = if (pixel == black) black else unlabledValue
            to.set(u, v, color)
        }

        // Flood fill
        var label = unlabledValue + 1 // Start labeling at 1 above our 'unlabeled' value
        for (u <- 0 until w; v <- 0 until h) {
            val pixel = to.get(u, v)
            if (pixel == unlabledValue) {
                if (floodFill(to, u, v, label)) {
                    label += 1
                }
            }
        }

    }

    /**
     *
     * @param to
     * @param x
     * @param y
     * @param label
     * @return true if the flooded region is at least _minPixels_ in size. False otherwise
     */
    private def floodFill(to: ImageProcessor, x: Int, y: Int, label: Int): Boolean = {
        val black = to.processorType.minValue.toInt

        val w = to.getWidth
        val h = to.getHeight
        case class Node(x: Int, y: Int)
        val pixels = new mutable.ArrayBuffer[Node]
        val q = new LinkedList[Node]
        q.addFirst(Node(x, y))
        while (!q.isEmpty) {
            val n = q.removeLast()
            if (n.x >= 0 &&
                    n.x < w &&
                    n.y >= 0 &&
                    n.y < h &&
                    to.getPixel(n.x, n.y) == unlabledValue) {
                pixels += n
                to.putPixel(n.x, n.y, label)
                q.addFirst(Node(n.x + 1, n.y))
                q.addFirst(Node(n.x, n.y + 1))
                q.addFirst(Node(n.x, n.y - 1))
                q.addFirst(Node(n.x - 1, n.y))
            }
        }

        if (pixels.size < minPixels) {
            // the region is to small, set it's pixels to black
            pixels.foreach(n => to.putPixel(n.x, n.y, black))
            false
        }
        else {
            true
        }
    }

}

object FloodFillWithMinimum {
    /**
     * Apply a flood fill to an image
     * @param minPixels The minimum acceptable number of pixels for a flood filled region.
     * @param from The image to flood fill. It will not be modified
     * @return A flood filled images where 0 is background any non-zero region is a flood filled
     *     region
     */
    def apply(minPixels: Int, from: ImageProcessor): ImageProcessor = {
        val fn = new FloodFillWithMinimum(minPixels)
        fn(from)
    }
}