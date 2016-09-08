package com.jcreixell.exif.support.configuration

import com.typesafe.config.{Config, ConfigFactory}

class Global extends Configuration {
  override def underlying(): Config = ConfigFactory.load()
}
