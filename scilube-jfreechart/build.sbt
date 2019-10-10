// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-jfreechart"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq(
    "org.jfree" % "jfreechart" % "1.5.0",
    "org.jfree" % "jcommon" % "1.0.24"
)


publishArtifact in Test := false

pomIncludeRepository := { _ => false }
  

