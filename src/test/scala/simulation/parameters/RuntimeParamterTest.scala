package simulation.parameters

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure.ScenarioBuilder
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class RuntimeParamterTest extends Simulation {

  //Protocol
  val httpProtocol: HttpProtocolBuilder = http.baseUrl("https://jsonplaceholder.typicode.com")
  //scenario

  def userMin: Int = System.getProperty("userMin","10").toInt
  def userMax: Int = System.getProperty("userMax","10").toInt
  def duration:Int = System.getProperty("duration","5").toInt

  before {
    println(s"User Count Min --> ${userMin}")
    println(s"User Count Min -->${userMax}")
    println(s"User usrDuration -->${duration}")
  }


  val scn : ScenarioBuilder = scenario("List Records")
    .exec(
      http("Get API List in the Records").get("/posts")
  )


  //setup
  setUp(scn.inject(rampConcurrentUsers(userMin).to(userMax).during(duration))
    .protocols(httpProtocol)
  )
}