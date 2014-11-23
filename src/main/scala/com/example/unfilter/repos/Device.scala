package com.example.unfilter.repos

import org.json4s.native.JsonMethods.{render, compact}
import org.json4s.JsonDSL._

case class Device(val token: String, val platform: String) {
  def json: String = {
    compact(render(("token" -> token) ~ ("platform" -> platform)))
  }
}
