package com.oborodulin.home.common.util

import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.Date
import java.util.Locale

//create an extension function on a date class which returns a string
fun Date.dateToString(format: String): String {
    //simple date formatter
    val dateFormatter = SimpleDateFormat(format, Locale.getDefault())

    //return the formatted date string
    return dateFormatter.format(this)
}

fun Double.is5PercentUp(): Boolean {
    return this >= 5
}

fun Double.is5PercentDown(): Boolean {
    return this <= -5
}

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

fun String.toFullFormatOffsetDateTime() = if (this.isNotEmpty())
    LocalDate.parse(
        this,
        DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT)
    ).atStartOfDay(ZoneId.systemDefault()).toOffsetDateTime() else null

fun OffsetDateTime?.toShortFormatString() =
    this?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))