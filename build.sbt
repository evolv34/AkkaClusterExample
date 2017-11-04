import Dependencies._
import Resolvers._
import sbt.Keys._

lazy val root = project.in(file("."))
  .settings(
    name := "AkkaClusterExample",
    version := "1.0",
    scalaVersion := "2.12.1",
    resolvers := coreResolvers,
    libraryDependencies ++= dependencies
  )