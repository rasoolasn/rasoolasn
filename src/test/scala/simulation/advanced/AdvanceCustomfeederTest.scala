package simulation.advanced

import io.gatling.core.Predef._
import io.gatling.core.scenario.Simulation
import io.gatling.core.structure._
import io.gatling.http.Predef._
import scala.util.Random

class AdvanceCustomfeederTest extends Simulation {

  // Protocol
  val token: String = "Bearer 4a647271eace6a74e97e347596825d2f913b4b64f196bce04060b9262dafd09d"

  val httpprotocol = http.baseUrl("https://gorest.co.in/public/v2")

  // Random generator
  val random = new Random()

  def randomString(length: Int): String = {
    random.alphanumeric.filter(_.isLetter).take(length).mkString
  }

  // Custom Feeder
  val customfeeder: Iterator[Map[String, _]] =
    Iterator.continually(
      Map(
        "name" -> randomString(8),
        "email" -> s"${randomString(8)}@online.com",
        "gender" -> "male",
        "status" -> "inactive"
      )
    )

  // Create User
  def createSingleStudent(): ChainBuilder = {

    exec(
      http("Post API to Create Student")
        .post("/users")
        .header("Authorization", token)
        .header("Accept", "application/json")
        .header("Content-Type", "application/json")
        .body(
          StringBody { session =>
            s"""
               |{
               |  "name": "${session("name").as[String]}",
               |  "email": "${session("email").as[String]}",
               |  "gender": "${session("gender").as[String]}",
               |  "status": "${session("status").as[String]}"
               |}
               |""".stripMargin
          }
        ).asJson
        .check(
          status.saveAs("statusCode"),
          bodyString.saveAs("response")
        )
    )
      .exec { session =>
        println("--------------------------------")
        println("Status   = " + session("statusCode").as[Int])
        println("Response = " + session("response").as[String])
        println("--------------------------------")
        println("********************************123")
        session
      }
  }

  // Scenario
  val scn: ScenarioBuilder = scenario("Advance Custom Feeder")
      .feed(customfeeder)
      .exec(createSingleStudent())

  // Setup
  setUp(scn.inject(atOnceUsers(5)))
    .protocols(httpprotocol)
}