// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-gis"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= {
  val geotoolkitVersion = "3.21.1"
  val derbyVersion = "10.13.1.1"
  Seq(
    "org.geotoolkit" % "geotk-referencing" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-epsg" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-referencing" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverage" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverageio" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverageio-netcdf" % geotoolkitVersion excludeAll (ExclusionRule(
      organization = "edu.ucar"
    )),
    "org.apache.derby" % "derbyclient" % derbyVersion,
    "org.apache.derby" % "derbynet" % derbyVersion,
    "edu.ucar" % "netcdf" % "4.3.23",
    "org.mbari" % "mbarix4j" % "2.0.4.jre11"
  )
}

publishArtifact in Test := false

pomIncludeRepository := { _ =>
  false
}
