package simulation.apiTest
import io.gatling.core.scenario.Simulation
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._


class DeleteApiTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val scn: ScenarioBuilder = scenario("Delete Record")
    .exec(
      http("Delete API -Delete Record")
        .delete("/posts/10")
        .body(RawFileBody("Data/requestBodyData/deleteRecord.json")).asJson
    )
  setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)

}
