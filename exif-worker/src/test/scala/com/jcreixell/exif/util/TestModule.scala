package com.jcreixell.exif.util

import com.google.inject.AbstractModule
import com.jcreixell.exif.support.consumer.{Consumer, MockConsumer}
import com.jcreixell.exif.support.download.{Downloader, MockDownloader}
import com.jcreixell.exif.support.storage.{MockStorage, Storage}

class TestModule extends AbstractModule {
  def configure() = {
    bind(classOf[Storage]).to(classOf[MockStorage])
    bind(classOf[Downloader]).to(classOf[MockDownloader])
    bind(classOf[Consumer]).to(classOf[MockConsumer])
  }
}
