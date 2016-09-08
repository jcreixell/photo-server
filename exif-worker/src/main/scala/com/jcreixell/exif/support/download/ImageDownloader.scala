package com.jcreixell.exif.support.download

import java.io.InputStream
import java.net.{HttpURLConnection, URL}

class ImageDownloader extends Downloader {
  def getAsInputStream(imageUrl: String,
                       connectTimeout: Int = 5000,
                       readTimeout: Int = 5000,
                       requestMethod: String = "GET"): InputStream = {

    val url = new URL(imageUrl)
    val connection = url.openConnection.asInstanceOf[HttpURLConnection]

    HttpURLConnection.setFollowRedirects(false)
    connection.setConnectTimeout(connectTimeout)
    connection.setReadTimeout(readTimeout)
    connection.setRequestMethod(requestMethod)

    connection.connect
    connection.getInputStream
  }

}
