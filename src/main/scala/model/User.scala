package com.github.nullptr7
package model

import io.circe._
import io.circe.generic.semiauto.{deriveDecoder, deriveEncoder}

import java.util.UUID

case class UserServiceWrapper(page: Int, per_page: Int, total: Int, total_pages: Int, data: List[User])

case class User(id: UUID, email: Option[String], fName: String, lName: String, avatar: Option[String])

object UserServiceWrapper {

  implicit val userServiceWrapperDecoder: Decoder[UserServiceWrapper] = deriveDecoder[UserServiceWrapper]

  implicit lazy val userDecoder: Decoder[User] = (c: HCursor) =>
    for {
      id     <- c.get[Int]("id").map(i => UUID.nameUUIDFromBytes(Array.apply(i.toByte)))
      email  <- c.get[Option[String]]("email")
      fName  <- c.get[String]("first_name")
      lName  <- c.get[String]("last_name")
      avatar <- c.get[Option[String]]("avatar")
    } yield User(id, email, fName, lName, avatar)

  implicit lazy val userEncoder: Encoder[User] = deriveEncoder[User]
}
