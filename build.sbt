val scala3Version = "3.2.2"

lazy val root = project
  .in(file("."))
  .enablePlugins(JacocoCoverallsPlugin)
  .settings(
    name := "Muehle",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "2.0.1",
    libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.15" % "test",
    libraryDependencies += "org.scala-lang.modules" %% "scala-swing" % "3.0.0",
    libraryDependencies += "org.scalafx" %% "scalafx" % "20.0.0-R31",
    libraryDependencies += "net.codingwell" %% "scala-guice" % "7.0.0",
    libraryDependencies += "com.typesafe.play" %% "play-json" % "2.10.0-RC5",
    libraryDependencies += "org.json4s" %% "json4s-native" % "4.0.3",
    libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.8",
    libraryDependencies += "org.yaml" % "snakeyaml" % "1.28",
    libraryDependencies += "org.apache.avro" % "avro" % "1.10.2",
    libraryDependencies += "org.scalactic" %% "scalactic" % "3.2.15",
    libraryDependencies += "org.slf4j" % "slf4j-api" % "1.7.32",
    libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.6"
)

assembly / assemblyMergeStrategy := {
  case PathList("META-INF", _*) => MergeStrategy.discard
  case _                        => MergeStrategy.first
}
assembly / target := baseDirectory.value
assembly / test := (assembly / test).dependsOn(test in Test).value
