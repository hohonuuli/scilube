package scilube

import _root_.spire.math.Complex
import edu.emory.mathcs.jtransforms.fft.DoubleFFT_1D
import scala.math._
import scala.util.Random
import org.mbari.math.{DoubleMath, Statlib, Matlib => JMatlib}


/**
 *
 * @author Brian Schlining
 * @since 2012-06-07
 */
object Matlib
        extends Mathematics
        with Probabilities
        with Statistics
