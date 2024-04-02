package com.oborodulin.home.common.data.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import com.squareup.moshi.ToJson
import java.math.BigDecimal

// https://stackoverflow.com/questions/54957945/moshi-problem-with-platform-class-bigdecimal
class BigDecimalAdapter : JsonAdapter<BigDecimal>() {
    @FromJson
    override fun fromJson(reader: JsonReader) =
        reader.readJsonValue()?.toString()?.toBigDecimalOrNull()

    @ToJson
    override fun toJson(writer: JsonWriter, value: BigDecimal?) {
        writer.jsonValue(value.toString())
    }
}