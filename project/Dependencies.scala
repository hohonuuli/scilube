import sbt._

object Dependencies {
  lazy val commonsMail = "org.apache.commons" % "commons-email" % "1.5"
  lazy val commonsMath = "org.apache.commons" % "commons-math3" % "3.6.1"
  lazy val jtransforms = "com.github.rwl"     % "jtransforms"   % "2.4.0" // FFT
  lazy val mbarix4j = "org.mbari"             % "mbarix4j"      % "2.0.5.jre11"
  lazy val scalaTest = "org.scalatest"       %% "scalatest"     % "3.2.2"
  lazy val spire = "org.typelevel"           %% "spire"         % "0.17.0"
}
