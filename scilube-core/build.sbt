// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-core"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= Seq(
  "org.mbari" % "mbarix4j" % "2.0.4.jre11",
  "com.github.rwl" % "jtransforms" % "2.4.0", // FFT
  "org.typelevel" % "spire_2.13.0-RC1" % "0.16.2", // Complex numbers
  "org.apache.commons" % "commons-math3" % "3.6.1"
)

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}
