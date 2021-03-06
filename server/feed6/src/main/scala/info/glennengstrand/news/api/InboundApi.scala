/**
 * News Feed
 * news feed api
 *
 * OpenAPI spec version: 1.0.0
 * Contact: media@glennengstrand.info
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 */

package info.glennengstrand.news.api

import info.glennengstrand.news.model.Inbound

import java.io.File

import org.scalatra.ScalatraServlet
import org.scalatra.swagger._
import org.json4s._
import org.json4s.JsonDSL._
import org.scalatra.json.{ JValueResult, JacksonJsonSupport }
import org.scalatra.servlet.{ FileUploadSupport, MultipartConfig, SizeConstraintExceededException }

import scala.collection.JavaConverters._
import info.glennengstrand.news.DI._

class InboundApi(implicit val swagger: Swagger) extends ScalatraServlet
  with FileUploadSupport
  with JacksonJsonSupport
  with SwaggerSupport {
  protected implicit val jsonFormats: Formats = DefaultFormats

  protected val applicationDescription: String = "InboundApi"

  before() {
    contentType = formats("json")
    response.headers += ("Access-Control-Allow-Origin" -> "*")
  }

  val getInboundOperation = (apiOperation[List[Inbound]]("getInbound")
    summary "retrieve the inbound feed for an individual participant"
    parameters (pathParam[Long]("id").description("")))

  get("/:id", operation(getInboundOperation)) {

    val id = params.getOrElse("id", halt(400))

    inboundService.gets(id.toLong)
  }

}
