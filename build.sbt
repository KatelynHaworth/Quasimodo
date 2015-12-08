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


/**
  * sbt-native-packager global settings
  */
enablePlugins(JavaServerAppPackaging)

maintainer in Linux := "Hive Media Productions <development@hivemedia.net.au>"

packageSummary in Linux := "Discovery and deployment service for MikroTik devices"

packageDescription := "Discovery and deployment service for MikroTik devices"


/**
  * sbt-native-package rpm settings
  */
rpmVendor := "Hive Media Productions"

rpmLicense := Option("(c) 2015, Hive Media Productions")

version in Rpm := version.value.substring(0, version.value.indexOf("-"))

rpmRelease := version.value.substring(version.value.indexOf("-") + 1).replace("-", "_")

rpmRequirements := Seq("java-1.8.0-openjdk")


//TODO: Implement deb settings