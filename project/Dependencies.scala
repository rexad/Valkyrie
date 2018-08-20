import play.sbt.PlayImport._
import sbt._

// noinspection SpellCheckingInspection
object Dependencies {
  object Version {
    val playJson = "2.6.9"
    val playWS = "1.1.6"
    val play = "2.6.11"
    val playExtensions = "0.10.0"
    val scalaTestPlay = "3.1.2"
    val playSlick = "3.0.3"
    val akka = "2.5.11"
    val swaggerCheck = "1.0.0"
    val specs2 = "3.8.9"
    val scalaTest = "3.0.5"
    val playFakeWS = "1.0.0"
    val slick = "3.2.1"
    val adp = "2.4.1.7"
    val jtds = "1.3.1"
    val logback = "1.2.3"
    val jodaTime = "2.9.6"
    val config = "1.3.1"
    val ficus = "1.4.3"
    val scalaLogging = "3.5.0"
    val guava = "22.0"
    val commonsEmail = "1.4"
    val rabbitMq = "4.1.0"
    val mockitoCore = "2.8.47"
    val cusco = "1.6.0"
    val freeMarker = "2.3.27-incubating"
    val bouncyCastle = "1.57"
    val javaMail = "1.6.0"
    val commonsCli = "1.4"
    val commonsLang3 = "3.7"
    val h2 = "1.4.193"
    val scalaUrlBuilder = "0.9.0"
    val commonMark = "0.10.0"
    val experiment = "2.5.2"
    val redaction = "1.0.7"
    val javaMailMock = "1.9"
    val monix = "3.0.0-RC1"
    val monixKafka = "0.16"
    val jwt = "3.2.0"
    val commonValidator = "1.6"
  }

  val commonsDependencies = Seq(
    "org.apache.commons" % "commons-lang3" % Version.commonsLang3,
    "joda-time" % "joda-time" % Version.jodaTime,
    "com.typesafe.play" %% "play-json" % Version.playJson,
    "com.typesafe.play" %% "play-json-joda" % Version.playJson,
    "com.typesafe.play" %% "play-ahc-ws-standalone" % Version.playWS,
    "com.typesafe.play" %% "play-ws-standalone-json" % Version.playWS,
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging,
    "com.typesafe" % "config" % Version.config,
    "org.f100ded.scala-url-builder" %% "scala-url-builder" % Version.scalaUrlBuilder,
    "ai.x" %% "play-json-extensions" % Version.playExtensions,
    "com.typesafe.slick" %% "slick" % Version.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % Version.slick,
    "commons-validator" % "commons-validator" % Version.commonValidator
  )
  val testDependencies = Seq(
    "org.scalatestplus.play" %% "scalatestplus-play" % Version.scalaTestPlay % "test",
    "org.scalatest" %% "scalatest" % Version.scalaTest % "test",
    "org.mockito" % "mockito-core" % Version.mockitoCore % "test",
    "com.typesafe.akka" %% "akka-testkit" % Version.akka % "test",
    "org.f100ded.play" %% "play-fake-ws-standalone" % Version.playFakeWS % "test"
  )

  val processorDependencies = Seq(
    "com.typesafe" % "config" % Version.config,
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging,
    "com.typesafe.slick" %% "slick" % Version.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % Version.slick,
    "com.rabbitmq" % "amqp-client" % Version.rabbitMq,
    "ch.qos.logback" % "logback-classic" % Version.logback
    )

  val apiDependencies = Seq(
    "com.typesafe.play" %% "play-slick" % Version.playSlick,
    "com.rabbitmq" % "amqp-client" % Version.rabbitMq,
    "com.microsoft.sqlserver" % "mssql-jdbc" % "6.1.0.jre8",
    filters,
    guice,
    "com.h2database" % "h2" % Version.h2 % Test,
    "com.typesafe.play" %% "play-test" % Version.play % "it,test",
    "de.leanovate.swaggercheck" %% "swagger-check-core" % Version.swaggerCheck % "it,test",
    "de.leanovate.swaggercheck" %% "swagger-check-core" % Version.swaggerCheck % "it,test",
    "org.specs2" %% "specs2-scalacheck" % Version.specs2 % "it,test",
    specs2 % "it,test",
    "com.auth0" % "java-jwt" % Version.jwt
  )

  val harvesterDependencies = Seq(
    "mysql" % "mysql-connector-java" % "5.1.16",
    "com.typesafe" % "config" % Version.config,
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging,
    "ch.qos.logback" % "logback-classic" % Version.logback,
    "com.rabbitmq" % "amqp-client" % Version.rabbitMq,
    "io.monix" %% "monix-execution" % Version.monix,
    "io.monix" %% "monix-reactive" % Version.monix,
    guice,
    "io.monix" %% "monix-kafka-11" % Version.monixKafka
  )

  val experimentDependencies = Seq(
    "org.scalatest" %% "scalatest" % Version.scalaTest % "test",
    "org.mockito" % "mockito-core" % Version.mockitoCore % "test",
    "com.typesafe.scala-logging" %% "scala-logging" % Version.scalaLogging,
    "com.iheart" %% "ficus" % Version.ficus
  )

  val syncDbDependencies = Seq(
    "net.sourceforge.jtds" % "jtds" % Version.jtds,
    "com.typesafe.slick" %% "slick" % Version.slick,
    "com.typesafe.slick" %% "slick-hikaricp" % Version.slick
  )
}
