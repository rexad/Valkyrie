import Service.RMQ.{ExchangeType, RabbitMQFactory, RabbitMQPublisher}
import com.rabbitmq.client.ConnectionFactory
import com.typesafe.config.{Config, ConfigFactory}
import com.typesafe.scalalogging.LazyLogging
import monix.execution.Scheduler.Implicits.global
import slick.jdbc.MySQLProfile.api._
import concurrent.{ExecutionContext, Future}

class HarvesterApp(
                    val config: Config,
                    val rabbitMQPublisher: RabbitMQPublisher)(implicit ec: ExecutionContext) {

}

object HarvesterApp extends App with LazyLogging {

  val mainThread = Thread.currentThread()
  val config = ConfigFactory.load
  val factory: ConnectionFactory = new ConnectionFactory()
  private lazy val exchangeName = config.getString("amqp.processor-queue")

  val publisher = RabbitMQFactory.createPublisher(exchangeName, ExchangeType.Fanout)
  val harvesterApp = new HarvesterApp(config, publisher)


}
