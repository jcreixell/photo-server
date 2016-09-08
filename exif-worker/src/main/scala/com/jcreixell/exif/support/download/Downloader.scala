package com.jcreixell.exif.support.download

import com.google.inject.ImplementedBy

import scala.tools.nsc.interpreter.InputStream

@ImplementedBy(classOf[ImageDownloader])
trait Downloader {
  def getAsInputStream(imageUrl: String,
                       connectTimeout: Int = 5000,
                       readTimeout: Int = 5000,
                       requestMethod: String = "GET"): InputStream
}
