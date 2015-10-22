scalaVersion := "2.11.7"

enablePlugins(JavaAppPackaging)
enablePlugins(DockerPlugin)
enablePlugins(GitVersioning)

net.virtualvoid.sbt.graph.Plugin.graphSettings

libraryDependencies ++= Seq(
  "org.specs2" %% "specs2-core" % "3.6.3" % "test",
  "com.github.scopt" %% "scopt" % "3.3.0",
  "com.google.guava" % "guava" % "18.0",
  "com.intenthq" %% "gander" % "1.2",
  "org.mapdb" % "mapdb" % "1.0.8"
)

import com.typesafe.sbt.packager.docker._
dockerBaseImage := "java:latest"

dockerCommands += ExecCmd("RUN", "bin/scala-based-on-a-true-story", "sample-db")