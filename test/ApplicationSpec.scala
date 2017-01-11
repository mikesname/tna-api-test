
import play.api.test._

class ApplicationSpec extends PlaySpecification {

  "Routes" should {

    "send 404 on a bad request" in new WithApplication {
      route(app, FakeRequest(GET, "/boum")).map(status) must_== Some(NOT_FOUND)
    }

  }

  "HomeController" should {

    "render the index page" in new WithApplication {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) must_== OK
      contentType(home) must_== Some("text/html")
      contentAsString(home) must contain("Search")
    }
  }
}
