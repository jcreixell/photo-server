name := "Photo Server"

version := "0.1"
scalaVersion := "2.11.8"
organization := "com.jcreixell"

lazy val photoService = project.in(file("photo-service"))
lazy val exifWorker = project.in(file("exif-worker"))
lazy val exifService = project.in(file("exif-service"))
