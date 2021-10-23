package com.github.nullptr7
package routes

import model.Customer
import repo.CustomerRepoImpl

import cats.effect._
import cats.implicits._
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s._
import org.http4s.circe.CirceEntityCodec.circeEntityEncoder
import org.http4s.dsl._

object CustomerRoute {

  def loadCustomers[F[_]: Async](customerRepoImpl: CustomerRepoImpl[F]): F[List[Customer]] =
    customerRepoImpl.value

  def apply[F[_]: Async](customers: List[Customer]): F[HttpRoutes[F]] = {
    object dsl extends Http4sDsl[F]; import dsl._
    HttpRoutes
      .of[F] { case _ @GET -> Root / "api" / "customers" =>
        Ok(customers.asJson.pure[F])
        //PreconditionFailed("Certain data required is not correct")
      }
      .pure[F]
  }

}
