import sbt._

/**
  * Created by prashanth on 11/4/17.
  */
object Dependencies {
  val dependencies = Seq(
    "com.typesafe.akka" %% "akka-actor"         %   "2.5.6",
    "com.typesafe.akka" %% "akka-cluster"       %   "2.5.6",
    "com.typesafe.akka" %% "akka-cluster-tools" %   "2.5.6")
}
