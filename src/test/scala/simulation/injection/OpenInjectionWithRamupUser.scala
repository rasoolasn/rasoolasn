package simulation.injection

import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class OpenInjectionWithRamupUser extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val scn : ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API List in the Records")
        .get("/posts")
    )
  //setup
  setUp(
    scn.inject(
        nothingFor(5),
        atOnceUsers(1),
        rampUsers(10).during(2)
        )
    .protocols(httpProtocol)
    )
}


