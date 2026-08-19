package simulation.apiTest

package simulation.apiTest
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._


class ReqResAllCrudTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val getscn: ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API ")
        .get("/posts")
    )
  val postscn: ScenarioBuilder = scenario("Create Record")
    .exec(
      http("Post API ")
        .post("/posts").body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson
    )
  val putscn: ScenarioBuilder = scenario("Put API")
    .exec(
      http("Update API ")
        .put("/posts/10")
        .body(RawFileBody("Data/requestBodyData/updateRecord.json")).asJson
    )
  val delscn: ScenarioBuilder = scenario("Delete Record")
    .exec(
      http("Delete API ")
        .delete("/posts/10")
        .body(RawFileBody("Data/requestBodyData/deleteRecord.json")).asJson
    )

  //setup
  setUp(
    getscn.inject(atOnceUsers(10)),
    postscn.inject(atOnceUsers(10)),
    putscn.inject(atOnceUsers(10)),
    delscn.inject(atOnceUsers(10))
    )
    .protocols(httpProtocol)
}
