package com.jcreixell.exif

import com.google.inject.Guice
import com.jcreixell.exif.support.storage.{MockStorage, Storage}
import com.jcreixell.exif.util.TestModule
import org.scalatest.FreeSpec

class ExifWorkerSpec extends FreeSpec {
  val injector = Guice.createInjector(new TestModule)
  val worker = injector.getInstance(classOf[ExifWorker])
  val storage = injector.getInstance(classOf[Storage]).asInstanceOf[MockStorage]

  "work" - {
    "should process and store exif data from photos in a message queue" in {
      worker.work

      assert(
        storage.get("1") == """{"dateTime":"2015:03:16 20:26:30","exposureTime":"1/30","fNumber":"2.2","orientation":"1"}"""
      )
    }
  }
}
