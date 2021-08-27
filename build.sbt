
lazy val junitVersion = "4.12"
lazy val logbackVersion = "1.2.1"
lazy val scalatestVersion = "3.0.1"
lazy val slf4jVersion = "1.7.24"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val buildSettings = Seq(
    //ensimeIgnoreScalaMismatch in ThisBuild := true,
    organization := "scilube",
    scalaVersion := "2.12.14",
    crossScalaVersions := Seq("2.12.4", "2.11.7"),
    version := "2.0.5_2.jre8",
    githubOwner := "mbari-org",
    githubRepository := "maven",
    githubTokenSource := TokenSource.Environment("GITHUB_TOKEN"),
    publishConfiguration := publishConfiguration.value.withOverwrite(true)
)

lazy val consoleSettings = Seq(
    shellPrompt := { state =>
      val user = System.getProperty("user.name")
      user + "@" + Project.extract(state).currentRef.project + ":sbt> "
    },
    initialCommands in console :=
      """
        |import java.time.Instant
        |import java.util.UUID
    """.stripMargin
)

lazy val dependencySettings = Seq(
    libraryDependencies ++=  Seq(
          "ch.qos.logback" % "logback-classic"  % logbackVersion,
          "ch.qos.logback" % "logback-core"     % logbackVersion,
          "com.novocode"   % "junit-interface"  % "0.11" % "test",
          "junit"          % "junit"            % junitVersion % "test",
          "org.scalatest"  %% "scalatest"       % scalatestVersion % "test",
          "org.slf4j"      % "log4j-over-slf4j" % slf4jVersion,
          "org.slf4j"      % "slf4j-api"        % slf4jVersion,
          "org.scala-lang.modules" %% "scala-xml" % "2.0.1" % "test"
    ),
    resolvers ++= Seq(
        Resolver.mavenLocal,
        Resolver.sonatypeRepo("releases"),
        // Resolver.bintrayRepo("hohonuuli", "maven"),
        "com.springsource.repository.bundles.external" at "https://repository.springsource.com/maven/bundles/external",
        "imagej.snapshots" at "https://maven.imagej.net/content/repositories/snapshots",
        "imagej.releases" at "https://maven.imagej.net/content/repositories/releases",
        "geotoolkit.releases" at "https://maven.geotoolkit.org/", 
        "netcdf.releases" at "https://artifacts.unidata.ucar.edu/content/repositories/unidata-releases/"
    )
  )


lazy val optionSettings = Seq(
    scalacOptions ++= Seq(
        "-deprecation",
        "-encoding",
        "UTF-8", // yes, this is 2 args
        "-feature",
        "-language:existentials",
        "-language:higherKinds",
        "-language:implicitConversions",
        "-unchecked",
        "-Xlint",
        "-Yno-adapted-args",
        "-Xfuture"
    ),
    javacOptions ++= Seq("-target", "1.8", "-source", "1.8"),
    updateOptions := updateOptions.value.withCachedResolution(true),
    testOptions += Tests.Argument(TestFrameworks.JUnit, "-q", "-v")
)

lazy val deploySettings = Seq(
  licenses ++= Seq(("MIT", url("http://opensource.org/licenses/MIT"))),
  homepage := Some(url("https://hohonuuli.github.io/scilube/")),
  publishMavenStyle := true,
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
)

lazy val appSettings = buildSettings ++ consoleSettings ++ dependencySettings ++
  optionSettings ++ deploySettings


// DEFINE NESTED PROJECTS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
lazy val root = project.in(file("."))
    .settings(
      name := "scilube-parent"
    )
    .settings(appSettings)
    .aggregate(core, extensions, ocean, jfreechart, gis)
    .dependsOn(core, extensions, ocean)

lazy val core = (project in file("scilube-core"))
  .settings(appSettings)

lazy val extensions = (project in file("scilube-extensions"))
  .settings(appSettings)
  .dependsOn(core)

lazy val ocean = (project.in(file("scilube-ocean")))
  .settings(appSettings)
  .dependsOn(core)

lazy val jfreechart = (project in file("scilube-jfreechart"))
  .settings(appSettings)
  .dependsOn(core, extensions)

lazy val gis = (project in file("scilube-gis")) 
  .settings(appSettings)
  .dependsOn(core, extensions)

// Aliases
addCommandAlias("cleanall", ";clean;clean-files")


