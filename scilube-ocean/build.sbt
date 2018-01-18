// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-ocean"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

libraryDependencies ++= Seq("org.mbari" % "mbarix4j" % "1.10.0", 
    "org.threeten" % "threetenbp" % "1.2" % "test" // Backport of JSR310 for testing on Java 7
    )


publishArtifact in Test := false

pomIncludeRepository := { _ => false }

