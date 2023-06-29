package com.oborodulin.jwsuite.presentation.ui.modules.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class CongregationInputEvent(val value: String) : Inputable {
    data class LocalityId(val input: String) : CongregationInputEvent(input)
    data class CongregationNum(val input: String) : CongregationInputEvent(input)
    data class CongregationName(val input: String) : CongregationInputEvent(input)
    data class TerritoryMark(val input: String) : CongregationInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
