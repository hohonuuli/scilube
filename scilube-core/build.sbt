// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-core"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= Seq(
    "org.mbari" % "mbarix4j" % "1.10.0",
    "com.github.rwl" % "jtransforms" % "2.4.0", // FFT
    "org.spire-math" %% "spire" % "0.11.0", // Complex numbers
    "org.apache.commons" % "commons-math3" % "3.5"
)

// publishTo <<= version { (v: String) =>
//   val nexus = "https://oss.sonatype.org/"
//   if (v.trim.endsWith("SNAPSHOT"))
//     Some("snapshots" at nexus + "content/repositories/snapshots")
//   else
//     Some("releases"  at nexus + "service/local/staging/deploy/maven2")
// }

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
  

