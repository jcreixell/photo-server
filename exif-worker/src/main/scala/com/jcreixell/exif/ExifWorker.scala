package com.jcreixell.exif

import com.google.inject.Inject
import com.jcreixell.exif.support.configuration.Configuration
import com.jcreixell.exif.support.consumer.Consumer
import com.jcreixell.exif.support.download.Downloader
import com.jcreixell.exif.support.metadata.ExifExtractor
import com.jcreixell.exif.support.serialization.Serializer
import com.jcreixell.exif.support.storage.Storage

class ExifWorker @Inject() (config: Configuration,
                            storage: Storage,
                            downloader: Downloader,
                            metadataExtractor: ExifExtractor,
                            serializer: Serializer,
                            consumer: Consumer) {

  def work = {
    consumer.consume(config.underlying().getString("rabbitmq.queue"))((msg: String) => {
      System.out.println(s"Consuming: $msg")

      val photoData = serializer.deserialize(msg)

      val photo = downloader.getAsInputStream(photoData("url"))
      val metadata = metadataExtractor.extractMetadata(photo)

      val json = serializer.serialize(metadata)

      storage.set(photoData("id"), json)
    })
  }

}
