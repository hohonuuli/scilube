// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-gis"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= {
  val geotoolkitVersion = "3.21"
  val derbyVersion = "10.10.2.0"
  Seq("org.geotoolkit" % "geotk-referencing" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-epsg" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-referencing" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverage" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverageio" % geotoolkitVersion,
    "org.geotoolkit" % "geotk-coverageio-netcdf" % geotoolkitVersion excludeAll(ExclusionRule(organization = "edu.ucar")),
    "org.apache.derby" % "derbyclient" % derbyVersion,
    "org.apache.derby" % "derbynet" % derbyVersion,
    "edu.ucar" % "netcdf" % "4.3.7",
    "org.mbari" % "mbarix4j" % "1.9.3-SNAPSHOT")
}
 

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

