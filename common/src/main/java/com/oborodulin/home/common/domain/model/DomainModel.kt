package com.oborodulin.home.common.domain.model;

import java.io.Serializable
import java.util.UUID

open class DomainModel(
    var id: UUID? = null,
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

