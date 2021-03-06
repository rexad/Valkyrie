package Service.Legacy.AJ

import java.io.PrintWriter

import Model.{AJSearchPage, ArabicEnum}
import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import com.lucidchart.open.xtract.XmlReader
import com.typesafe.config.ConfigFactory
import com.typesafe.scalalogging.LazyLogging
import play.api.libs.ws.WSClientConfig
import play.api.libs.ws.ahc.{AhcWSClientConfig, StandaloneAhcWSClient}
import utils.Helper
import monix.execution.Scheduler.Implicits.global

import scala.concurrent.Future
import scala.xml.XML

object AJDownloaderService extends LazyLogging {
  implicit val system: ActorSystem = ActorSystem("test")
  implicit val materializer: ActorMaterializer = ActorMaterializer()
  val cfg = ConfigFactory.load
  private val Default_Search_Page = cfg.getString("pages.Search.AJ.AJUrl")
  val playWsConfig = AhcWSClientConfig(WSClientConfig())
  val playWs = StandaloneAhcWSClient(playWsConfig)
  val hitPerPage = 1000
  val index_start = 0

  val headers: Seq[(String, String)] = Seq[(String, String)](
    "Host" -> "search1.aljazeera.net",
    "Connection" -> "keep-alive",
    "Accept" -> "json",
    "Origin" -> "http://www.aljazeera.net",
    "Referer" -> "http://www.aljazeera.net/home/search?q=%d8%ae%d8%b3%d8%a7%d8%a6%d8%b1%d9%87%d8%a7&cat=news&datefrom=&dateto=&exact=",
    "Accept-Encoding" -> "gzip, deflate",
    "Accept-Language" -> "en-US,en;q=0.9",

    "User-Agent" -> "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_12_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/67.0.3396.99 Safari/537.36"
  )

  def apply(): Future[List[Either[Exception, AJSearchPage]]] = {

    for {
      numberArticles <- downloadSearchPage(Default_Search_Page, hitPerPage, index_start, ArabicEnum.letter1.toString)
      r <- {
        val numberOfPages = if (numberArticles.right.get.total%hitPerPage == 0 )
        {numberArticles.right.get.total%hitPerPage}
        else (numberArticles.right.get.total/hitPerPage) + 1

        Future.sequence((1 to 2).foldLeft(List[Future[Either[Exception, AJSearchPage]]]()) { (acc, el) =>
          downloadSearchPage(Default_Search_Page, hitPerPage, el * hitPerPage, ArabicEnum.letter1.toString) :: acc

        })

      }

    } yield
      {
        r
      }

  }

  def downloadSearchPage(searchPage: String, hitPerPage: Int, index_start: Int, key: String): Future[Either[Exception, AJSearchPage]] = {
    // for (e <- ArabicEnum.values) {
    val url = generateURl(searchPage, hitPerPage, index_start, key)
    // logger.debug(s"url generated for aljazeera with hitPerPage $hitPerPage and index starting at $index_start is: $url")
    Helper.downloadUrl(url, Some(headers)).map {
      case success => parseSearchPage(success)
    }
    // }
  }

  def generateURl(template: String, numberOfArticle: Int, index: Int, query: String): String =
    template.replace("%{searchEntry}", query)
      .replace("%{numberOfArticle}", numberOfArticle.toString)
      .replace("%{index}", index.toString)


  def parseSearchPage(body: String): Either[Exception, AJSearchPage] = {
    // logger.info(s"************* paaaaaaaaaarsing \n")
    // logger.info(s"\n\n\n$body \n\n\n")
    val r = scala.util.Random


    new PrintWriter(s"/tmp/raw/${r.nextLong()}.html") { write(body); close }

    try {
      Right(XmlReader.of[AJSearchPage]
      .read(XML.loadString(body))
      .getOrElse()
      .asInstanceOf[AJSearchPage])

    }
    catch {
      case e: Throwable =>
        logger.error("failed to parse***********************************")
        Left(new Exception)
    }

  }

  def getArticle(ajArticle: AJSearchPage): Unit = {
    ajArticle.articleHit.sections.map(art => Helper.downloadUrl(art.url))
  }

}
