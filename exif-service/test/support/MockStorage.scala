package support

import com.google.inject.Singleton

import scala.collection.mutable

@Singleton
class MockStorage extends Storage {
  val storage = mutable.Map.empty[String, String]

  override def get(key: String): Option[String] = {
    storage.get(key)
  }

  def set(key: String, value: String) = storage += (key -> value)
}
