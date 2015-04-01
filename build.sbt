// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
organization in ThisBuild := "scilube"

name := "scilube-parent"

version in ThisBuild := "2.0-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.4"

crossScalaVersions in ThisBuild := Seq("2.11.4", "2.10.4")

scalacOptions in ThisBuild ++= Seq("-deprecation", "-feature", "-unchecked")

javacOptions in ThisBuild ++= Seq("-target", "1.6", "-source","1.6")

// DEFINE NESTED PROJECTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
lazy val root = project.in(file("."))
    .aggregate(core, extensions, ocean, imglib2, jfreechart, gis)
    .dependsOn(core, extensions, ocean)

lazy val core = project in file("scilube-core")

lazy val extensions = project in file("scilube-extensions")  dependsOn(core)

lazy val ocean = project.in(file("scilube-ocean")).dependsOn(core)

lazy val imglib2 = project in file("scilube-imglib2") dependsOn(core, extensions)

lazy val jfreechart = project in file("scilube-jfreechart") dependsOn(core, extensions)

lazy val gis = project in file("scilube-gis") dependsOn(core, extensions)

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Add Testing libs
libraryDependencies in ThisBuild ++= Seq(
    "junit" % "junit" % "4.11" % "test",
    "org.scalatest" %% "scalatest" % "2.2.2" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test"
)

// Add SLF4J and Logback libs
libraryDependencies in ThisBuild ++= {
  val slf4jVersion = "1.7.7"
  val logbackVersion = "1.1.2"
  Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion)
}

updateOptions in ThisBuild := updateOptions.value.withCachedResolution(true) 

publishMavenStyle in ThisBuild := true

publishTo in ThisBuild := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

resolvers in ThisBuild ++= Seq(Resolver.mavenLocal,
  "com.springsource.repository.bundles.external" at "http://repository.springsource.com/maven/bundles/external",
  "imagej.snapshots" at "http://maven.imagej.net/content/repositories/snapshots",
  "imagej.releases" at "http://maven.imagej.net/content/repositories/releases")

// OTHER SETTINGS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Adds commands for dependency reporting
net.virtualvoid.sbt.graph.Plugin.graphSettings

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state => 
  val user = System.getProperty("user.name")
  "\n" + user + "@" + Project.extract(state).currentRef.project + "\nsbt> " 
}

// Add this setting to your project to generate a version report (See ExtendedBuild.scala too.)
// Use as 'sbt versionReport' or 'sbt version-report'
versionReport <<= (externalDependencyClasspath in Compile, streams) map {
  (cp: Seq[Attributed[File]], streams) =>
    val report = cp.map {
      attributed =>
        attributed.get(Keys.moduleID.key) match {
          case Some(moduleId) => "%40s %20s %10s %10s".format(
            moduleId.organization,
            moduleId.name,
            moduleId.revision,
            moduleId.configurations.getOrElse("")
          )
          case None =>
            // unmanaged JAR, just
            attributed.data.getAbsolutePath
        }
    }.sortBy(a => a.trim.split("\\s+").map(_.toUpperCase).take(2).last).mkString("\n")
    streams.log.info(report)
    report
}

// For sbt-pack
packAutoSettings

// fork a new JVM for run and test:run
fork := true

// Aliases
addCommandAlias("cleanall", ";clean;clean-files")

pomExtra in ThisBuild := (
  <url>http://scalanlp.org/</url>
  <licenses>
    <license>
      <name>Apache 2</name>
      <url>http://www.apache.org/licenses/LICENSE-2.0.html</url>
      <distribution>repo</distribution>
    </license>
  </licenses>
  <scm>
    <url>git@github.com:hohonuuli/scilube.git</url>
    <connection>scm:git:git@github.com:hohonuuli/scilube.git</connection>
  </scm>
  <developers>
    <developer>
      <id>hohonuuli</id>
      <name>Brian Schlining</name>
      <url>http://www.mbari.org/staff/brian</url>
    </developer>
  </developers>)

testOptions in ThisBuild += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
