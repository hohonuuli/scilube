// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-core"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= Seq(
    "org.mbari" % "mbarix4j" % "1.10.0",
    "com.github.rwl" % "jtransforms" % "2.4.0", // FFT
    "org.spire-math" %% "spire" % "0.13.0", // Complex numbers
    "org.apache.commons" % "commons-math3" % "3.6.1"
)

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
  

