import Model.AJSearchPage
import Service.Legacy.AJ.AJDownloaderService
import Service.Legacy.AJ.AJDownloaderService.logger
import Service.RMQ.RabbitMQPublisher
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import javax.inject.Inject
import java.io.PrintWriter

import scala.concurrent.ExecutionContext
import monix.execution.Scheduler.Implicits.global

class HarvesterApp (
                              val config: Config,
                              val rabbitMQPublisher: RabbitMQPublisher)(implicit ec: ExecutionContext) {

}

object HarvesterApp  extends App with LazyLogging {


  val searchPage = AJDownloaderService().map(_.map(_.map(e =>
{
  logger.debug(s"ahhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh")
    e.articleHit.sections.map(hit =>
      new PrintWriter(s"/tmp/articles/${hit.title}.html") { write("file contents"); close }

  )})))
}
