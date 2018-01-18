package scilube.grid

/**
 * A Grid whose underlying values can be changed
 *
 * @author Brian Schlining
 * @since 2012-04-23
 */
trait MutableGrid[A, B, C] extends Grid[A, B, C] {

  def z(i: Int, j: Int, k: C): Unit

  /**
   * Set the z value directly
   */
  def update(i: Int, j: Int, k: C): Unit = z(i, j, k)

}
