package com.jcreixell.exif.support.consumer

import com.google.inject.Inject
import com.jcreixell.exif.support.configuration.Configuration
import com.rabbitmq.client.{AMQP, ConnectionFactory, DefaultConsumer, Envelope}

class RabbitmqConsumer @Inject() (config: Configuration) extends Consumer {

  private val factory = new ConnectionFactory()
  factory.setHost(config.underlying().getString("rabbitmq.host"))

  private val connection = factory.newConnection()
  private val channel = connection.createChannel()

  override def consume(queue: String)(callback: (String => Unit)): Unit = {
    val consumer = new DefaultConsumer(channel) {
      override protected def handleDelivery(consumerTag: String,
                                            envelope: Envelope,
                                            properties: AMQP.BasicProperties,
                                            body: Array[Byte]): Unit = {
        val msg = new String(body, "UTF-8")
        callback(msg)
      }
    }

    channel.queueDeclare(queue, false, false, false, null)
    channel.basicConsume(queue, true, consumer)
  }
}
