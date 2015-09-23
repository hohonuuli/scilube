// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-imglib2"

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
libraryDependencies ++= {
  val bioformatsVersion = "5.0.0-beta1"
  val imglibVersion = "2.0.0-beta-26"
  Seq("net.imglib2" % "imglib2" % imglibVersion,
    "net.imglib2" % "imglib2-algorithms" % imglibVersion,
    "net.imglib2" % "imglib2-algorithms-gpl" % imglibVersion,
    "net.imglib2" % "imglib2-ij" % imglibVersion,
    "net.imglib2" % "imglib2-ops" % imglibVersion,
    "net.imglib2" % "imglib2-realtransform" % imglibVersion,
    "loci" % "bio-formats" % bioformatsVersion,
    "loci" % "ome-io" % bioformatsVersion,
    "loci" % "loci-legacy" % bioformatsVersion,
    "javax.media.jai" % "com.springsource.javax.media.jai.core" % "1.1.3",
    "org.mbari" % "mbarix4j" % "1.9.3-SNAPSHOT")
}
  

// publishTo <<= version { (v: String) =>
//   val nexus = "https://oss.sonatype.org/"
//   if (v.trim.endsWith("SNAPSHOT"))
//     Some("snapshots" at nexus + "content/repositories/snapshots")
//   else
//     Some("releases"  at nexus + "service/local/staging/deploy/maven2")
// }

publishArtifact in Test := false

pomIncludeRepository := { _ => false }
