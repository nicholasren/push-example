package com.example.unfilter


object Server extends App {
  unfiltered.jetty.Server.local(8080).plan(PushApi).run()
}
