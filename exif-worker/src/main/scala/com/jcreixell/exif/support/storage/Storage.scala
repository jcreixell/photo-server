package com.jcreixell.exif.support.storage

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[RedisStorage])
trait Storage {
  def set(key: String, value: String): Unit
}
