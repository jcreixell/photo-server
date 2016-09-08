package controllers

import com.google.inject.Inject
import play.api.mvc.{Action, Controller}
import support.Storage

class Exif @Inject() (storage: Storage) extends Controller {
  def show(id: Long) = Action { implicit request =>
    val json = storage.get(id.toString)

    json match {
      case Some(metadata) => Ok(metadata)
      case None => NotFound
    }
  }
}
