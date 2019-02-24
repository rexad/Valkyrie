package Model

sealed trait harvesterException extends Exception

case class failedSPDownloadException(message: String) extends harvesterException
case class failedParsingSP(message: String) extends harvesterException
