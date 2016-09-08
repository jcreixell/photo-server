package support.exif

import com.google.inject.Inject

import scala.io.Source
import scala.util.{Failure, Success, Try}

class ExifClient @Inject() (configuration: play.api.Configuration) extends ExifService {
  private val host = configuration.underlying.getString("exif.host")
  private val port = configuration.underlying.getString("exif.port")

  override def fetch(id: Long): Option[String] = {
    val response = Try(Source.fromURL(s"http://$host:$port/exif/$id").mkString)
    response match {
      case Success(exifData) => Some(exifData)
      case Failure(_) => None
    }
  }

}
