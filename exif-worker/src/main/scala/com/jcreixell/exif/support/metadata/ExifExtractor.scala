package com.jcreixell.exif.support.metadata

import java.io.InputStream

import com.drew.imaging.ImageMetadataReader
import com.drew.metadata.exif.{ExifDirectoryBase, ExifIFD0Directory, ExifSubIFDDirectory}


class ExifExtractor extends Extractor {

  override def extractMetadata(inputStream: InputStream): Map[String, String] = {
    val metadata = ImageMetadataReader.readMetadata(inputStream)

    val subIFDDirectory = metadata.getFirstDirectoryOfType(classOf[ExifIFD0Directory])
    val iFD0Directory = metadata.getFirstDirectoryOfType(classOf[ExifSubIFDDirectory])

    Map(
      "dateTime" -> Option(iFD0Directory.getString(ExifDirectoryBase.TAG_DATETIME_ORIGINAL)).getOrElse(""),
      "exposureTime" -> Option(iFD0Directory.getString(ExifDirectoryBase.TAG_EXPOSURE_TIME)).getOrElse(""),
      "fNumber" -> Option(iFD0Directory.getString(ExifDirectoryBase.TAG_FNUMBER)).getOrElse(""),
      "orientation" -> Option(subIFDDirectory.getString(ExifDirectoryBase.TAG_ORIENTATION)).getOrElse("")
    )
  }

}
