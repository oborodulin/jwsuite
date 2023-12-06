package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.group.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.jwsuite.presentation_congregation.ui.model.CongregationsListItem

sealed class GroupInputEvent(val value: String) : Inputable {
    data class Congregation(val input: CongregationsListItem) : GroupInputEvent(input.headline)
    data class GroupNum(val input: String) : GroupInputEvent(input)

    override fun value(): String {
        return this.value
    }
}