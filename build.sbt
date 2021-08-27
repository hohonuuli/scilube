import Dependencies._

ThisBuild / scalaVersion := "2.13.5"
ThisBuild / version := "3.0.0"
ThisBuild / organization := "org.mbari.scilube"
ThisBuild / organizationName := "MBARI"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = (project in file(".")).settings(
  name := "scilube",
  libraryDependencies ++= Seq(
    commonsMail,
    commonsMath,
    jtransforms,
    mbarix4j,
    scalaTest % Test,
    spire
  ),
  publishTo := Some(Resolver.githubPackages("mbari-org", "maven")),
  resolvers ++= Seq(
    Resolver.githubPackages("mbari-org", "maven")
  )
)

// See https://www.scala-sbt.org/1.x/docs/Using-Sonatype.html for instructions on how to publish to Sonatype.
