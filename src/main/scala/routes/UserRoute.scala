package com.github.nullptr7
package routes

import model.UserServiceWrapper.userEncoder
import service.UserService

import cats.effect.Async
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.circe.CirceEntityCodec.{circeEntityDecoder, circeEntityEncoder}
import org.http4s.dsl.Http4sDsl
import org.typelevel.log4cats.SelfAwareStructuredLogger
import org.typelevel.log4cats.slf4j.Slf4jLogger

object UserRoute {

  def apply[F[_]: Async]: F[HttpRoutes[F]] = {

    val logger: SelfAwareStructuredLogger[F] = Slf4jLogger.getLogger[F]

    val userService = new UserService[F]
    object dsl extends Http4sDsl[F]; import dsl._
    HttpRoutes
      .of[F] { case _ @GET -> Root / "api" / "users" =>
        userService.getAllUsers.value.flatMap {
          case Left(value)  => logger.error(s"Bad request receive $value") *> BadRequest(value)
          case Right(value) => logger.info(s"Request is receive $value") *> Ok(value)
        }
      }
      .pure[F]
  }

}
