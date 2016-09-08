package support.queue

import com.google.inject._

@Singleton
class MockProducer extends QueueProducer {
  val queue = new scala.collection.mutable.Queue[String]

  override def produce(_queue: String, msg: String) = {
    queue += msg
  }

  def dequeue(): String = queue.dequeue()
}
