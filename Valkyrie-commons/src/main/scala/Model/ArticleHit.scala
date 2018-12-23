package Model

import com.lucidchart.open.xtract.{XmlReader, __}
import com.lucidchart.open.xtract.XmlReader._

import cats.syntax.all._

case class Content(sections: Seq[ArticleHit])

object Content {
  implicit val reader: XmlReader[Content] = (__ \ "Hit").read(seq[ArticleHit]).map(apply _)
}


case class ArticleHit (
                        title: String,
                        title2: Option[String],
                        description: Option[String],
                        metatag_title: Option[String],
                        date: String,
                        contentTag: Option[String],
                        url: String
                      )
object ArticleHit{
  implicit val reader: XmlReader[ArticleHit] = (
    (__ \ "title_ac_edge").read[String],
    (__ \ "content_ac_edge").read[String].optional,
    (__ \ "metatag.description").read[String].optional,
  (__ \ "metatag.title").read[String].optional,
  (__ \ "metatag.publishdt").read[String],
    (__ \ "content_arabic").read[String].optional,
    (__ \ "url").read[String]
  ).mapN(apply _)
}