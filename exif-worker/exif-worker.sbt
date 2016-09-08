name := "Exif Worker"

version := "0.1"
scalaVersion := "2.11.8"
organization := "com.jcreixell"

val awsJavaSdkV = "1.11.30"
val ampqV = "3.6.5"
val redisV = "3.2"
val json4sV = "3.2.11"
val metadataExtractorV = "2.9.1"
val guiceV = "4.1.0"
val configV = "1.3.0"
val scalatestV = "3.0.0"

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" % awsJavaSdkV,
  "com.rabbitmq" % "amqp-client" % ampqV,
  "net.debasishg" %% "redisclient" % redisV,
  "org.json4s" %% "json4s-jackson" % json4sV,
  "com.drewnoakes" % "metadata-extractor" % metadataExtractorV,
  "com.google.inject" % "guice" % guiceV,
  "com.typesafe" % "config" % configV,
  "org.scalatest" %% "scalatest" % scalatestV % "test"
)
