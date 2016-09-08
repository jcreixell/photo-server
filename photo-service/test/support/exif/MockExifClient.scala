package support.exif

import com.google.inject.Singleton

@Singleton
class MockExifClient extends ExifService {
  var mustFail = false

  override def fetch(id: Long): Option[String] = {
    if (mustFail) None
    else Some("{}")
  }

  def setMustFail(value: Boolean) = {
    mustFail = value
  }

}
