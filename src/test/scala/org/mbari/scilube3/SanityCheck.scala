package example

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class SanityCheck extends AnyFlatSpec with Matchers {
  "SanityCheck" should "run simple test" in {
    "hello" shouldEqual "hello"
  }
}
