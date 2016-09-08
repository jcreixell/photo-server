package com.jcreixell.exif.support.serialization

import org.json4s.NoTypeHints
import org.json4s.jackson.Serialization

class JsonSerializer extends Serializer {
  override def serialize(hash: Map[String, String]): String = {
    implicit val formats = Serialization.formats(NoTypeHints)
    Serialization.write(hash)
  }

  override def deserialize(string: String): Map[String, String] = {
    implicit val formats = Serialization.formats(NoTypeHints)
    Serialization.read[Map[String, String]](string)
  }

}
