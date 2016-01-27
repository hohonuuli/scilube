package scilube.imglib2

import ij.ImagePlus
import java.net.URL
import java.awt.image.BufferedImage
import java.io.File
import ij.process.ImageProcessor

/**
 *
 * @author Brian Schlining
 * @since 2012-03-06
 */

trait ImageIOTrait {

  /**
   * Read an image from a URL
   * @param url to read from
   * @return the resulting image as an ImagePlus
   */
  def read(url: URL): ImagePlus

  /**
   * Read an image from a URL as a BufferedImage
   * @param url to read from
   * @return the resulting BufferedImage
   */
  def readAsBufferedImage(url: URL): BufferedImage

  def write(imagePlus: ImagePlus, file: File): Unit

  def write(imageProcessor: ImageProcessor, file: File): Unit

  def write(bufferedImage: BufferedImage, file: File): Unit

}
