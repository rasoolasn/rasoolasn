package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure._
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder
import io.gatling.core.structure.ScenarioBuilder

class AuthenticationTest extends Simulation{

  //Protocol
  val token: String = "Bearer 4a647271eace6a74e97e347596825d2f913b4b64f196bce04060b9262dafd09d"

  val httpprotocol: HttpProtocolBuilder = http.baseUrl("https://gorest.co.in/public/v2")


  // scenario
    def AllStudentDetails(): ChainBuilder = {
      exec(
       http("get all the students details")
         .get("/users")
         .header("Authorization", token)
         .check(
          status.is(200),
          jsonPath("$[0].id").saveAs("studentId"),
          jsonPath("$[0].name").saveAs("studentName")
         )
      )
    }

  def SingleUserDetails(): ChainBuilder = {
    exec(
      http("Get Single User Detail")
        .get("/users/#{studentId}")
        .header("Authorization", token)
        .check(
          status.is(200),
          jsonPath("$.id").saveAs("#{studentId}"),
          jsonPath("$.name").saveAs("#{studentName}")
        )
    )
  }


 val Sce1: ScenarioBuilder = scenario("Get all user details")
    .exec(AllStudentDetails())
    .pause(3)
    .exec(SingleUserDetails())

  //setup
setUp(
  Sce1.inject(atOnceUsers(1))
    .protocols(httpprotocol)
    )

}
