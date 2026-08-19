package simulation.apiTest
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class PostApiTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val scn: ScenarioBuilder = scenario("Create Record")
    .exec(
      http("Post API -Create Record")
      .post("/posts").body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson

    )
  setUp(scn.inject(atOnceUsers(10))).protocols(httpProtocol)

}
