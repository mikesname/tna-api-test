package controllers

import javax.inject._

import play.api.mvc._
import services.TnaService

import scala.concurrent.ExecutionContext


@Singleton
class TnaController @Inject()(tnaService: TnaService)(implicit executionContext: ExecutionContext) extends Controller {

  def index(query: String = "", page: Int = 1) = Action.async { implicit request =>
    tnaService.search(query, page).map { results =>
      Ok(views.html.index(query, page, routes.TnaController.index(query, page), results))
    }
  }

  def item(id: String, page: Int = 1) = Action.async { implicit request =>
    val itemF = tnaService.get(id)
    val childrenF = tnaService.children(id, page)

    for (item <- itemF; children <- childrenF) yield {
      Ok(views.html.item(item, children, page))
    }
  }
}
