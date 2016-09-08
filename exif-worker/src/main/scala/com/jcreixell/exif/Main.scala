package com.jcreixell.exif

import com.google.inject.Guice

object Main {
  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector()
    val worker = injector.getInstance(classOf[ExifWorker])
    worker.work
  }
}
