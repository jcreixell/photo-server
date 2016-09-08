package com.jcreixell.exif.support.configuration

import com.google.inject.ImplementedBy
import com.typesafe.config.Config

@ImplementedBy(classOf[Global])
trait Configuration {
  def underlying(): Config
}
