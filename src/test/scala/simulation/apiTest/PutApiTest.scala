package simulation.apiTest

import io.gatling.core.scenario.Simulation
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._

class PutApiTest extends Simulation{

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val scn: ScenarioBuilder = scenario("Put API")
    .exec(
      http("Update API ")
        .put("/posts/10")
        .body(RawFileBody("Data/requestBodyData/updateRecord.json")).asJson
    )

  setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)

}
