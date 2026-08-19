package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure._
import io.gatling.http.Predef.http
import io.gatling.http.protocol.HttpProtocolBuilder
import simulation.utils.BaseTest

class BaseTestImplemantaionTest extends Simulation with BaseTest {

  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  val sc1: ScenarioBuilder = scenario("scenario 1")
    .exec(postUser("Data/requestBodyData/createRecord.json", 201, true, 9))
    .pause(1)
    .exec(updateUser(5,"Data/requestBodyData/createRecord.json",200, true, 2))
    .pause(1)
    .exec(postUser("Data/requestBodyData/createRecord.json", 201))

  //setup
  setUp(
    sc1.inject(atOnceUsers(1))
  )
    .protocols(httpProtocol)

}
