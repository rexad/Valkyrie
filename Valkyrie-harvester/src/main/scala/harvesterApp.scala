import Model.AJSearchPage
import Service.Legacy.AJ.AJDownloaderService
import Service.RMQ.RabbitMQPublisher
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import javax.inject.Inject
import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}

// In order to evaluate tasks, we'll need a Scheduler
import monix.execution.Scheduler.Implicits.global

class HarvesterApp (
                              val config: Config,
                              val rabbitMQPublisher: RabbitMQPublisher)(implicit ec: ExecutionContext) {

}

object HarvesterApp  extends App with LazyLogging {


  val searchPage = AJDownloaderService.downloadSearchPage()
  searchPage onComplete{
    case Success(searchPage) => if (searchPage.isDefined)
                                    {
                                      logger.info("download of search age was a success, url recorded are"
                                      + searchPage.get.articleHit.sections.length)
                                    }
    else logger.info("download of search age was a failure")
    case Failure(t) => logger.info("failed with :" + t.getMessage)
  }
}
