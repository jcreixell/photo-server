package support

import com.google.inject.Inject
import com.redis.RedisClient
import play.api.Configuration

class RedisStorage @Inject() (configuration: Configuration) extends Storage {
  private val redis = new RedisClient(
    configuration.underlying.getString("redis.host"),
    configuration.underlying.getInt("redis.port")
  )

  override def get(key: String): Option[String] = {
    redis.get(key)
  }
}
