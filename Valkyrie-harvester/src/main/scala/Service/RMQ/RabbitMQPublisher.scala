package Service.RMQ

import com.rabbitmq.client.{AMQP, Channel}
import play.api.libs.json.{Json, Writes}

import scala.concurrent.{ExecutionContext, Future, blocking}

class RabbitMQPublisher(channel: Channel,
                        exchange: String)
                       (implicit ec: ExecutionContext) {

  val PERSISTENT_JSON = new AMQP.BasicProperties().builder()
    .contentEncoding("UTF-8")
    .contentType("application/json").build()

  def publish[T: Writes](msg: T, routingKey: String = ""): Future[Unit] = Future {
    val str = Json.toJson(msg).toString()
    blocking {
      channel.synchronized {
        channel.basicPublish("", exchange, false, PERSISTENT_JSON, str.getBytes("UTF-8"))
      }
    }
  }
}
