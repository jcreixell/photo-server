package support.upload

import java.io.File

import com.google.inject.Singleton

@Singleton
class MockUploader extends Uploader {

  override def upload(key: String, file: File): String = "http://my.photo/me.jpg"

}
