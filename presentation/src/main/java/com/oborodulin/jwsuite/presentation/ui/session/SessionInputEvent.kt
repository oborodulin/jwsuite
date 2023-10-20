package com.oborodulin.jwsuite.presentation.ui.session

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class SessionInputEvent(val value: String) : Inputable {
    data class Username(val input: String) : SessionInputEvent(input)
    data class Pin(val input: String) : SessionInputEvent(input)
    data class ConfirmPin(val input: String) : SessionInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
