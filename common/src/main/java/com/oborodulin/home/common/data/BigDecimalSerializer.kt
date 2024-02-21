package com.oborodulin.home.common.data

import com.oborodulin.home.common.util.Constants.CONV_COEFF_BIGDECIMAL
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.math.BigDecimal

// https://woohuiren.me/blog/custom-kotlin-serializer-for-bigdecimal/
object BigDecimalSerializer : KSerializer<BigDecimal> {
    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("BigDecimal", PrimitiveKind.LONG)

    override fun deserialize(decoder: Decoder): BigDecimal {
        return BigDecimal(decoder.decodeLong()).divide(BigDecimal(CONV_COEFF_BIGDECIMAL))
    }

    override fun serialize(encoder: Encoder, value: BigDecimal) {
        encoder.encodeLong(value.multiply(BigDecimal(CONV_COEFF_BIGDECIMAL)).toLong())
    }
}