package controllers

import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.mvc.Result
import play.api.test.FakeRequest
import play.api.test.Helpers._
import support.{MockStorage, Storage}

import scala.concurrent.Future

class ExifSpec extends PlaySpec with OneAppPerSuite {
  implicit override lazy val app = new GuiceApplicationBuilder()
    .overrides(bind[Storage].to[MockStorage])
    .build()

  val controller = app.injector.instanceOf[Exif]
  val storage = app.injector.instanceOf[Storage].asInstanceOf[MockStorage]

  "show" should {
    "exif metadata is NOT found" in {
      val result: Future[Result] = controller.show(1)(FakeRequest())
      status(result) mustBe NOT_FOUND
    }

    "exif metadata is found" in {
      val metadata = """{"orientation":"1"}"""
      storage.set("69", metadata)

      val result: Future[Result] = controller.show(69)(FakeRequest())
      val bodyText: String = contentAsString(result)

      status(result) mustBe OK
      bodyText mustEqual metadata
    }
  }
}
