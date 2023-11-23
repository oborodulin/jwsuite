package com.oborodulin.jwsuite.domain.model

import com.oborodulin.jwsuite.domain.util.MemberRoleType
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

// https://stackoverflow.com/questions/65011936/serialize-zoneddatetime-in-kotlin-data-class
object MemberRoleTypeSerializer : KSerializer<MemberRoleType> {
    override val descriptor = PrimitiveSerialDescriptor("MemberRoleType", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): MemberRoleType {
        return MemberRoleType.valueOf(decoder.decodeString())
    }

    override fun serialize(encoder: Encoder, value: MemberRoleType) {
        encoder.encodeString(value.name)
    }
}