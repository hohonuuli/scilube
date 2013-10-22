package scilube.grid

/**
 * A grid represents a rectangular array of values.
 *
 * @author Brian Schlining
 * @since Sep 30, 2010
 */

trait Grid[A, B, C] {

  /**
   * Defines the x coordinates of the grid Z
   */
  def x: IndexedSeq[A]

  /**
   * Defines the y coordinates of the grid Z
   */
  def y: IndexedSeq[B]

  /**
   * Retrieves a value from grid based in the indices
   * @param i The x index
   * @param j The y index
   * @return The z value at i, j
   */
  def z(i: Int, j: Int): C

  /**
   * Retrieves a value from grid based in the indices
   * @param i The x index
   * @param j The y index
   * @return The z value at i, j
   */
  def apply(i: Int, j: Int): C = z(i, j)

}

