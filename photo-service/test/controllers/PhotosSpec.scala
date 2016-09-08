package controllers

import anorm.SqlParser._
import anorm._
import org.scalatest.BeforeAndAfterAll
import org.scalatestplus.play.{OneAppPerSuite, PlaySpec}
import play.api.db.Database
import play.api.db.evolutions.Evolutions
import play.api.inject.bind
import play.api.inject.guice.GuiceApplicationBuilder
import play.api.libs.Files
import play.api.libs.Files.TemporaryFile
import play.api.mvc.MultipartFormData.{BadPart, DataPart, FilePart}
import play.api.mvc.{MultipartFormData, Result}
import play.api.test.Helpers._
import play.api.test.{FakeHeaders, FakeRequest}
import support.exif.{ExifService, MockExifClient}
import support.queue.{MockProducer, QueueProducer}
import support.upload.{MockUploader, Uploader}

import scala.concurrent.Future

class PhotosSpec extends PlaySpec with OneAppPerSuite with BeforeAndAfterAll {
  implicit override lazy val app = new GuiceApplicationBuilder()
    .overrides(bind[Uploader].to[MockUploader])
    .overrides(bind[QueueProducer].to[MockProducer])
    .overrides(bind[ExifService].to[MockExifClient])
    .build()

  val db = app.injector.instanceOf[Database]
  val controller = app.injector.instanceOf[Photos]
  val exifService = app.injector.instanceOf[ExifService].asInstanceOf[MockExifClient]
  val queuePoducer = app.injector.instanceOf[QueueProducer].asInstanceOf[MockProducer]

  override def beforeAll() = {
    Evolutions.applyEvolutions(db)
  }


  "show" should {
    "photo is not found" in {
      val result: Future[Result] = controller.show(69)(FakeRequest())
      val bodyText: String = contentAsString(result)

      status(result) mustBe NOT_FOUND
    }

    "photo is found without EXIF data" in {
      exifService.setMustFail(true)

      val photoId = db.withConnection { implicit connection =>
        SQL(
          """INSERT INTO photos(id, user, description, url)
             VALUES ((SELECT NEXT VALUE FOR photo_id_seq), 'eyeem', 'cool photo', 'http://jcreixell.com/photo.jpg')"""
        ).executeInsert(scalar[Long].single)
      }

      val result: Future[Result] = controller.show(photoId)(FakeRequest())
      val bodyText: String = contentAsString(result)
      status(result) mustBe OK
      bodyText mustEqual s"""{"id":$photoId,"user":"eyeem","description":"cool photo","url":"http://jcreixell.com/photo.jpg"}"""
    }

    "photo is found with EXIF data" in {
      exifService.setMustFail(false)

      val photoId = db.withConnection { implicit connection =>
        SQL(
          """INSERT INTO photos(id, user, description, url)
             VALUES ((SELECT NEXT VALUE FOR photo_id_seq), 'eyeem', 'cool photo', 'http://jcreixell.com/photo.jpg')"""
        ).executeInsert(scalar[Long].single)
      }

      val result: Future[Result] = controller.show(photoId)(FakeRequest())
      val bodyText: String = contentAsString(result)
      status(result) mustBe OK
      bodyText mustEqual s"""{"id":$photoId,"user":"eyeem","description":"cool photo","url":"http://jcreixell.com/photo.jpg","exif":{}}"""
    }
  }

  "create" should {
    "upload successful" in {
      val tempFile = TemporaryFile("do_upload", "spec")

      val dataPart = DataPart("json", "{}")
      val filePart = FilePart("photo", "photo.jpg", None, tempFile)

      val files = Seq[FilePart[TemporaryFile]](filePart)
      val json = "{\"user\":\"jorge\",\"description\":\"cool\"}"

      val multipartBody = MultipartFormData(Map[String, Seq[String]]("json" -> Seq(json)), files, Seq[BadPart]())

      val request = FakeRequest[MultipartFormData[Files.TemporaryFile]]("POST", "/photo", FakeHeaders(), multipartBody)
      val result = controller.create(request)

      val photoId = db.withConnection { implicit connection =>
        SQL"SELECT id from photos WHERE user = 'jorge' limit 1".as(SqlParser.scalar[Int].singleOpt).get
      }

      val bodyText: String = contentAsString(result)

      status(result) mustBe OK
      bodyText mustEqual s"""{"id":$photoId,"user":"jorge","description":"cool","url":"http://my.photo/me.jpg"}"""
      queuePoducer.dequeue() mustEqual s"""{"id":$photoId,"user":"jorge","description":"cool","url":"http://my.photo/me.jpg"}"""
    }
  }
}
