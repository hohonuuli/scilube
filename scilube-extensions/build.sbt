// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-extensions"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq(
    "org.mbari" % "mbarix4j" % "1.10.0",
    "org.apache.commons" % "commons-email" % "1.4"
)

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
  
