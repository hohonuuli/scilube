// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

name := "scilube-ocean"

scalacOptions += "-deprecation"
  
javacOptions ++= Seq("-target", "1.6", "-source","1.6")

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// Add Testing libs
libraryDependencies ++= Seq(
    "junit" % "junit" % "4.11" % "test",
    "org.scalatest" %% "scalatest" % "2.2.2" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test"
)

// Add SLF4J and Logback libs
libraryDependencies ++= {
  val slf4jVersion = "1.7.7"
  val logbackVersion = "1.1.2"
  Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion)
}

libraryDependencies += "org.mbari" % "mbarix4j" % "1.9.3-SNAPSHOT"

resolvers += Resolver.mavenLocal

publishTo <<= version { (v: String) =>
  val nexus = "https://oss.sonatype.org/"
  if (v.trim.endsWith("SNAPSHOT"))
    Some("snapshots" at nexus + "content/repositories/snapshots")
  else
    Some("releases"  at nexus + "service/local/staging/deploy/maven2")
}

publishArtifact in Test := false

pomIncludeRepository := { _ => false }

testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")