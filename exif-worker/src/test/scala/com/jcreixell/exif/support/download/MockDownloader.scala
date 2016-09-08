package com.jcreixell.exif.support.download

import java.io.InputStream

class MockDownloader extends Downloader {
  override def getAsInputStream(imageUrl: String,
                                connectTimeout: Int,
                                readTimeout: Int,
                                requestMethod: String): InputStream = {

    getClass.getResourceAsStream(imageUrl)
  }

}
