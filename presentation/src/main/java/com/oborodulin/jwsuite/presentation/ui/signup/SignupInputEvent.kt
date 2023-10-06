package com.oborodulin.jwsuite.presentation.ui.signup

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class SignupInputEvent(val value: String) : Inputable {
    data class Username(val input: String) : SignupInputEvent(input)
    data class Password(val input: String) : SignupInputEvent(input)
    data class ConfirmPassword(val input: String) : SignupInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
