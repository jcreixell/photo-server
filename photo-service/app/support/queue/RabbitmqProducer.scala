package support.queue

import com.google.inject.Inject
import com.rabbitmq.client.ConnectionFactory

class RabbitmqProducer @Inject() (configuration: play.api.Configuration) extends QueueProducer {
  private val factory = new ConnectionFactory()
  factory.setHost(configuration.underlying.getString("rabbitmq.host"))

  private val connection = factory.newConnection()
  private val channel = connection.createChannel()

  override def produce(queue: String, msg: String) = {
    channel.queueDeclare(queue, false, false, false, null)
    channel.basicPublish("", queue, null, msg.getBytes())
  }
}
