name := "EXIF Service"

version := "0.1"
scalaVersion := "2.11.8"
organization := "com.jcreixell"

val redisV = "3.2"

enablePlugins(PlayScala)
PlayKeys.devSettings := Seq("play.server.http.port" -> "9001")

libraryDependencies ++= Seq(
  "net.debasishg" %% "redisclient" % redisV,
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.0" % "test"
)
