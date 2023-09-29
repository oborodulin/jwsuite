package com.oborodulin.jwsuite.presentation.ui.login

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class LoginInputEvent(val value: String) : Inputable {
    data class Username(val input: String) : LoginInputEvent(input)
    data class Password(val input: String) : LoginInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
