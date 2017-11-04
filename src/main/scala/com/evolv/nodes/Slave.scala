package com.evolv.nodes

import java.net.InetAddress

import akka.actor.ActorSystem
import com.evolv.akka.MetricsProducer
import com.evolv.nodes.Master.actorSystem
import com.typesafe.config.ConfigFactory

object Slave {

  def getSeedNodes(): String = {
    val seedNodes = System.getProperty("com.evolv.cluster.seedNodes").split(",")
    if (seedNodes.length > 1) {
      seedNodes.reduce((node1, node2) => s"akka.tcp://${actorSystem}@$node1" + s",akka.tcp://${actorSystem}@$node2")
    } else {
      s"akka.tcp://${actorSystem}@${System.getProperty("com.evolv.cluster.seedNodes")}"
    }
  }

  def start() = {
    println(s"localhost ==> ${InetAddress.getLocalHost}")
    val clusterConfig = {
      ConfigFactory
        .parseString(s"akka.remote.netty.tcp.port=0")
        .withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.hostname=${InetAddress.getLocalHost.getHostAddress}"))
        .withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.bind-hostname=${InetAddress.getLocalHost.getHostAddress}"))
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [slave]"))
        .withFallback(ConfigFactory.parseString(s"""akka.cluster.seed-nodes = ["${getSeedNodes()}"]"""))
        .withFallback(ConfigFactory.load())
    }

    val system = ActorSystem("HelloSystem", clusterConfig)

    val monitorActor = system.actorOf(MetricsProducer.props, name = "printrouter-slave1")
  }
}
