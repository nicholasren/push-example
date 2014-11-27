package com.example.unfilter.repos

import akka.actor.Actor
import com.example.unfilter.repos.NotificationSender.Push
import com.example.unfilter.models.Notification

import org.json4s.native.JsonMethods.parse
import com.amazonaws.services.sns.model.PublishRequest
import org.json4s.DefaultFormats

object NotificationSender {
  case class Push(json: String)

}
class NotificationSender extends Actor {
  implicit val formats = DefaultFormats

  override def receive: Actor.Receive = {
    case Push(json) => {
      val notification = parse(json).extract[Notification]

      val req = new PublishRequest

      req.setMessage(notification.message)
      req.setSubject(notification.subject)
      req.setTargetArn(notification.target)

      println(sns)

      sns.publish(req).getMessageId
    }
  }
}
