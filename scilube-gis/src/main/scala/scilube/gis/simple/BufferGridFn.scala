package scilube.gis.simple

import java.io.File
import scilube.grid.{DoubleArrayGrid}


object BufferGridFn {

    def main(args: Array[String]): Unit = {
        require(args.size == 3, "Use as: " + getClass.getSimpleName + " ingrid outgrid\n" +
                "\tArgs: ingrid: UTM ASC grid file\n" +
                "\t      outgrid: Target file to write an ASC grid\n" +
                "\t      bufferSize: The number of additional pixles (pers side) to dilate a pixel")


        val ingrid = new File(args(0).trim)
        val outgrid = new File(args(1).trim)
        val bufferSize = args(2).toInt
        println("Reading " + ingrid)
        val data = ASCGridReader.read(ingrid)
        println("Dilating grid")
        val rugosity = DoubleArrayGrid.dilateByCount(data, bufferSize)
        println("Writing " + outgrid)
        ASCGridWriter.write(outgrid, rugosity)

    }
    
}