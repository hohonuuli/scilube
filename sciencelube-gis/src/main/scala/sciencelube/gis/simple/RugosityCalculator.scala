package sciencelube.gis.simple

import scala.math._
import sciencelube.grid.{MutableDoubleGrid, Grid}
import sciencelube.geometry.{Triangle3D, Point3D}
import java.io.File


/**
 *
 * @author Brian Schlining
 * @since 2012-04-23
 */
object RugosityCalculator {

    def apply(grid: Grid[Double, Double, Double]): Grid[Double, Double, Double] = {

        val x = grid.x
        val y = grid.y
        val dx = x.init.zip(x.tail).map(a => abs(a._1 - a._2))
        val dy = y.init.zip(y.tail).map(a => abs(a._1 - a._2))

        val rugosity = new MutableDoubleGrid(x, y, Double.NaN)

        val points = Array.ofDim[Point3D[Double]](x.size, y.size)
        for (i <- 0 until x.size; j <- 0 until y.size) {
            points(i)(j) = Point3D(x(i), y(j), grid(i, j))
        }

        for (i <- 2 until (x.size - 1); j <- 2 until (y.size - 1)) {




            /*
                1 2 3
                4   5
                6 7 8
             */
            val p1 = points(i - 1)(j - 1)
            val p2 = points(i - 1)(j)
            val p3 = points(i - 1)(j + 1)
            val p4 = points(i)(j - 1)
            val p5 = points(i)(j + 1)
            val p6 = points(i + 1)(j - 1)
            val p7 = points(i + 1)(j)
            val p8 = points(i + 1)(j + 1)
            val center = points(i)(j)

            /*
                 p1-----p2-----p3
                 |  \ t1 | t2 / |
                 |   \   |   /  |
                 | t3 \  |  /t4 |
                 p4------c-----p5
                 | t5 /  | \ t6 |
                 |   /   |  \   |
                 |  / t7 | t8\  |
                 p6-----p7-----p8
             */
            val t1 = Triangle3D(p1, p2, center);
            val t2 = Triangle3D(p2, p3, center);
            val t3 = Triangle3D(p1, p4, center);
            val t4 = Triangle3D(p3, p5, center);
            val t5 = Triangle3D(p4, p6, center);
            val t6 = Triangle3D(p5, p8, center);
            val t7 = Triangle3D(p6, p7, center);
            val t8 = Triangle3D(p7, p8, center);

            val surfaceArea = area(t1) + area(t2) +  area(t3) + area(t4) + area(t5) + area(t6) +
                    area(t7) +  area(t8)

            val planarArea = dx(i) * dy(j)
            rugosity(i, j) = surfaceArea / planarArea

        }
        rugosity
    }

    private def area(t: Triangle3D[Double, Double]) = {
        val p = t.points
        val p1 = p(0)
        val p2 = p(1)
        val p3 = p(2)
        val a = p1.distance(p2) / 2
        val b = p2.distance(p3) / 2
        val c = p3.distance(p1) / 2
        val s = (a + b + c) / 2
        sqrt(s * (s - a) * ( s - b) * (s - c))
    }

    def main(args: Array[String]) {
        require(args.size == 2, "Use as: " + getClass.getSimpleName + " ingrid outgrid\n" +
                "\tArgs: ingrid: UTM ASC grid file\n" +
                "\t      outgrid: Target file to write an ASC grid")


        val ingrid = new File(args(0).trim)
        val outgrid = new File(args(1).trim)
        println("Reading " + ingrid)
        val bathymetry = ASCGridReader.read(ingrid)
        println("Calculating Rugosity")
        val rugosity = RugosityCalculator.apply(bathymetry)
        println("Writing " + outgrid)
        ASCGridWriter.write(outgrid, rugosity)

    }

}
