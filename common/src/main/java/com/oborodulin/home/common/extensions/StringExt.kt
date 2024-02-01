package com.oborodulin.home.common.extensions

import com.oborodulin.home.common.util.Constants
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.UUID

fun String.toUUIDOrNull() = this.ifEmpty { null }?.let { UUID.fromString(it) }

fun String.toOffsetDateTime(): OffsetDateTime {
    //val zoneId: ZoneId = ZoneId.of("UTC")   // Or another geographic: Europe/Paris
    //val defaultZone: ZoneId = ZoneId.systemDefault()
    val zoneId: ZoneId = ZoneId.systemDefault()
    val formatter: DateTimeFormatter =
        DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)
    val dateTime: LocalDateTime = LocalDateTime.parse(this, formatter)
    val offset: ZoneOffset = zoneId.rules.getOffset(dateTime)
    return OffsetDateTime.of(dateTime, offset)
}

fun String.toFullFormatOffsetDateTime(): OffsetDateTime = LocalDate.parse(
    this,
    DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime()

fun String.toFullFormatOffsetDateTimeOrNull() = if (this.isNotEmpty())
    this.toFullFormatOffsetDateTime()
else null