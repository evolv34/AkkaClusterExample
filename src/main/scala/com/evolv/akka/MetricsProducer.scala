package com.evolv.akka

import java.net.InetAddress

import akka.actor.{Actor, ActorLogging, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.{Publish, Subscribe}

/**
  * Created by prashanth on 10/25/17.
  */

object MetricsProducer {

  case class Publisher(hostName: String)

  def props: Props = Props[MetricsProducer]
}

class MetricsProducer extends Actor with ActorLogging {
  val mediator = DistributedPubSub(context.system).mediator
  val topic = "test"

  def receive = {
    case MetricsProducer.Publisher(msg) => {
      mediator ! Publish(topic, MetricsSubscriber.Message(s"${InetAddress.getLocalHost.getHostAddress} :- sample message"))
    }
  }
}
