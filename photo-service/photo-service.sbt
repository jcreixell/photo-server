name := "Photo Service"

version := "0.1"
scalaVersion := "2.11.8"
organization := "com.jcreixell"

val awsJavaSdkV = "1.11.30"
val ampqV = "3.6.5"
val anormV = "2.5.0"
val json4sV = "3.4.0"

enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  "com.amazonaws" % "aws-java-sdk" % awsJavaSdkV,
  "com.rabbitmq" % "amqp-client" % ampqV,
  jdbc,
  evolutions,
  "com.typesafe.play" %% "anorm" % anormV,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
)
