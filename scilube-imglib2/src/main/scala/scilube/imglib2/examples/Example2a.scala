package scilube.imglib2.examples

import org.mbari.net.URLUtilities
import ij.io.Opener
import net.imglib2.img.array.ArrayImgFactory
import net.imglib2.`type`.numeric.real.FloatType
import net.imglib2.`type`.Type
import net.imglib2.img.Img
import ij.ImageJ
import net.imglib2.img.display.imagej.ImageJFunctions

/**
 *
 * @author Brian Schlining
 * @since 2013-03-18
 */
object Example2a extends App {

  def example() {
    // open with ImgOpener using an ArrayImgFactory
    val url = getClass.getResource("/DrosophilaWing.tif")
    val file = URLUtilities.toFile(url)
    val img = new Opener().openImage(file.getAbsolutePath)

    //val duplicate = copyImage(img)

    //ImageJFunctions.show(duplicate)
  }

  def copyImage[T <: Type[T]](input: Img[T]): Img[T] = {

    // create a new Image with the same properties
    // note that the input provides the size for the new image as it implements
    // the Interval interface
    val output = input.factory().create(input, input.firstElement())

    val cursorInput = input.cursor()
    val cursorOutput = output.cursor()

    while (cursorInput.hasNext) {

      // move both cursors forward by one pixel
      cursorInput.fwd()
      cursorOutput.fwd()

      // set the value of this pixel of the output image to the same as the input,
      // every Type supports T.set( T type )
      cursorOutput.get().set(cursorInput.get())
    }

    output

  }

  new ImageJ()
  example()

}
