package com.jcreixell.exif.support.storage

import com.google.inject.Inject
import com.jcreixell.exif.support.configuration.Configuration
import com.redis.RedisClient

class RedisStorage @Inject() (config: Configuration) extends Storage {
  private val redis = new RedisClient(
    config.underlying().getString("redis.host"),
    config.underlying().getInt("redis.port")
  )

  override def set(key: String, value: String) = {
    redis.set(key, value)
  }

}
