package support

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[RedisStorage])
trait Storage {
  def get(key: String): Option[String]
}
