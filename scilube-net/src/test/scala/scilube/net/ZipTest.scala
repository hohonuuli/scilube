package scilube.net

import java.io.{BufferedWriter, File, FileWriter}
import java.util.Date
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.FunSpec
import org.scalatest.matchers.ShouldMatchers

@RunWith(classOf[JUnitRunner])
class ZipTest extends FunSpec with ShouldMatchers {

  describe("A Zip object") {
    it("should zip a file up") {
      val fs = Seq("target/file1.txt", "target/file2.txt")

      fs.foreach { f =>
        val out = new BufferedWriter(new FileWriter(f))
        out.write("Some text written on " + new Date)
        out.close()
      }

      val targetFile = new File("target", getClass.getSimpleName + "-test-output.zip")
      Zip(targetFile, fs)
    }
  }
  
}