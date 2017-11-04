package com.evolv.akka

import akka.actor.{Actor, Props}
import akka.cluster.pubsub.DistributedPubSub
import akka.cluster.pubsub.DistributedPubSubMediator.Subscribe

/**
  * Created by prashanth on 10/30/17.
  */
object MetricsSubscriber {
  case class Message(hostname: String)
  def props(): Props = Props[MetricsSubscriber]
}

class MetricsSubscriber extends Actor {
  val mediator = DistributedPubSub(context.system).mediator
  val topic = "test"
  mediator ! Subscribe(topic, self)

  def receive = {
    case MetricsSubscriber.Message(message) => println(s"message $message")
  }

}
