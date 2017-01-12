package services

import javax.inject.{Inject, Singleton}

import models._
import play.api.http.{HeaderNames, MimeTypes}
import play.api.libs.json.Json
import play.api.libs.ws.WSClient

import scala.concurrent.{ExecutionContext, Future}


@Singleton
case class TnaService @Inject()(ws: WSClient)(implicit executionContext: ExecutionContext) {

  private val BASE = "http://discovery.nationalarchives.gov.uk/DiscoveryWebAPI"

  def informationAsset(id: String): Future[InformationAsset] =
    ws.url(s"$BASE/InformationAssets/$id")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get().map(_.json.as[InformationAsset])

  def children(id: String, page: Int = 1): Future[Seq[InformationAsset]] =
    ws.url(s"$BASE/InformationAssets/$id/children/page/$page")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get().map(_.json.as[Seq[InformationAsset]])

  def records(query: String, page: Int): Future[Seq[Record]] =
    ws.url(s"$BASE/searchrecords/page/$page/query=$query")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get()
      .map(_.json.as[SearchResult].records)

  def fileAuthorities(query: String, page: Int): Future[Seq[FileAuthority]] =
    ws.url(s"$BASE/searchfileauthority/page/$page/query=$query")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get()
      .map(_.json.as[SearchResult].fileAuthorities)

  def archives(query: String, page: Int): Future[Seq[Archive]] =
    ws.url(s"$BASE/searcharchives/page/$page/query=$query")
      .withHeaders(HeaderNames.ACCEPT -> MimeTypes.JSON)
      .get()
      .map(_.json.as[SearchResult].archives)
}
