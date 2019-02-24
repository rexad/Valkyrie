package algebra

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url

/** Used to represent the behavior of downloading legacy/existing articles from different news provider,
  * not a real-time downloader
  *
  * @tparam F Return type of the methods, could be futures or IO or anything
  * @tparam T The container of the articles downloaded
  */
trait LegacyDownloader[F[_], T[_]] {

  /** Articles count is important when looking for the number of legacy articles existing
    *
    * @return the number of articles existing
    */
  def getArticlesCount : F[Int]

  /** All news outlets have a search page and usually is the default way to access the legacy articles
    *
    * @tparam A the type in which we represent the search page
    * @return return the url of the search page containing the articles to download
    */
  def generateSearchPageURL[A](): F[A]

  /**
    *
    * @param url url of the search page
    * @param header headers to include in the html
    * @param decoder parse the search page into a more user friendly type
    * @param parseHeader most header should a pair of key, name
    * @tparam A scala representation of the searchpage could be a case class or any other type
    * @tparam B the header that needs to be sent
    * @tparam C some news requires a body, usually for post request
    * @return return the search page containing the legacy articles
    */
  def downloadSearchPage[A, B, C](url: String Refined Url, header: B, body: C)
                              (implicit decoder : Decoder[A],
                               parseHeader: B => List[(String, String)]): F[A]

  /** Download articles
    *
    * @param url url of the articles
    * @param startIndex from where to start the download usually by date or order it appears
    * @param endIndex to which articles to stop downloading usually by date or order it appears
    * @param header header usually are needed
    * @param body body might be needed
    * @param parseHeader parse the header in to friendly type
    * @param decoder decode the returned data to the desired type
    * @tparam A model to represent the article downloaded
    * @tparam B header if it exists
    * @tparam C body if it exists
    * @return a list of articles
    */
  def downloadArticles[A, B, C](url: String Refined Url, startIndex: Int, endIndex: Int, header: B, body: C)
                               (implicit decoder: Decoder[A],
                                parseHeader: B => List[(String, String)]): T[F[A]]
}
