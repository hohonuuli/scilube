package scilube.imglib2.examples

import net.imglib2.img.cell.CellImgFactory
import net.imglib2.`type`.numeric.real.FloatType
import net.imglib2.img.display.imagej.ImageJFunctions
import ij.ImageJ

/**
 *
 * @author Brian Schlining
 * @since 2013-03-18
 */
object Example1c extends App {

  def example() {
    // Create ImgFactory base on cells (sellsize = 5x5x5...x5) that will
    // instantiate the Img
    val imgFactory = new CellImgFactory[FloatType](5)

    // create an 3d-Img with dimensions 20x30x40 (here cell size is (5x5x5))
    val img1 = imgFactory.create(Array(20L, 30L, 40L), new FloatType())

    // create another image with the same size
    // note that the input provides the size for the new image as it implements
    // the Interval interface
    val img2 = imgFactory.create(img1, img1.firstElement())

    // display both (but they are empty)
    ImageJFunctions.show(img1);
    ImageJFunctions.show(img2);
  }

  new ImageJ()
  example()

}

