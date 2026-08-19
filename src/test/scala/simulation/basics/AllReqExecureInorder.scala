package simulation.basics

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class AllReqExecureInorder extends  Simulation{

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  val allscenario: ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API ").get("/posts")
        .check(status is 200)

    )
    .pause(3)
     .exec(
      http("Post API ").
        post("/posts").body(RawFileBody("Data/requestBodyData/createRecord.json")).asJson
        .check(status.in(200 to 205))
        .check(jsonPath("$.id").is("1"))

     )
    .pause(3, 5)

    .exec(session => session.set("pause","3"))
    .exec(
      http("Update API ").put("/posts/10").body(RawFileBody("Data/requestBodyData/updateRecord.json")).asJson
        .check(status.in(200 to 205))
        .check(jsonPath("$.id").is("1"))
        .check(jsonPath("$.title").is("Learining Postman tool Again"))
    )
    .pause("#{pause}")

    .exec(session => session.set("pause","3"))
    .exec(
      http("Delete API ").delete("/posts/10").body(RawFileBody("Data/requestBodyData/deleteRecord.json")).asJson
        .check(status.not(405))
    )

    .pause("#{pause}")
  //setup

   setUp(allscenario.inject(atOnceUsers(10)))
    .protocols(httpProtocol)

}
