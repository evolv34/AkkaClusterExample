package com.evolv.akka

import java.time.{ZoneOffset, ZonedDateTime}

import akka.actor.{Actor, ActorLogging, Props}

object PrintActor {

  case class Print(name: String)

  def props() = Props[PrintActor]
}

class PrintActor extends Actor with ActorLogging {

  import PrintActor._

  def receive = {
    case p: Print => {
      println(s"${ZonedDateTime.now(ZoneOffset.UTC)} --> hello from print actor ${self.path} ---> ${p.name}")
    }
  }
}
