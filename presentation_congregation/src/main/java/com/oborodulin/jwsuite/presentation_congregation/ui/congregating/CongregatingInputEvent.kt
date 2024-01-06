package com.oborodulin.jwsuite.presentation_congregation.ui.congregating

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class CongregatingInputEvent(val value: String) : Inputable {
    data class IsService(val input: Boolean) : CongregatingInputEvent(input.toString())

    override fun value() = this.value
}
