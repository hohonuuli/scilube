package scilube.imglib2

import ij.process.ImageProcessor

/**
 * Image processing functions based on <a href="http://developer.imagej.net/imglib">imglib2<a>
 */
package object fn {
  
  type ProcessorTransform = (ImageProcessor) => ImageProcessor
 
}