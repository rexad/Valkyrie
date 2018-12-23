package Model

import com.lucidchart.open.xtract.{XmlReader, __}
import com.lucidchart.open.xtract.XmlReader._

import cats.syntax.all._

case class AJSearchPage (
                          hitsPerPage: Int,
                          total: Int,
                          start: Option[Int],
                          end: Option[Int],
                          articleHit: Content
                        )
object AJSearchPage{
  implicit val reader: XmlReader[AJSearchPage] = (
    (__ \ "HitsCount" \ "hitsPerPage").read[Int],
    (__ \ "HitsCount" \ "total").read[Int],
    (__ \ "HitsCount" \ "start").read[Int].optional,
    (__ \ "HitsCount" \ "end").read[Int].optional,
    (__ \ "Results" \ "Hits").read[Content]
  ).mapN(apply _)
}


