// PROJECT PROPERTIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
organization in ThisBuild := "scilube"

name := "scilube-parent"

version in ThisBuild := "2.0.3"

licenses in ThisBuild ++= Seq(("MIT", url("http://opensource.org/licenses/MIT")))

scalaVersion in ThisBuild := "2.12.0"

crossScalaVersions in ThisBuild := Seq("2.12.0", "2.11.9", "2.10.4")

homepage := Some(url("https://hohonuuli.github.io/scilube/"))

// https://tpolecat.github.io/2014/04/11/scalac-flags.html
scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-encoding", "UTF-8",       // yes, this is 2 args
  "-feature",
  "-language:existentials",
  "-language:higherKinds",
  "-language:implicitConversions",
  "-language:postfixOps",
  "-unchecked",
  //"-Xfatal-warnings",
  "-Xlint",
  "-Yno-adapted-args",
  //"-Ywarn-numeric-widen",
  //"-Ywarn-value-discard",
  "-Xfuture")

javacOptions in ThisBuild ++= Seq("-target", "1.8", "-source","1.8")

// DEFINE NESTED PROJECTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
lazy val root = project.in(file("."))
    .aggregate(core, extensions, ocean, jfreechart, gis)
    .dependsOn(core, extensions, ocean)

lazy val core = project in file("scilube-core")

lazy val extensions = project in file("scilube-extensions")  dependsOn(core)

lazy val ocean = project.in(file("scilube-ocean")).dependsOn(core)

lazy val jfreechart = project in file("scilube-jfreechart") dependsOn(core, extensions)

lazy val gis = project in file("scilube-gis") dependsOn(core, extensions)

// DEPENDENCIES ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
// Add Testing libs
libraryDependencies in ThisBuild ++= Seq(
    "junit" % "junit" % "4.12" % "test",
    "org.scalatest" %% "scalatest" % "2.2.5" % "test",
    "com.novocode" % "junit-interface" % "0.11" % "test"
)

// Add SLF4J and Logback libs
libraryDependencies in ThisBuild ++= {
  val slf4jVersion = "1.7.12"
  val logbackVersion = "1.1.3"
  Seq(
    "org.slf4j" % "slf4j-api" % slf4jVersion,
    "org.slf4j" % "log4j-over-slf4j" % slf4jVersion,
    "ch.qos.logback" % "logback-classic" % logbackVersion,
    "ch.qos.logback" % "logback-core" % logbackVersion)
}

updateOptions in ThisBuild := updateOptions.value.withCachedResolution(true)

publishMavenStyle in ThisBuild := true

//publishTo in ThisBuild := Some(Resolver.file("file",  new File(Path.userHome.absolutePath+"/.m2/repository")))

resolvers in ThisBuild ++= Seq(Resolver.mavenLocal,
  Resolver.bintrayRepo("hohonuuli", "maven"),
  "com.springsource.repository.bundles.external" at "http://repository.springsource.com/maven/bundles/external",
  "imagej.snapshots" at "http://maven.imagej.net/content/repositories/snapshots",
  "imagej.releases" at "http://maven.imagej.net/content/repositories/releases")

// OTHER SETTINGS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

// todolist tags to search fro
todosTags := Set("TODO", "FIXME", "HACK", "WTF\\?")

// set the prompt (for this build) to include the project id.
shellPrompt in ThisBuild := { state =>
  val user = System.getProperty("user.name")
  "\n" + user + "@" + Project.extract(state).currentRef.project + "\nsbt> "
}

lazy val versionReport = TaskKey[String]("version-report")

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

// -- SCALARIFORM
// Format code on save with scalariform
import scalariform.formatter.preferences._
import com.typesafe.sbt.SbtScalariform

SbtScalariform.scalariformSettings

SbtScalariform.ScalariformKeys.preferences := SbtScalariform.ScalariformKeys.preferences.value
  .setPreference(IndentSpaces, 2)
  .setPreference(PlaceScaladocAsterisksBeneathSecondAsterisk, false)
  .setPreference(DoubleIndentClassDeclaration, true)

// fork a new JVM for run and test:run
fork := true

// Aliases
addCommandAlias("cleanall", ";clean;clean-files")

testOptions in ThisBuild += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")

pomExtra := (
  <scm>
    <url>git@github.com:hohonuuli/scilube.git</url>
    <connection>scm:git:git@github.com:hohonuuli/scilube.git</connection>
  </scm>
  <developers>
    <developer>
      <id>hohonuuli</id>
      <name>Brian Schlining</name>
      <url>http://www.mbari.org/staff/brian/</url>
    </developer>
  </developers>)
