package data

import com.typesafe.config.{Config, ConfigFactory}
import slick.basic.DatabaseConfig
import slick.jdbc.JdbcProfile

object Databases {
  val config: Config = ConfigFactory.load()

  val dbValkyrie: DatabaseConfig[JdbcProfile] = DatabaseConfig.forConfig[JdbcProfile]("valkyrie.db.url", config)


}