package scilube.imglib2

import java.awt.image.BufferedImage
import java.io.File
import ij.process.ImageProcessor
import java.net.URL
import javax.imageio.ImageIO
import javax.media.jai.JAI
import org.mbari.awt.image.ImageUtilities
import ij.{ IJ, ImagePlus }

/**
 *
 * @author Brian Schlining
 * @since 2012-03-06
 */

class ImageIOA extends ImageIOTrait {

  def read(url: URL): ImagePlus = new ImagePlus(url.toExternalForm, readAsBufferedImage(url))

  def readAsBufferedImage(url: URL): BufferedImage = {
    val name = url.getFile.toLowerCase
    // Tiff's get special handling
    if (name.endsWith(".tif") || name.endsWith(".tiff")) {
      val renderedImage = JAI.create("url", url)
      ImageUtilities.toBufferedImage(renderedImage)
    } else {
      ImageIO.read(url)
    }
  }

  def write(imagePlus: ImagePlus, file: File) {
    val path = file.getAbsolutePath
    if (path.endsWith(".png") || path.endsWith(".gif") || path.endsWith("jpg")) {
      write(imagePlus.getBufferedImage(), file);
    } else {
      IJ.save(imagePlus, file.getAbsolutePath());
    }
  }

  def write(imageProcessor: ImageProcessor, file: File) {
    write(new ImagePlus("", imageProcessor), file)
  }

  def write(bufferedImage: BufferedImage, file: File) {
    val path = file.getAbsolutePath
    if (path.endsWith(".png") || path.endsWith(".gif") || path.endsWith("jpg")) {
      val idx = path.lastIndexOf(".")
      val ext = path.substring(idx + 1)
      ImageIO.write(bufferedImage, ext, file)
    } else {
      write(new ImagePlus("", bufferedImage), file)
    }
  }
}
