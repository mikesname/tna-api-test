package models

import play.api.libs.functional.syntax._
import play.api.libs.json.{Reads, __}

case class SearchResult(
  count: Int,
  records: Seq[Record] = Nil,
  fileAuthorities: Seq[FileAuthority] = Nil,
  archives: Seq[Archive] = Nil
)

object SearchResult {
  implicit val reads: Reads[SearchResult] = (
    (__ \ "ResultCount").read[Int] and
    (__ \ "SearchRecordsResults").read[Seq[Record]] and
    (__ \ "SearchFileAuthoritysResults").read[Seq[FileAuthority]] and
    (__ \ "SearchArchivesResults").read[Seq[Archive]]
  )(SearchResult.apply _)
}


