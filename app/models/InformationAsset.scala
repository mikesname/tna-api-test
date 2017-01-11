package models

import play.api.libs.json.{JsObject, Reads, __}
import play.api.libs.functional.syntax._


case class InformationAsset(
  id: String,
  parent: Option[String] = None,
  data: JsObject
)

object InformationAsset {
  implicit val reads: Reads[InformationAsset] = (
    (__ \ "IAID").read[String] and
    (__ \ "ParentIAID").readNullable[String] and
    __.read[JsObject]
  )(InformationAsset.apply _)
}
