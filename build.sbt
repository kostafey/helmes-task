name := """helmes-task"""
organization := "kostafey"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.13.8"

libraryDependencies += guice

libraryDependencies += "com.h2database" % "h2" % "1.4.200"
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.7"
libraryDependencies += "com.google.code.gson" % "gson" % "1.7.1"
libraryDependencies += "org.hibernate" % "hibernate-core" % "5.6.7.Final"
libraryDependencies += "c3p0" % "c3p0" % "0.9.1.2"
libraryDependencies += "org.hibernate" % "hibernate-c3p0" % "5.6.7.Final"
