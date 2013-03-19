package scilube.imglib2.examples

import net.imglib2.`type`.numeric.RealType
import net.imglib2.`type`.NativeType
import java.io.File
import net.imglib2.img.array.ArrayImgFactory
import net.imglib2.io.ImgOpener
import net.imglib2.img.Img
import net.imglib2.img.display.imagej.ImageJFunctions
import net.imglib2.img.cell.CellImgFactory
import net.imglib2.`type`.numeric.real.FloatType
import ij.ImageJ
import org.mbari.net.URLUtilities

/**
 * 
 * @author Brian Schlining
 * @since 2013-03-18
 */
object Example1b extends App {

  def example[T <: RealType[T] with NativeType[T]]() {
    val url = getClass.getResource("/DrosophilaWing.tif")
    val file = URLUtilities.toFile(url)
    val imgFactory = new ArrayImgFactory[T]
    // Return type will be defined by the opener
    val image: Img[T] = new ImgOpener().openImg(file.getAbsolutePath, imgFactory)
    ImageJFunctions.show(image)

    // Open as float independant of the type of the image.
    val imageFloat = new ImgOpener().openImg(file.getAbsolutePath,
      new CellImgFactory[FloatType](10), new FloatType())
    ImageJFunctions.show(imageFloat)
  }

  new ImageJ()
  example()
}
