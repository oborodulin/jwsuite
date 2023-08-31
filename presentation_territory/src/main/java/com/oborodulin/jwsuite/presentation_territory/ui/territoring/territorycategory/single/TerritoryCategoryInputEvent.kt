package com.oborodulin.jwsuite.presentation_territory.ui.territoring.territorycategory.single

import com.oborodulin.home.common.ui.components.field.util.Inputable

sealed class TerritoryCategoryInputEvent(val value: String) : Inputable {
    data class TerritoryCategoryCode(val input: String) : TerritoryCategoryInputEvent(input)
    data class TerritoryCategoryMark(val input: String) : TerritoryCategoryInputEvent(input)
    data class TerritoryCategoryName(val input: String) : TerritoryCategoryInputEvent(input)

    override fun value(): String {
        return this.value
    }
}
