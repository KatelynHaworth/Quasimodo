package au.net.hivemedia.quasimodo

import akka.actor.SupervisorStrategy.{Stop, Escalate}
import akka.actor.{AllForOneStrategy, Props, ActorLogging, Actor}

/**
  * Created by Liam on 11/12/2015.
  */
class Supervisor extends Actor with ActorLogging {

  /**
    * Builds actors that are supervised by this actor
    *
    * @return Receive
    */
  override def receive: Receive = {
    case props: Props =>
      log.info(s"Building new child actor to be supervised from ${props.actorClass()}")
      sender ! context.actorOf(props)

    case (props: Props, name: String) =>
      log.info(s"Building new child actor to be supervised from ${props.actorClass()} called $name")
      sender ! context.actorOf(props, name)
  }

  /**
    * Handles exception thrown by children actors and handles
    * the exception on a type basis
    */
  override val supervisorStrategy = AllForOneStrategy() {
      case ex: RuntimeException =>
        log.error(s"Caught an exception that the service can't recover from, bailing", ex)
        sys.exit(1)
        Stop
    }
}
