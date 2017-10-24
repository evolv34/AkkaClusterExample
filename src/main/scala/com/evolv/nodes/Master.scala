package com.evolv.nodes

import akka.actor.ActorSystem
import akka.cluster.Cluster
import akka.cluster.routing.{ClusterRouterPool, ClusterRouterPoolSettings}
import akka.routing.RoundRobinPool
import com.evolv.akka.HelloActor.Hello
import com.evolv.akka.{HelloActor, PrintActor}
import com.typesafe.config.ConfigFactory
import scala.concurrent.duration._

/**
  * Created by prashanth on 10/17/17.
  */
object Master {

  val actorSystem = "HelloSystem"

  def getSeedNodes(): String = {
    val seedNodes = System.getProperty("com.evolv.cluster.seedNodes").split(",")
    if (seedNodes.length > 1) {
      seedNodes.reduce((node1, node2) => s"akka.tcp://${actorSystem}@$node1" + s",akka.tcp://${actorSystem}@$node2")
    } else {
      s"akka.tcp://${actorSystem}@${System.getProperty("com.evolv.cluster.seedNodes")}"
    }
  }

  def start(): Unit = {
    val clusterConfig = {
      ConfigFactory
        .parseString(s"akka.remote.netty.tcp.port=8080")
        .withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.hostname=${System.getProperty("com.evolv.cluster.hostname")}"))
        .withFallback(ConfigFactory.parseString(s"akka.remote.netty.tcp.bind-hostname=${System.getProperty("com.evolv.cluster.hostname")}"))
        .withFallback(ConfigFactory.parseString("akka.cluster.roles = [master]"))
        .withFallback(ConfigFactory.parseString(s"""akka.cluster.seed-nodes = ["${getSeedNodes()}"]"""))
        .withFallback(ConfigFactory.load())
    }

    val system = ActorSystem(actorSystem, clusterConfig)

    Cluster(system).registerOnMemberUp({
      println("Member is up")

      val printActor = system.actorOf(ClusterRouterPool(RoundRobinPool(0), ClusterRouterPoolSettings(
        totalInstances = 10, maxInstancesPerNode = 3,
        allowLocalRoutees = true)).props(PrintActor.props()), name = "printrouter")

      val helloActor = system.actorOf(ClusterRouterPool(RoundRobinPool(0), ClusterRouterPoolSettings(
        totalInstances = 10, maxInstancesPerNode = 3,
        allowLocalRoutees = true)).props(HelloActor.props(printActor)), name = "hellorouter")

      import system.dispatcher

      system.scheduler.schedule(1.seconds, 1.seconds) {
        helloActor ! Hello("Sam")
        helloActor ! Hello("Allen")
      }
    })


    Cluster(system).registerOnMemberRemoved({
      println("Member is down")
    })
  }

}
