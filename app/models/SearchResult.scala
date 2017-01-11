package models

import play.api.libs.json.{Reads, _}
import play.api.libs.functional.syntax._


object SearchResult {
  implicit val reads: Reads[SearchResult] = (
    (__ \ "Id").read[String] and
    (__ \ "Title").read[String] and
    (__ \ "Description").readNullable[String]
  )(SearchResult.apply _)
}

case class SearchResult(
  id: String,
  title: String,
  description: Option[String] = None
)