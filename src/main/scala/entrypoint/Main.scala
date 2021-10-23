package com.github.nullptr7
package entrypoint

import repo.CustomerRepoImpl
import routes.{CustomerRoute, UserRoute}

import cats.effect.kernel.Resource
import cats.effect.{IO, IOApp}
import cats.implicits._
import org.http4s.HttpRoutes
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits._
import org.http4s.server.Server

object Main extends IOApp.Simple {

  private val allRoutes: IO[HttpRoutes[IO]] = for {
    allUsers      <- CustomerRepoImpl.apply[IO].value
    customerRoute <- CustomerRoute.apply[IO](allUsers)
    userRoute     <- UserRoute.apply[IO]
    composeRoutes <- (customerRoute <+> userRoute).pure[IO]
  } yield composeRoutes

  private def serverConfiguration(routes: HttpRoutes[IO]): Resource[IO, Server] =
    BlazeServerBuilder
      .apply[IO]
      .bindHttp(8080, "localhost")
      .withHttpApp(routes.orNotFound)
      .resource

  private val server: Resource[IO, Server] = for {
    routes <- Resource.eval(allRoutes)
    serve  <- serverConfiguration(routes)
  } yield serve

  override def run: IO[Unit] = server.use(_ => IO.never[Unit])
}
