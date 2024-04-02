package com.oborodulin.home.common.data.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

// https://stackoverflow.com/questions/71360333/convert-date-and-time-to-iso-8601-in-kotlin
// https://stackoverflow.com/questions/25938560/parse-iso8601-date-string-to-date-with-utc-timezone
class OffsetDateTimeAdapter : JsonAdapter<OffsetDateTime>() {
    @FromJson
    override fun fromJson(reader: JsonReader) =
        reader.readJsonValue()?.toString()
            ?.let { OffsetDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME) }

    @ToJson
    override fun toJson(writer: JsonWriter, value: OffsetDateTime?) {
        writer.jsonValue(value?.format(DateTimeFormatter.ISO_DATE_TIME))
    }
}