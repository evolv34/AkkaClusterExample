package com.evolv.akka

import akka.actor.{Actor, ActorLogging, ActorRef, Props}
import akka.routing.ConsistentHashingRouter.ConsistentHashable
import PrintActor._

object HelloActor {

  case class Hello(name: String) extends ConsistentHashable with Serializable {
    override def consistentHashKey: String = name
  }

  def props(print: ActorRef) = Props(classOf[HelloActor], print)
}

class HelloActor(print: ActorRef) extends Actor with ActorLogging {

  import HelloActor._

  def receive: Receive = {
    case hello: Hello => print ! Print(hello.name)
  }
}
