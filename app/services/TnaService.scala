package services

import javax.inject.{Inject, Singleton}

import models.{InformationAsset, SearchResult}
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}


@Singleton
case class TnaService @Inject()(ws: WSClient)(implicit executionContext: ExecutionContext) {

  private val BASE = "http://discovery.nationalarchives.gov.uk/DiscoveryWebAPI"

  def search(query: String, page: Int): Future[Seq[SearchResult]] =
    ws.url(s"$BASE/search/page/$page/query=$query")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get().map { r =>
        (r.json \ "SearchRecordsResults").as[Seq[SearchResult]]
    }

  def get(id: String): Future[InformationAsset] =
    ws.url(s"$BASE/InformationAssets/$id")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get().map(_.json.as[InformationAsset])

  def children(id: String, page: Int = 1): Future[Seq[InformationAsset]] =
    ws.url(s"$BASE/InformationAssets/$id/children/page/$page")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get().map(_.json.as[Seq[InformationAsset]])
}
