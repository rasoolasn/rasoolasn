package simulation.basics

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder


class AddPauseTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario
  val getscenario: ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API ")
        .get("/posts")
    )
    .pause(3)
  val postscenario: ScenarioBuilder = scenario("Create Record")
    .exec(
      http("Post API ")
        .post("/posts").body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson
    )
    .pause(3, 5)
  val putscenario: ScenarioBuilder = scenario("Put API")
    .exec(session => session.set("pause","3"))
     .exec(
      http("Update API ")
        .put("/posts/10")
        .body(RawFileBody("Data/requestBodyData/updateRecord.json")).asJson
    )
    .pause("#{pause}")

  val delscenario: ScenarioBuilder = scenario("Delete Record")
    .exec(session => session.set("pause","3"))
    .exec(
      http("Delete API ")
        .delete("/posts/10")
        .body(RawFileBody("Data/requestBodyData/deleteRecord.json")).asJson
    )
    .pause("#{pause}")
  //setup
  setUp(
    getscenario.inject(atOnceUsers(10)),
    postscenario.inject(atOnceUsers(10)),
    putscenario.inject(atOnceUsers(10)),
    delscenario.inject(atOnceUsers(10))
    )
    .protocols(httpProtocol)
}
