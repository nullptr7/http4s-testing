package com.github.nullptr7
package implicits

import cats.Applicative
import cats.data.EitherT

object CommonImplicitOps {

  implicit class EitherTOps[F[_]: Applicative, L, R](either: F[Either[L, R]]) {
    def toEitherT: EitherT[F, L, R] = EitherT.apply(either)
  }

}
