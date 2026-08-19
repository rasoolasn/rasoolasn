package demo

import scala.concurrent.duration._

import io.gatling.core.Predef._
import io.gatling.http.Predef._
import io.gatling.jdbc.Predef._

class RecordedHARSimulation extends Simulation {

  private val httpProtocol = http
    .baseUrl("https://petstore.octoperf.com")
    .inferHtmlResources(AllowList(), DenyList(""".*\.js""", """.*\.css""", """.*\.gif""", """.*\.jpeg""", """.*\.jpg""", """.*\.ico""", """.*\.woff""", """.*\.woff2""", """.*\.(t|o)tf""", """.*\.png""", """.*\.svg""", """.*detectportal\.firefox\.com.*"""))
    .acceptHeader("text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7")
    .acceptEncodingHeader("gzip, deflate, br")
    .acceptLanguageHeader("en-US,en;q=0.9,hi;q=0.8,sw;q=0.7")
    .upgradeInsecureRequestsHeader("1")
    .userAgentHeader("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/151.0.0.0 Safari/537.36")
  
  private val headers_0 = Map(
  		"priority" -> "u=0, i",
  		"sec-ch-ua" -> """Not=A?Brand";v="99", "Google Chrome";v="151", "Chromium";v="151""",
  		"sec-ch-ua-mobile" -> "?0",
  		"sec-ch-ua-platform" -> "Windows",
  		"sec-fetch-dest" -> "document",
  		"sec-fetch-mode" -> "navigate",
  		"sec-fetch-site" -> "same-origin",
  		"sec-fetch-user" -> "?1"
  )


  private val scn = scenario("RecordedHARSimulation")
    .exec(
      http("Landing Page Click on Fish")
        .get("/actions/Catalog.action;jsessionid=F97FDB06F6B093C7385815100BEBC0D0?viewCategory=&categoryId=FISH")
        .headers(headers_0),
      pause(2),
      http("Add to Catalog")
        .get("/actions/Catalog.action?viewProduct=&productId=FI-SW-01")
        .headers(headers_0),
      pause(3),
      http("Add Cart")
        .get("/actions/Cart.action?addItemToCart=&workingItemId=EST-1")
        .headers(headers_0),
      pause(4),
      http("Click Order")
        .get("/actions/Order.action?newOrderForm=")
        .headers(headers_0)
    )

	setUp(scn.inject(atOnceUsers(1))).protocols(httpProtocol)
}
