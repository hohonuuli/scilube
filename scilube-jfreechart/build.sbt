// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-jfreechart"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq(
    "org.jfree" % "jfreechart" % "1.0.19",
    "org.jfree" % "jcommon" % "1.0.23"
)


publishArtifact in Test := false

pomIncludeRepository := { _ => false }
  

