package com.oborodulin.home.common.ui.model

import java.util.UUID

open class ModelUi(var id: UUID? = null, var tlId: UUID? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModelUi) return false

        if (id != other.id) return false
        if (tlId != other.tlId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + tlId.hashCode()
        return result
    }
}
