package com.oborodulin.home.common.data.json

import com.oborodulin.home.common.extensions.toUUIDOrNull
import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.util.UUID

// https://www.baeldung.com/kotlin/moshi-json-library
class UuidAdapter : JsonAdapter<UUID>() {
    @FromJson
    override fun fromJson(reader: JsonReader) = reader.readJsonValue()?.toString().toUUIDOrNull()

    @ToJson
    override fun toJson(writer: JsonWriter, value: UUID?) {
        writer.jsonValue(value.toString())
    }
}