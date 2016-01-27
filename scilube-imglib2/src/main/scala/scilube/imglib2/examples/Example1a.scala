package scilube.imglib2.examples

import net.imglib2.`type`.numeric.NumericType
import net.imglib2.`type`.NativeType
import ij.io.Opener
import net.imglib2.img.{ Img, ImagePlusAdapter }
import net.imglib2.img.display.imagej.ImageJFunctions
import ij.ImageJ
import org.mbari.net.URLUtilities

/**
 * @see http://fiji.sc/ImgLib2_Examples
 * @author Brian Schlining
 * @since 2013-03-16
 */
object Example1a extends App {

  def example[T <: NumericType[T] with NativeType[T]]() {

    val url = getClass.getResource("/DrosophilaWing.tif")
    val file = URLUtilities.toFile(url)
    val imagePlus = new Opener().openImage(file.getAbsolutePath)
    // display image via ImageJ
    imagePlus.show()
    // wrap it into an ImgLib image (no copying)
    val image: Img[T] = ImagePlusAdapter.wrap(imagePlus)
    // display image via ImgLib using ImagJ
    ImageJFunctions.show(image)
  }

  new ImageJ()
  example()

}
