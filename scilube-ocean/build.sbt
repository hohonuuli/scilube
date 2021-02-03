// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-ocean"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq(
  "org.mbari" % "mbarix4j" % "2.0.4.jre11",
  "org.threeten" % "threetenbp" % "1.5.0" % "test" // Backport of JSR310 for testing on Java 7
)

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}
