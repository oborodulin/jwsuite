package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class CongregatingFields : Focusable {
    CONGREGATING_IS_SERVICE;

    override fun key(): String {
        return this.name
    }
}
