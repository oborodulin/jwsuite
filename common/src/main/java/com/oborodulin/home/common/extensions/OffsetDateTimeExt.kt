package com.oborodulin.home.common.extensions

import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

fun OffsetDateTime?.toShortFormatString() =
    this?.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))