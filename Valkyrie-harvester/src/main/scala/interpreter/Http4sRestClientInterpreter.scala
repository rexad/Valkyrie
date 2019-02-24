package interpreter

import algebra.RestClient
import cats.effect.IO
import eu.timepit.refined.api.Refined
import eu.timepit.refined.string
import org.http4s._
import org.http4s.client.blaze.Http1Client

class Http4sRestClientInterpreter extends RestClient[IO] {

  override def get[A, B](url: Refined[String, string.Url], header: B)
                        (implicit ht : B => List[(String, String)] ): IO[A] = {
    val httpClient = Http1Client[IO]().unsafeRunSync

    httpClient.expect[A](
      Request[IO](method = Method.GET, uri = Uri.fromString(url.value).right.get, headers = Headers(List(Header(" ", " "))))
    )
  }
}
