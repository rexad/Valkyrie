package Service.RMQ

import java.net.URI

import Service.RMQ.ExchangeType.ExchangeType
import com.rabbitmq.client.{Connection, ConnectionFactory}
import com.rabbitmq.client.impl.ForgivingExceptionHandler
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.JavaConverters._
import monix.execution.Scheduler.Implicits.global


object RabbitMQFactory extends  LazyLogging {

  val config = ConfigFactory.load
  val delay: String = config.getString("amqp.reconnect-delay")
  val username: String = config.getString("amqp.username")
  val password: String = config.getString("amqp.password")
  val host: String = config.getString("amqp.host")

  logger.debug(s"AMQP: Connecting to ${host}...")
  val factory: ConnectionFactory = new ConnectionFactory()
  factory.setHost(host)
  factory.setExceptionHandler(new ForgivingExceptionHandler)
  factory.setAutomaticRecoveryEnabled(true)
  val conn: Connection = factory.newConnection()


  logger.debug("AMQP: Connected")


  def createPublisher(exchange: String, exchangeType: ExchangeType = ExchangeType.Fanout): RabbitMQPublisher = {
    val channel = RabbitMQFactory.conn.createChannel()
    channel.queueDeclare(exchange, true, false, false, null)
    new RabbitMQPublisher(channel, exchange)
  }
}