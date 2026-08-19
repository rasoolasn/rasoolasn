package simulation.utils

import io.gatling.core.Predef._
import io.gatling.core.structure.ChainBuilder
import io.gatling.http.Predef._
import io.gatling.http.request.builder.HttpRequestBuilder
import io.netty.handler.codec.http.HttpHeaderNames



trait BaseTest {

  def postUser(RequestBodyFileame: String, statuscode: Int, needRepeting: Boolean = false, repeatingtime: Int = 0 ): ChainBuilder = {
    val request = http("Post API ")
        .post("/posts")
        .body(RawFileBody(RequestBodyFileame)).asJson
        .check(status.is(statuscode))
    requestBuilder(request , needRepeting, repeatingtime )
    

  }

  def updateUser(userid: Int, RawBodyFilename: String, statuscode: Int, needRepeting: Boolean = false, repeatingtime: Int = 0 ): ChainBuilder = {
    val request= http("Update API ")
        .put(s"/posts/$userid")
        .body(RawFileBody(RawBodyFilename)).asJson
        .check(status.is(statuscode))
    requestBuilder(request, needRepeting, repeatingtime)
    
  }

  def requestBuilder(request: HttpRequestBuilder, needRepeting: Boolean = false, repeatingtime: Int = 0 ): ChainBuilder = {
    if (needRepeting){
      repeat(repeatingtime, "repeaterIndex"){
        exec(request)
      }
    }
    else {
      exec(request)
    }

  }

}
