package com.example.unfilter.repos

import akka.actor.Actor
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods.parse
import com.example.unfilter.repos.DeviceRepository.{All, Create}
import com.amazonaws.services.sns.model.{ListEndpointsByPlatformApplicationRequest, CreatePlatformEndpointRequest}

import scala.collection.JavaConversions._
import com.example.unfilter.models.Device

class DeviceRepository extends Actor {
  implicit val formats = DefaultFormats

  val applicationArn = "arn:aws:sns:ap-southeast-2:369407384105:app/APNS_SANDBOX/xiaojun_push"

  override def receive: Actor.Receive = {
    case Create(json) => {
      val device = parse(json).extract[Device]
      val req = new CreatePlatformEndpointRequest

      req.setCustomUserData("")
      req.setToken(device.token)
      req.setPlatformApplicationArn(applicationArn)

      sender ! sns.createPlatformEndpoint(req).getEndpointArn
    }

    case All => {
      val req = new ListEndpointsByPlatformApplicationRequest
      req.setPlatformApplicationArn(applicationArn)

      val arns = sns.listEndpointsByPlatformApplication(req).
        getEndpoints.
        map(_.getEndpointArn).toList

      sender ! arns
    }
  }
}

object DeviceRepository {

  sealed trait Action
  case class Create(json: String) extends Action
  case object All extends Action

}

