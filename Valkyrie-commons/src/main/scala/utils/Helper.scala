package utils

import Model.failedSPDownloadException
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.typesafe.scalalogging.LazyLogging

import scala.concurrent.Future
import play.api.libs.ws.WSClientConfig
import play.api.libs.ws.ahc.{AhcWSClientConfig, StandaloneAhcWSClient}
import monix.execution.Scheduler.Implicits.global
object Helper extends LazyLogging {


  implicit val system: ActorSystem = ActorSystem("test")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val playWsConfig = AhcWSClientConfig(WSClientConfig())
  val playWs = StandaloneAhcWSClient(playWsConfig)

  def downloadUrl(url: String, headers: Option[Seq[(String, String)]] = None): Future[String] = {
    val playUrl = playWs.url(url)
    if(headers.isDefined) playUrl.withHttpHeaders(headers.get: _*)

    playUrl.get().map({
      case e if e.status == 200 =>
        logger.debug(s"The url: $url was a successfuly downloaded")
        e.body

      case failed =>
        logger.error(s"Download was a failure with code" + failed.status)
        throw failedSPDownloadException(failed.status.toString)
    })
  }
}
