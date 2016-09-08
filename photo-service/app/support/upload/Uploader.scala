package support.upload

import java.io.File

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[S3Uploader])
trait Uploader {
  def upload(key: String, file: File): String
}
