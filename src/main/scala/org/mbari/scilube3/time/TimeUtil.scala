package org.mbari.scilube3.time

import java.time.format.DateTimeFormatter

import scala.util.Try
import java.time.{Instant, ZoneId}

object TimeUtil {

  object ISO8601 {

    private[this] val formatMicros = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmss.SSSSSSX")
      .withZone(ZoneId.of("UTC"))

    private[this] val formatMillis = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmss.SSSX")
      .withZone(ZoneId.of("UTC"))

    private[this] val formatSeconds = DateTimeFormatter
      .ofPattern("yyyyMMdd'T'HHmmssX")
      .withZone(ZoneId.of("UTC"))

    private[this] val formatLongSeconds = DateTimeFormatter
      .ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSX")
      .withZone(ZoneId.of("UTC"))

    /** Parse a string in any of the formats that we use  in filenames
      * @param timestamp
      * @return
      */
    def parse(timestamp: String): Option[Instant] = {
      //    formatMillis.parse(timestamp)
      Try(formatMicros.parse(timestamp))
        .orElse(Try(formatMillis.parse(timestamp)))
        .orElse(Try(formatLongSeconds.parse(timestamp)))
        .orElse(Try(formatSeconds.parse(timestamp)))
        .toOption
        .map(Instant.from)
    }
  }
}