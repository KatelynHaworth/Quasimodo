name := "quasimodo"

organization := "au.net.hivemedia"

version := Process("git describe --tags --abbrev=5", baseDirectory.value).!!.replace("\n", "")

scalaVersion := "2.11.7"

resolvers += "JAnalyse Repository" at "http://www.janalyse.fr/repository/"

val akkaVersion = "2.4.1"

libraryDependencies ++= Seq(
  "com.typesafe.akka"             %% "akka-actor"                   % "2.4.1",
  "com.typesafe.akka"             %% "akka-slf4j"                   % "2.4.1",
  "commons-net"                    % "commons-net"                  % "3.3",
  "com.typesafe.scala-logging"    %% "scala-logging"                % "3.1.0",
  "ch.qos.logback"                 % "logback-classic"              % "1.1.2",
  "fr.janalyse"                   %% "janalyse-ssh"                 % "0.9.19",
  "me.legrange"                    % "mikrotik"                     % "2.2"
)

lazy val root = (project in file(".")).
  enablePlugins(BuildInfoPlugin).
  settings(
    buildInfoKeys := Seq[BuildInfoKey](name, version, scalaVersion, sbtVersion),
    buildInfoPackage := "au.net.hivemedia.quasimodo"
  )
