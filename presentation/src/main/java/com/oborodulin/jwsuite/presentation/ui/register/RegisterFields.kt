package com.oborodulin.jwsuite.presentation.ui.register

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class RegisterFields : Focusable {
    REGISTER_USERNAME,
    REGISTER_PASSWORD;

    override fun key(): String {
        return this.name
    }
}
