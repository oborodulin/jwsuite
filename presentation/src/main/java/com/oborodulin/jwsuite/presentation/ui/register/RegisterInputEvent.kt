package com.oborodulin.jwsuite.presentation.ui.register

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class RegisterInputEvent(val value: String) : Inputable {
    data class Username(val input: String) : RegisterInputEvent(input)
    data class Password(val input: String) : RegisterInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
