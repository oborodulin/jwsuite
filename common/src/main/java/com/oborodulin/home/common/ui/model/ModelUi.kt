package com.oborodulin.home.common.ui.model

import java.util.UUID

open class ModelUi(var id: UUID? = null) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is ModelUi) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
