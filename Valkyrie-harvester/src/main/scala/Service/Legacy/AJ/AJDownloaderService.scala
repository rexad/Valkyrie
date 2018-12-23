package Service.Legacy

import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging

class AJDownloaderService (cfg: Config) extends LazyLogging {
  private val Default_Search_Page = cfg.getString("Pages.Search.AJ")

  val 

}
