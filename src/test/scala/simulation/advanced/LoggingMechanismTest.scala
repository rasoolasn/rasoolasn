package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class LoggingMechanismTest extends  Simulation{

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  val allscenario: ScenarioBuilder = scenario("List Records")
    .exec(flushHttpCache)
    .exec(flushCookieJar)
     .exec(
      http("Post API ").
        post("/posts").body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson
        .check(status.in(200 to 205))
        .check(jsonPath("$.userId").is("1"))

     )

  //setup
   setUp(allscenario.inject(atOnceUsers(10)))
    .protocols(httpProtocol)
}
