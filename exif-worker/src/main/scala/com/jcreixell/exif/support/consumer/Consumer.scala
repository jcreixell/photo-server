package com.jcreixell.exif.support.consumer

import com.google.inject.ImplementedBy

@ImplementedBy(classOf[RabbitmqConsumer])
trait Consumer {
  def consume(queue: String)(callback: (String => Unit)): Unit
}
