package scilube.imglib2.examples

import org.mbari.net.URLUtilities
import net.imglib2.io.ImgOpener
import net.imglib2.img.array.ArrayImgFactory
import net.imglib2.`type`.numeric.real.FloatType
import net.imglib2.img.{ImgFactory, Img}
import net.imglib2.`type`.Type
import net.imglib2.img.cell.CellImgFactory
import net.imglib2.img.display.imagej.ImageJFunctions
import ij.ImageJ

/**
 * Here we want to copy an ArrayImg into a CellImg using a generic method,
 * but we cannot do it with simple Cursors as they have a different iteration order.
 * @author Brian Schlining
 * @since 2013-03-18
 */
object Example2b extends App {

  def example() {
    // open with ImgOpener using an ArrayImgFactory
    val url = getClass.getResource("/DrosophilaWing.tif")
    val file = URLUtilities.toFile(url)
    val img = new ImgOpener().openImg(file.getAbsolutePath,
      new ArrayImgFactory[FloatType],
      new FloatType())

    // copy the image into a CellImg with a cellsize of 20x20
    val duplicate = copyImageCorrect(img, new CellImgFactory[FloatType](20));

    ImageJFunctions.show(img)
    ImageJFunctions.show(duplicate)

  }

  def copyImageCorrect[T <: Type[T]](input: Img[T], imgFactory: ImgFactory[T]) = {

    // create a new Image with the same dimensions but the other imgFactory
    // note that the input provides the size for the new image by implementing the Interval
    val output = imgFactory.create(input, input.firstElement())

    // create a cursor that automatically localizes itself on every move
    val cursorInput = input.localizingCursor()
    val randomAccess = output.randomAccess()

    while(cursorInput.hasNext) {
      // move input cursor forward
      cursorInput.fwd()
      // set the output cursor to the position of the input cursor
      randomAccess.setPosition(cursorInput)
      // set the value of this pixel of the output image, every Type supports T.set( T type )
      randomAccess.get().set(cursorInput.get())

    }
    output
  }

  new ImageJ
  example()

}
