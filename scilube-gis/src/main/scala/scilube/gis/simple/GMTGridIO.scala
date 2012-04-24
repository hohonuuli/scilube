package scilube.gis.simple


import java.io.File
import ucar.nc2.dataset.NetcdfDataset
import ucar.nc2.NetcdfFileWriteable
import ucar.ma2.{DataType, Array => NCArray}
import java.util.{Date}
import org.slf4j.LoggerFactory

import scala.Array
import scilube.grid.{MutableDoubleGrid, Grid}


/**
 * Reads and writes GMT/GRD files. Here's an example CDL
 * {{{
 * netcdf MontereyCanyon_UTM50 {
dimensions:
	x = 5083 ;
	y = 4067 ;
variables:
	double x(x) ;
		x:long_name = "Easting (meters)" ;
		x:actual_range = 422985.277598382, 677085.277598382 ;
	double y(y) ;
		y:long_name = "Northing (meters)" ;
		y:actual_range = 3915514.90677777, 4118814.90677777 ;
	float z(y, x) ;
		z:long_name = "Topography (m)" ;
		z:_FillValue = NaNf ;
		z:actual_range = -4032.4375, -16.2922439575195 ;

// global attributes:
		:Conventions = "COARDS/CF-1.0" ;
		:title = "Topography Grid" ;
		:description = "\n",
			"\tProjection: UTM10N\n",
			"\tGrid created by mbgrid\n",
			"\tMB-system Version 5.1.3beta1865\n",
			"\tRun by <mappingauv> on <pumice.shore.mbari.org> at <Thu Jul 15 18:07:13 2010>" ;
		:GMT_version = "4.5.2" ;
}
 *
 * }}}
 */
class GMTGridIO {

    private val log = LoggerFactory.getLogger(getClass)

    /**
     * Read the GMT/GRD file and convert to Grid
     */
    def read(location: String): Grid[Double, Double, Double] =  {
        val netcdf = NetcdfDataset.openDataset(location)
        val xVar = netcdf.findVariable("x")
        val x = xVar.read().copyTo1DJavaArray().asInstanceOf[Array[Double]]
        val yVar = netcdf.findVariable("y")
        val y = yVar.read().copyTo1DJavaArray().asInstanceOf[Array[Double]]
        val grid = new MutableDoubleGrid[Double, Double](x, y, Double.NaN)
        val zVar = netcdf.findVariable("z")
        val z = zVar.read().copyToNDJavaArray.asInstanceOf[Array[Array[Float]]]
        for (i <- 0 until x.size; j <- 0 until y.size) {
            grid.z(i, j, z(j)(i).asInstanceOf[Double])
        }
        grid
    }

    def write[A <: Grid[Double, Double, Double]](file: File, data: A, zName: String) {
        log.debug("Writing " + file)
        val nc = NetcdfFileWriteable.createNew(file.getCanonicalPath)
        nc.addGlobalAttribute("Conventions", "COARDS/CF-1.0")
        nc.addGlobalAttribute("description", "\tProjection: UTM10N\n" +
                "\tGrid created by: GMTGridIO.scala")
        nc.addGlobalAttribute("CreationDate", (new Date()).toString)

        val xDim = nc.addDimension("x", data.x.size)
        val yDim = nc.addDimension("y", data.y.size)
        val xVar = nc.addVariable("x", DataType.DOUBLE, Array(xDim))
        nc.addVariableAttribute("x", "long_name", "Easting (meters)")
        val xRange = Array(data.x(0), data.x(data.x.size - 1))
        nc.addVariableAttribute("x", "actual_range", toDoubleNCArray(xRange))
        val yVar = nc.addVariable("y", DataType.DOUBLE, Array(yDim))
        nc.addVariableAttribute("y", "long_name", "Northing (meters)")
        val yRange = Array(data.y(0), data.y(data.y.size - 1))
        nc.addVariableAttribute("y", "actual_range", toDoubleNCArray(yRange))
        val zVar = nc.addVariable("z", DataType.FLOAT, Array(yDim, xDim))
        nc.addVariableAttribute("z", "long_name", zName)
        val zMin = data.min
        val zMax = data.max
        val zRange = Array(zMin, zMax)
        nc.addVariableAttribute("z", "actual_range", toFloatNCArray(zRange))
        nc.create()

        nc.write("x", NCArray.factory(data.x.toArray))
        nc.write("y", NCArray.factory(data.y.toArray))
        nc.write("z", toNCArray(data))
        nc.close()
    }

    private def toNCArray(grid: Grid[Double, Double, Double]) = {
        val ncArray = NCArray.factory(DataType.DOUBLE, Array(grid.y.size, grid.x.size))
        val index = ncArray.getIndex
        for {xi <- 0 until grid.x.size
            yi <- 0 until grid.y.size} {
            index.set(yi, xi)
            ncArray.setDouble(index, grid(xi, yi))
        }
        ncArray
    }

    private def toDoubleNCArray(array: Array[Double]) = {
        val ncArray = NCArray.factory(DataType.DOUBLE, Array(array.size))
        val index = ncArray.getIndex
        for (i <- 0 until array.size) {
            index.set(i)
            ncArray.setDouble(index, array(i))
        }
        ncArray
    }

    private def toFloatNCArray(array: Array[Double]) = {
        val ncArray = NCArray.factory(DataType.FLOAT, Array(array.size))
        val index = ncArray.getIndex
        for (i <- 0 until array.size) {
            index.set(i)
            ncArray.setFloat(index, array(i).asInstanceOf[Float])
        }
        ncArray
    }


}