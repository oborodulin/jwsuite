package com.oborodulin.home.common.domain.model;

import com.oborodulin.home.common.data.UUIDSerializer
import java.io.Serializable
import java.util.UUID

@kotlinx.serialization.Serializable
open class DomainModel(
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    var id: UUID? = null,
    @kotlinx.serialization.Serializable(with = UUIDSerializer::class)
    var tlId: UUID? = null
) : Serializable {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is DomainModel) return false

        if (id != other.id) return false
        if (tlId != other.tlId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id?.hashCode() ?: 0
        result = 31 * result + (tlId?.hashCode() ?: 0)
        return result
    }
}

