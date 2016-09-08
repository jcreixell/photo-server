package com.jcreixell.exif.support.metadata

import java.io.InputStream

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[ExifExtractor])
trait Extractor {
  def extractMetadata(inputStream: InputStream): Map[String, String]
}
