package com.oborodulin.jwsuite.presentation.ui.modules.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class GroupInputEvent(val value: String) : Inputable {
    data class GroupCode(val input: String) : GroupInputEvent(input)
    data class GroupName(val input: String) : GroupInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
