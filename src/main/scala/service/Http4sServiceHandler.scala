package com.github.nullptr7
package service

import error.ErrorResponse
import implicits.CommonImplicitOps._

import cats.data.EitherT
import cats.effect.kernel.{Async, Resource}
import cats.implicits._
import org.http4s.Status.Successful
import org.http4s.blaze.client.BlazeClientBuilder
import org.http4s.client.Client
import org.http4s.{EntityDecoder, Request}
import org.typelevel.log4cats.Logger

trait Http4sServiceHandler[F[_]] {

  implicit protected[service] val async:  Async[F]
  implicit protected[service] val logger: Logger[F]

  private[service] val resourceClient: Resource[F, Client[F]] = BlazeClientBuilder.apply[F].resource

  def sendAndReceive[T](request: Request[F])(implicit tEntityDecoder: EntityDecoder[F, T]): EitherT[F, ErrorResponse, T] =
    resourceClient.use {
      _.run(request).use {
        case Successful(response) =>
          logger.info(s"Response received with status ${response.status}") *>
            response
              .attemptAs[T]
              .value
              .map(_.leftMap(x => ErrorResponse(400, x.getMessage())))
        case failureResponse      =>
          logger.error(s"Failure in response... ${failureResponse.status}") *>
            Either
              .left[ErrorResponse, T](ErrorResponse(500, "Unknown Error"))
              .pure[F]
      }
    }.toEitherT

}
