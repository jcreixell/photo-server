package com.jcreixell.exif.support.consumer

class MockConsumer extends Consumer {
  def consume(queue: String)(callback: (String => Unit)) = {
    callback(
      """{"id":1,"user":"jorge","description":"cool","url":"/058f9186ae41e8972982d39638a8966f6eab2a8a-1426534100.jpg"}"""
    )
  }
}
