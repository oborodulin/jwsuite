package com.oborodulin.jwsuite.presentation.ui.modules.territoring.territory.grid

import com.oborodulin.home.common.ui.components.field.util.Inputable
import com.oborodulin.home.common.ui.model.ListItemModel

sealed class TerritoriesInputEvent(val value: String) : Inputable {
    data class Member(val input: ListItemModel) : TerritoriesInputEvent(input.headline)
    data class ReceivingDate(val input: String) : TerritoriesInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
