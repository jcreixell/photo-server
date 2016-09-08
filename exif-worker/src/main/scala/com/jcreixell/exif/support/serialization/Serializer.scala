package com.jcreixell.exif.support.serialization

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[JsonSerializer])
trait Serializer {
  def serialize(hash: Map[String, String]): String

  def deserialize(string: String): Map[String, String]
}
