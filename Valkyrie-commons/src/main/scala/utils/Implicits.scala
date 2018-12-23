package utils

object Implicits {
  implicit class InterpolatedString(val s: String) extends AnyVal {
    // to use: "%{hey} whats up" %%% Map("hey" -> "ho")
    def %%%(m: Map[String, String], isStrict: Boolean = true): String = {
      "%\\{[^\\}]*\\}".r.findAllIn(s).fold(s) {
        (res, key) => {
          val x: String = res
          val keyVal = key.substring(2, key.length - 1) // Remove the %{ }
          res.replaceAllLiterally(key, m.getOrElse(keyVal, {
            if (isStrict) {
              throw new IllegalArgumentException(s"Unknown key $keyVal in pattern $s")
            }
            else {
              key
            }
          }))
        }
      }
    }
    def isAscii : Boolean = s.forall(_ < 128)
  }

  implicit class FormatString(val s: String) {
    def removePunctAndSpace(replace: Option[String] = None): String = {
      s.replaceAll("""[\p{Punct}||[\p{Space}]&&[^()]]""", replace.getOrElse(""))
    }
  }
}