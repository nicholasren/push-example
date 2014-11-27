package com.example.unfilter

import unfiltered.request._
import unfiltered.response._

import akka.actor.{Props, ActorSystem}

import scala.concurrent.duration._
import akka.util.Timeout
import akka.pattern.ask

import unfiltered.request.{Seg, POST}
import unfiltered.filter.Plan


import com.example.unfilter.repos.{NotificationSender, DeviceRepository}
import com.example.unfilter.repos.DeviceRepository._


import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global

import org.json4s.JsonAST.{JArray, JString}
import org.json4s.native.JsonMethods.{render, compact}
import org.json4s.JValue
import com.example.unfilter.repos.NotificationSender.Push

object PushApi extends Plan {

  implicit val system = ActorSystem("system")
  implicit val timeout = Timeout(10 seconds)
  val register = system.actorOf(Props[DeviceRepository])
  val sender = system.actorOf(Props[NotificationSender])

  def intent = {
    case req @ POST(Path(Seg("devices" :: Nil))) => {
      val response = (register ? Create(Body.string(req))).mapTo[String]

      Created ~> ResponseString(waitFor(response))
    }
    case GET(Path(Seg("devices" :: Nil))) => {
      val response = (register ? All).
        mapTo[List[String]].
        map { arns =>pretty(JArray(arns.map(JString(_)))) }

      Ok ~> ResponseString(waitFor(response))
    }

    case req @ POST(Path(Seg("notifications" :: Nil))) => {
      val response = (sender? Push(Body.string(req))).mapTo[String]

      Created ~> ResponseString(waitFor(response))
    }
  }


  def pretty(jValue: JValue) : String = compact(render(jValue))

  def waitFor[T] (future: Future[T]): T = {
   Await.result(future, timeout.duration)
  }
}
