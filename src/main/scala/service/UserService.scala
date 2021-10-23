package com.github.nullptr7
package service

import error.ErrorResponse
import model.{User, UserServiceWrapper}

import cats.data.EitherT
import cats.effect.kernel.Async
import org.http4s.implicits._
import org.http4s.{EntityDecoder, Header, Method, Request, Uri}
import org.typelevel.ci.CIString
import org.typelevel.log4cats.Logger
import org.typelevel.log4cats.slf4j.Slf4jLogger

class UserService[F[_]](implicit override val async: Async[F]) extends Http4sServiceHandler[F] {

  implicit override protected[service] val logger: Logger[F]  = Slf4jLogger.getLogger[F]
  private val allUserUri:                          Uri        = uri"https://reqres.in/api/users"
  private val mediaTypeJson:                       Header.Raw = Header.Raw.apply(CIString("Content-Type"), "application/json")

  def getAllUsers(implicit
    userEntityDecoder: EntityDecoder[F, UserServiceWrapper]
  ): EitherT[F, ErrorResponse, List[User]] = {

    val req: Request[F] =
      Request.apply(method = Method.GET, uri = allUserUri, headers = org.http4s.Headers(mediaTypeJson))
    sendAndReceive[UserServiceWrapper](req).map(_.data)
  }

}
