/**
 * '''scilube-core''' - Science libraries for Scala.
 *
 * The scilube-core package contains the base math functions for Scilube. These functions are loosely
 * modeled after [[http://www.mathworks.com/products/matlab/ Matlab's]] functions. Why Matlab? For
 * many science applications, the code is prototyped in Matlab and then ported to other languages
 * for efficiency reasons or to satisfy deployment requirements. By having a functions with simlilar
 * names and actions, I hope to simplify porting of applications.
 *
 * A large portion of the functionality of this package can be accessed using a single object,
 * [[scilube.Matlib]]. The usage will be obvious to anyone who's worked in Matlab. Many functions,
 * rather than accepting 2D matrices take 1-D `Array[Double]` instead.
 */
package object scilube {

}