package scilube.grid

/**
 *
 * @author Brian Schlining
 * @since 2012-04-23
 */
trait MutableGrid[A, B, C] extends Grid[A, B, C] {

    def z(i: Int, j: Int, k: C): Unit

    /**
     * Set the z value directly
     */
    def update(i: Int, j: Int, k: C) { z(i, j, k) }


}
