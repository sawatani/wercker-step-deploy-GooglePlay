name := "Deploy-GooglePlay"

version := "0.0.1"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "com.google.apis" % "google-api-services-androidpublisher" % "v2-rev16-1.19.1",
  "org.scalaz" %% "scalaz-core" % "7.1.1"
)
