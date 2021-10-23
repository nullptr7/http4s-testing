package com.github.nullptr7
package repo

import model.Customer

import cats.effect.{Async, Resource}
import cats.implicits._
import doobie.implicits._
import doobie.util.transactor.Transactor.Aux
import doobie.{Fragment, Transactor}

import scala.io.{BufferedSource, Source}

class CustomerRepoImpl[F[_]: Async] {

  private val readRes: Resource[F, BufferedSource] =
    Resource.make[F, BufferedSource](Source.fromFile("src/main/resources/allUsers.sql").pure[F])(_ => ().pure[F])

  private def fetchCustomersFromDb(fragment: Fragment, xa: Aux[F, Unit]): F[List[Customer]] =
    fragment.query[Customer].to[List].transact[F](xa)

  private val xa: Aux[F, Unit] = Transactor.fromDriverManager[F](
    driver = "org.postgresql.Driver",
    url    = "jdbc:postgresql://localhost:15432/dockerDatabase",
    user   = "dockerUser",
    pass   = "docker@123Pocker@098"
  )

  val value: F[List[Customer]] = for {
    query     <- readRes.use(_.mkString.pure[F])
    fragment  <- Fragment.const(query).pure[F]
    customers <- fetchCustomersFromDb(fragment, xa)
  } yield customers

}

object CustomerRepoImpl {
  def apply[F[_]: Async] = new CustomerRepoImpl[F]
}
