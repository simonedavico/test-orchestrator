name := """test-orchestrator"""

version := "dev"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

// scalaz-bintray resolver needed for specs2 library
resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
resolvers ++= Seq(
  "Sonatype repo"                    at "https://oss.sonatype.org/content/groups/scala-tools/",
  "Sonatype releases"                at "https://oss.sonatype.org/content/repositories/releases",
  "Sonatype snapshots"               at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype staging"                 at "http://oss.sonatype.org/content/repositories/staging",
  "Java.net Maven2 Repository"       at "http://download.java.net/maven/2/",
  "Twitter Repository"               at "http://maven.twttr.com",
  Resolver.bintrayRepo("websudos", "oss-releases")
)

val phantomVersion = "1.22.0"

libraryDependencies ++= Seq(
  ws, // Play's web services module
  specs2 % Test,
  "org.specs2" %% "specs2-matcher-extra" % "3.6" % Test,
  "org.easytesting" % "fest-assert" % "1.4" % Test,
  "org.scalatest" %% "scalatest" % "2.2.6" % Test,
  "com.typesafe.akka" %% "akka-testkit" % "2.4.7" % Test,
  "com.typesafe.akka" %% "akka-stream-testkit" % "2.4.7" % Test,
  "com.typesafe.akka" %% "akka-stream" % "2.4.7",
  "org.webjars" % "bootstrap" % "2.3.2",
  "org.webjars" % "flot" % "0.8.0"
)

routesGenerator := InjectedRoutesGenerator

fork in run := true


// Settings for assembly (plug-in to generate fat jar)
mainClass in assembly := Some("play.core.server.ProdServerStart")

fullClasspath in assembly += Attributed.blank(PlayKeys.playPackageAssets.value)

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  "benchflow-test-orchestrator.jar"
}

assemblyJarName in assembly := "benchflow-test-orchestrator.jar"

assemblyMergeStrategy in assembly := {
  case PathList("org", "apache", "commons", "logging", xs @ _*) => MergeStrategy.first
  case other => (assemblyMergeStrategy in assembly).value(other)
}
