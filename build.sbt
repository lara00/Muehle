val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .enablePlugins(JacocoCoverallsPlugin)
  .settings(
    name := "Muehle",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0"
  )
