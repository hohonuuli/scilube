package scilube.imglib2.examples

import net.imglib2.io.ImgOpener
import org.mbari.net.URLUtilities
import net.imglib2.img.array.ArrayImgFactory
import net.imglib2.`type`.numeric.real.FloatType
import net.imglib2.img.display.imagej.ImageJFunctions
import net.imglib2.view.Views
import ij.ImageJ

/**
 * Displaying images partly using Views
 * @author Brian Schlining
 * @since 2013-03-18
 */
object Example1d extends App {

  def example() {
    val url = getClass.getResource("/DrosophilaWing.tif")
    val file = URLUtilities.toFile(url)
    val img = new ImgOpener().openImg(file.getAbsolutePath,
      new ArrayImgFactory[FloatType],
      new FloatType())

    ImageJFunctions.show(img)

    // use a View to define an interval (min and max coordinate, including) to display
    val view = Views.interval(img, Array(200L, 200L), Array(500L, 350L))

    // display only the part of the Img
    ImageJFunctions.show(view)

    // or the same area rotated by 90 degrees (x-axis (0) and y-axis (1) switched)
    ImageJFunctions.show(Views.rotate(view, 0, 1))
  }

  new ImageJ()
  example()

}
