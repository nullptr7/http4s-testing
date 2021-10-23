package com.github.nullptr7
package entrypoint

import cats.effect.{IO, IOApp}
import cats.implicits._

import scala.concurrent.duration._

object SimpleIOMain extends IOApp.Simple {

  override def run: IO[Unit] = IO.println("Hello Fucking World") *> forRun

  val forRun: IO[Unit] =
    for {
      ctr <- IO.ref(0)
      wait = IO.sleep(1.second)
      poll = wait *> ctr.get
      _ <- poll.flatMap(IO.println(_)).foreverM.start
      _ <- poll.map(_ % 3 == 0).ifM(IO.println("fizz"), IO.unit).foreverM.start
      _ <- poll.map(_ % 5 == 0).ifM(IO.println("buzz"), IO.unit).foreverM.start
      _ <- (wait *> ctr.update(_ + 1)).foreverM.void
    } yield ctr

}
