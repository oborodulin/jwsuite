package com.oborodulin.jwsuite.presentation_congregation.ui.congregating.congregation.single

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class CongregationInputEvent(val value: String) : Inputable {
    data class Locality(val input: ListItemModel) : CongregationInputEvent(input.headline)
    data class CongregationNum(val input: String) : CongregationInputEvent(input)
    data class CongregationName(val input: String) : CongregationInputEvent(input)
    data class TerritoryMark(val input: String) : CongregationInputEvent(input)
    data class IsFavorite(val input: Boolean) : CongregationInputEvent(input.toString())

    override fun value() = this.value
}
