package scilube.imglib2

import javax.imageio.ImageIO
import org.mbari.awt.image.ImageUtilities
import java.awt.image.BufferedImage
import java.net.URL
import java.io.File
import ij.{IJ, ImagePlus}
import ij.process.ImageProcessor
import net.imglib2.io.ImgOpener
import org.mbari.net.URLUtilities
import net.imglib2.img.display.imagej.ImageJFunctions
import net.imglib2.img.ImgPlus
import net.imglib2.`type`.numeric.integer.UnsignedByteType
import javax.media.jai.JAI

/**
 *
 * @author Brian Schlining
 * @since 2012-03-19
 */

class ImageIOB extends ImageIOTrait {


    def read(url: URL): ImagePlus = {
        url.getProtocol match {
            // Use imglib2 to read local files.
            case "file" => {
                val io = new ImgOpener
                val imgPlus = io.openImg(URLUtilities.toFile(url).getAbsolutePath)
                imgPlus.numDimensions() match {
                    case 2 => ImageJFunctions.wrapUnsignedShort(imgPlus, "") // B&W
                    case 3 => ImageJFunctions.wrapUnsignedByte(imgPlus.asInstanceOf[ImgPlus[UnsignedByteType]], "") // Color
                }
            }
            case _ => new ImagePlus(url.toExternalForm, readAsBufferedImage(url))
        }
    }


    //new ImagePlus(url.toExternalForm, readAsBufferedImage(url))

    def readAsBufferedImage(url: URL): BufferedImage = {
        val name = url.getFile.toLowerCase
        // Tiff's get special handling
        if (name.endsWith(".tif") || name.endsWith(".tiff")) {
            val renderedImage = JAI.create("url", url)
            ImageUtilities.toBufferedImage(renderedImage)
        }
        else {
            ImageIO.read(url)
        }
    }


    def write(imagePlus: ImagePlus, file: File) {
        val path = file.getAbsolutePath
        if (path.endsWith(".png") || path.endsWith(".gif") || path.endsWith("jpg")) {
            write(imagePlus.getBufferedImage(), file);
        }
        else {
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
        }
        else {
            write(new ImagePlus("", bufferedImage), file)
        }
    }

}
