package Service.RMQ

object ExchangeType extends Enumeration {
  type ExchangeType = Value
  val Direct = Value("direct")
  val Fanout = Value("fanout")
  val Topic = Value("topic")
}
