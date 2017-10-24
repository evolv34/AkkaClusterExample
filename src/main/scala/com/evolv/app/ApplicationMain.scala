package com.evolv.app

import com.evolv.nodes.{Master, NodeType, Slave}

object ApplicationMain extends App {
  println("Welcome to akka clustering application")

  val nodeType = System.getProperty("com.evolv.cluster.nodeType")

  NodeType.withName(nodeType) match {
    case NodeType.MASTER => Master.start
    case NodeType.SLAVE => Slave.start
  }
}
