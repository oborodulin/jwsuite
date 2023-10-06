package com.oborodulin.jwsuite.presentation.ui.signup

import com.oborodulin.home.common.ui.components.field.util.Focusable

enum class SignupFields : Focusable {
    SIGNUP_USERNAME,
    SIGNUP_PASSWORD,
    SIGNUP_CONFIRM_PASSWORD;

    override fun key(): String {
        return this.name
    }
}
