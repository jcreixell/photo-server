package com.jcreixell.exif.support.storage

import com.google.inject.Singleton

import scala.collection.mutable

@Singleton
class MockStorage extends Storage {
  val storage = mutable.Map.empty[String, String]

  override def set(key: String, value: String): Unit = {
    storage += (key -> value)
  }

  def get(key: String): String = {
    storage(key)
  }
}
