package algebra

import eu.timepit.refined.api.Refined
import eu.timepit.refined.string.Url

trait RestClient[F[_]] {
  def get[A, B](url: String Refined Url, header: B)
               (implicit ht : B => List[(String, String)] ): F[A]
  // def post[A, B, C](url: String Refined Url, header: B, body: C): F[A]
}
