name := """helmes-task"""
organization := "kostafey"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies += guice

libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.7"
