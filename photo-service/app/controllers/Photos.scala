package controllers

import com.google.inject.Inject
import models.Photo
import play.api.Configuration
import play.api.db.Database
import play.api.libs.functional.syntax._
import play.api.libs.json._
import play.api.mvc.{Action, Controller}
import support.exif.ExifService
import support.queue.QueueProducer
import support.upload.Uploader

class Photos @Inject() (configuration: Configuration,
                        db: Database,
                        exifService: ExifService,
                        queueProducer: QueueProducer,
                        uploader: Uploader) extends Controller {

  implicit val photoDataReads: Reads[PhotoData] = (
    (JsPath \ "user").read[String] and
      (JsPath \ "description").read[String]
    ) (PhotoData.apply _)
  implicit val photoWrites = new Writes[Photo] {
    def writes(photo: Photo) = Json.obj(
      "id" -> photo.id,
      "user" -> photo.user,
      "description" -> photo.description,
      "url" -> photo.url
    )
  }

  def show(id: Long) = Action { implicit request =>
    Photo.findById(db, id) match {
      case Some(photo) => {
        val photoJson = Json.toJson(photo)

        exifService.fetch(id) match {
          case Some(exifResponse) => {
            val exifJson = Json.obj("exif" -> Json.parse(exifResponse))

            Ok(photoJson.as[JsObject].deepMerge(exifJson.as[JsObject]))
          }
          case None => Ok(photoJson)
        }
      }
      case None => NotFound
    }
  }

  def create = Action(parse.multipartFormData) { request =>
    val json = Json.parse(request.body.dataParts.get("json").map(_.head).getOrElse("{}"))
    val url = request.body.file("photo").map(photo => uploader.upload(s"photos/${photo.filename}", photo.ref.file))

    json.validate[PhotoData] match {
      case s: JsSuccess[PhotoData] => {
        val photoData = s.get

        val photo = Photo(None, photoData.user, photoData.description, url)
        val photoWithId = Photo.insert(db, photo)

        val response = Json.toJson(photoWithId)
        queueProducer.produce("uploaded_photo", response.toString)

        Ok(response)
      }
      case s: JsError => InternalServerError
    }
  }

  case class PhotoData(user: String, description: String)
}
