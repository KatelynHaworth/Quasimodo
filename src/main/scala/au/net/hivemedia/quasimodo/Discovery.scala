package au.net.hivemedia.quasimodo

import akka.actor.{Cancellable, Actor, ActorLogging}
import me.legrange.mikrotik.ApiConnection

import au.net.hivemedia.quasimodo.AppMain._

import scala.concurrent.duration._
import scala.collection.JavaConversions._

/**
  * Connects to a dedicated MikroTik device to
  * collect information about neighbour devices
  * through the MikroTik API
  *
  * @author Liam Haworth
  */
class Discovery extends Actor with ActorLogging {

  import actorSystem.dispatcher

  /**
    * Defines the API connection to the MikroTik used for discovery
    */
  private var discoveryServer: ApiConnection = _

  /**
    * The cancellable scheduler that runs the discovery task on a set interval
    */
  private var discoveryScheduler: Cancellable = _

  /**
    * Receives and handles messages from the Akka letter box
    *
    * @return Receive
    */
  override def receive: Receive = {

    /**
      * Calls the start of the discovery task scheduler
      */
    case "Start"        =>
      if(discoveryScheduler != null && !discoveryScheduler.isCancelled)
        throw new RuntimeException("Discovery scheduler was already running!")

      val interval = config.getInt("discovery.interval")

      log.info(s"Starting scheduler for the discovery system to run every $interval seconds")

      discoveryScheduler = actorSystem.scheduler.schedule(
        30.seconds,
        Duration.create(interval, SECONDS),
        self,
        "Discover"
      )

    /**
      * Makes calls to the MikroTik API to receive neighbours list from
      * dedicated device
      */
    case "Discover"     => discover()
  }

  /**
    * Initializes the actor by opening the connection to the
    * MikroTik API on the dedicated device
    */
  override def preStart(): Unit = {
    log.info("Initializing API connection to discovery system")

    val host = config.getString("discovery.host")
    val user = config.getString("discovery.user")
    val pass = config.getString("discovery.pass")

    log.info(s"Connecting to device at $host")
    discoveryServer = ApiConnection.connect(host)

    log.info(s"Logging into device as $user")
    discoveryServer.login(user, pass)

    log.info("API connection to the discovery system was successful")
  }

  /**
    * Connects to the MikroTik API on the dedicated device
    * and collects the list of neighbour devices and parses the
    * information into a list of objects
    */
  private def discover(): Unit = {
    log.info("Checking in with discovery system")

    val response = discoveryServer.execute("/ip/neighbor/print")
    var deviceList = List.empty[MikroTikDevice]

    response.toList.foreach { device =>
      val mikroTikDevice = MikroTikDevice(
        device("mac-address"),
        device("address"),
        device("version"),
        device("platform"),
        device("board")
      )

      log.debug(s"Discovered device: $mikroTikDevice")

      deviceList = deviceList :+ mikroTikDevice
    }

    log.info(s"Discovered ${deviceList.size} device(s)")

    //TODO: Hand off this date some where to be stored and/or processed (LiamHaworth)
  }

  override def postStop(): Unit = {
    discoveryScheduler.cancel()

    log.info("Stopped the discovery scheduler")

    discoveryServer.close()

    log.info("Closed API connection to the discovery system")
  }
}

/**
  * Represents a MikroTik device discovered on a network
  *
  * @param macAddress The MAC address of the discovered device
  * @param address IP Address of the discoved device
  * @param version The version of the platform it is running
  * @param platform The platform the device is running
  * @param board The type of board the device uses
  */
case class MikroTikDevice(macAddress: String, address: String, version: String, platform: String, board: String)

