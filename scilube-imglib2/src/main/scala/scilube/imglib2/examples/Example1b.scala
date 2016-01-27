package scilube.imglib2.examples

import net.imglib2.`type`.numeric.RealType
import net.imglib2.`type`.NativeType
import java.io.File
import net.imglib2.img.array.ArrayImgFactory
import ij.io.Opener
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
    val image = new Opener().openImage(file.getAbsolutePath)
    //ImageJFunctions.show(image)

  }

  new ImageJ()
  example()
}
