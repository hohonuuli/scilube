// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-extensions"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq(
  "org.mbari" % "mbarix4j" % "2.0.4.jre11",
  "org.apache.commons" % "commons-email" % "1.5"
)

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}
