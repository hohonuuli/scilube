package scilube

import ij.process.ImageProcessor

/**
 * Image processing functions based on <a href="http://developer.imagej.net/imglib">imglib2<a>
 */
package object imglib2 {


    /**
     * implicit conversion of [[ij.process.ImageProcessor]] to ImageProcessorExt
     * @param imageProcessor
     * @return
     */
    implicit def extendImageProcessor(imageProcessor: ImageProcessor) = new ImageProcessorExt(imageProcessor)

}