package com.oborodulin.jwsuite.presentation.ui.login

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class LoginFields : Focusable {
    LOGIN_USERNAME,
    LOGIN_PASSWORD;

    override fun key(): String {
        return this.name
    }
}
