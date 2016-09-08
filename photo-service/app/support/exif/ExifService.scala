package support.exif

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ExifClient])
trait ExifService {
  def fetch(id: Long): Option[String]
}
