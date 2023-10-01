package com.oborodulin.home.common.data

import com.oborodulin.home.common.util.Constants
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.time.OffsetDateTime
import java.time.format.DateTimeFormatter

// https://stackoverflow.com/questions/65011936/serialize-zoneddatetime-in-kotlin-data-class
object OffsetDateTimeSerializer : KSerializer<OffsetDateTime> {
    private val formatter =
        DateTimeFormatter.ofPattern(Constants.APP_OFFSET_DATE_TIME)//.ISO_OFFSET_DATE_TIME
    override val descriptor = PrimitiveSerialDescriptor("OffsetDateTime", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): OffsetDateTime {
        return formatter.parse(decoder.decodeString(), OffsetDateTime::from)
    }

    override fun serialize(encoder: Encoder, value: OffsetDateTime) {
        encoder.encodeString(value.format(formatter))
    }
}