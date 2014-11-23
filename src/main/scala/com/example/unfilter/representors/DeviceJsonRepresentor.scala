package com.example.unfilter.representors

import org.json4s.native.JsonMethods.{render, compact}
import org.json4s.JsonDSL._
import com.example.unfilter.models.Device

trait DeviceJsonRepresentor {
  self : Device =>

  def json: String = {
    compact(render(("token" -> token) ~ ("platform" -> platform)))
  }
}
