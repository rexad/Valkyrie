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

  RabbitMQFactory.createPublisher("test")
}
