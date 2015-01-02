package scilube.net

import java.io.{ BufferedInputStream, File, FileInputStream, FileOutputStream }
import java.util.zip.{ ZipEntry, ZipOutputStream } 
import org.mbari.io.IOUtilities 

object Zip {
  def apply(out: File, files: Iterable[String]) = {
  
    val zip = new ZipOutputStream(new FileOutputStream(out))

    files.foreach { name =>
      zip.putNextEntry(new ZipEntry(name))
      val in = new BufferedInputStream(new FileInputStream(name))
      var b = in.read()
      while (b > -1) {
        zip.write(b)
        b = in.read()
      }
      in.close()
      zip.closeEntry()
    }
    zip.close()
  }
}