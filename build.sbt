import Dependencies._

lazy val scala3 = "3.1.0"
lazy val scala213 = "2.13.7"

lazy val supportedScalaVersions = List(scala3, scala213)


ThisBuild / scalaVersion := scala3
ThisBuild / version := "3.0.1"
ThisBuild / organization := "org.mbari.scilube"
ThisBuild / organizationName := "MBARI"

Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val root = (project in file(".")).settings(
  name := "scilube",
  crossScalaVersions := supportedScalaVersions,
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
