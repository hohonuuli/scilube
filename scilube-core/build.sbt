// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-core"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= Seq(
  "org.mbari" % "mbarix4j" % "2.0.4.jre11",
  "com.github.rwl" % "jtransforms" % "2.4.0", // FFT
  "org.typelevel" %% "spire" % "0.17.0-M1", // Complex numbers
  "org.apache.commons" % "commons-math3" % "3.6.1"
)

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}
