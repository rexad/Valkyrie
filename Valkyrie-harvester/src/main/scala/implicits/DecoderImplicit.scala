package implicits

import cats.effect.IO
import org.http4s.{DecodeResult, EntityDecoder, MediaRange, Message}

object DecoderImplicit  {
  implicit val DecodeString : EntityDecoder[IO, String] = {
    new EntityDecoder[IO, String] {

      override def decode(msg: Message[IO], strict: Boolean): DecodeResult[IO, String] = null

      override def consumes: Set[MediaRange] = null

    }
  }
}
