import sbt.Resolver

name := "load_test_redis"

version := "0.1"

scalaVersion := "2.12.4"

val akkaHttpVersion = "10.0.9"
libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-http-core",
  "com.typesafe.akka" %% "akka-http",
  "com.typesafe.akka" %% "akka-http-testkit"
).map(_ % akkaHttpVersion)

val circeVersion = "0.9.0-M2"
libraryDependencies ++= Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser"
).map(_ % circeVersion)

resolvers += Resolver.bintrayRepo("hseeberger", "maven")
libraryDependencies += "de.heikoseeberger" %% "akka-http-circe" % "1.14.0"

libraryDependencies += "com.github.etaty" %% "rediscala" % "1.8.0"

javaOptions ++= Seq(
  "-server",
  "-verbose:gc",
  "-XX:+UseG1GC"
)

