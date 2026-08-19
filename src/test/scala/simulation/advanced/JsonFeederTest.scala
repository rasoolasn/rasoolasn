package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.feeder._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

class JsonFeederTest extends Simulation{

  //Protocol
  val token: String = "Bearer 4a647271eace6a74e97e347596825d2f913b4b64f196bce04060b9262dafd09d"
  val httpprotocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")

  val jsonfeederfile: Feeder[Any] = jsonFile("Data/feeder/studentDetails.json").circular()

  def SingleUserDetails(): ChainBuilder = {
  repeat(12){
    feed(jsonfeederfile)
       .exec(
        http("Get Single User Detail-#{id}")
          .get("/users/#{id}")
          .header("Authorization", token)
          .check(
            status.is(200),
            jsonPath("$.id").saveAs("#{id}"),
            jsonPath("$.name").saveAs("#{name}")
          )
      )
    }

  }
 // scenario

 val Sce1: ScenarioBuilder = scenario("Get user details")
    .exec(SingleUserDetails())

  //setup
setUp(
  Sce1.inject(atOnceUsers(1))
    .protocols(httpprotocol)
    )

}
