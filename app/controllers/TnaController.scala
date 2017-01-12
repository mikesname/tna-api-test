package controllers

import javax.inject._

import play.api.mvc._
import services.TnaService

import scala.concurrent.ExecutionContext


@Singleton
class TnaController @Inject()(tnaService: TnaService)(implicit executionContext: ExecutionContext) extends Controller {

  def records(query: String = "", page: Int = 1): Action[AnyContent] = Action.async { implicit request =>
    tnaService.records(query, page).map { results =>
      Ok(views.html.records(query, page, routes.TnaController.records(query, page), results))
    }
  }

  def fileAuthorities(query: String = "", page: Int = 1): Action[AnyContent] = Action.async { implicit request =>
    tnaService.fileAuthorities(query, page).map { results =>
      Ok(views.html.fileAuthorities(query, page, routes.TnaController.fileAuthorities(query, page), results))
    }
  }

  def archives(query: String = "", page: Int = 1): Action[AnyContent] = Action.async { implicit request =>
    tnaService.archives(query, page).map { results =>
      Ok(views.html.archives(query, page, routes.TnaController.archives(query, page), results))
    }
  }

  def item(id: String, page: Int = 1): Action[AnyContent] = Action.async { implicit request =>
    val itemF = tnaService.informationAsset(id)
    val childrenF = tnaService.children(id, page)

    for (item <- itemF; children <- childrenF) yield {
      Ok(views.html.informationAsset(item, children, page))
    }
  }
}
