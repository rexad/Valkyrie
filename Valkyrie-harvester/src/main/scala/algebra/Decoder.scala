package algebra

trait Decoder[A] {
  def apply(v: String): Either[Exception, A]
  def apply(v: Array[Byte]): Either[Exception, A]
}
