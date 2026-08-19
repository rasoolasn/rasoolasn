package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ChainBuilder
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder


class ReusableMethodTest extends Simulation{

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")

  //Scenario
  def postUser(): ChainBuilder = {
    exec(
      http("Post API ")
        .post("/posts")
        .body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson
    )
  }

  def updateUser(): ChainBuilder = {
    exec(
      http("Update API ")
        .put("/posts/10")
        .body(RawFileBody("Data/requestBodyData/updateRecord.json")).asJson
        )
  }

  val sc1: ScenarioBuilder = scenario("scenario 1")
    .exec(postUser())
    .exec(updateUser())
    .exec(postUser())

  //setup
  setUp(
      sc1.inject(atOnceUsers(1))
    )
    .protocols(httpProtocol)
}
