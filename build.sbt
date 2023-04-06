val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .settings(
    name := "Muehle",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test"
    // Test / scalaSource := baseDirectory.value / "test" / "scala"
  )