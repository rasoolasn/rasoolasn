package simulation.apiTest

import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class GetApiTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val scn : ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API List in the Records")
      .get("/posts")
  )
  //setup
  setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)
}