name := """playground"""

version := "0.0.1-SNAPSHOT"

scalaVersion := "2.11.1"

libraryDependencies ++= Seq(
  jdbc,
  javaJpa,
  cache,
  "postgresql" % "postgresql" % "9.1-901-1.jdbc4",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.6.Final"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava)
