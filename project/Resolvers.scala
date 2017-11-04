import sbt._

/**
  * Created by prashanth on 11/4/17.
  */

object Resolvers {

  val typeSafeRepository = "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

  val coreResolvers = Seq(typeSafeRepository)
}
