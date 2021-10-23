package com.github.nullptr7
package error

import io.circe.Codec
import io.circe.generic.semiauto.deriveCodec

case class ErrorResponse(statusCode: Int, message: String)

object ErrorResponse {
  implicit val errorResponseMapping: Codec[ErrorResponse] = deriveCodec[ErrorResponse]
}
