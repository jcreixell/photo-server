package support.queue

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[RabbitmqProducer])
trait QueueProducer {
  def produce(queueName: String, msg: String)
}
